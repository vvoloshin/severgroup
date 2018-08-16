package com.severgroup.service;


import com.severgroup.model.RowSessionRecord;
import com.severgroup.to.AvgRecord;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractReaderService implements ReaderService<List<AvgRecord>> {
    private final static Logger LOGGER = Logger.getLogger(AbstractReaderService.class);
    protected Path fileName;

    public AbstractReaderService(Path fileName) {
        this.fileName = fileName;
    }

    public long toNextDaySeconds(long firstTimeStamp, long seconds) {
        Instant instantStart = Instant.ofEpochMilli(firstTimeStamp * 1000);
        //base date / time from CSV
        //slippery moment, time shift!
        LocalDateTime dateTimeStart = LocalDateTime.ofInstant(instantStart, ZoneOffset.of("+3"));
        //base date from CSV
        LocalDate dateStart = dateTimeStart.toLocalDate();
        //date after adding session time, seconds
        LocalDateTime dateTimeEnd = dateTimeStart.plusSeconds(seconds);
        LocalDate dateEnd = dateTimeEnd.toLocalDate();
        //check that the date after adding the session time remains the same, true-the next day
        if (dateEnd.isAfter(dateStart)) {
            //calculate the midnight time of the base date
            LocalDateTime midnight = LocalDateTime.of(dateStart, LocalTime.of(23, 59, 59));
            //the difference between START and midnight
            return ChronoUnit.SECONDS.between(dateTimeStart, midnight);
        }
        return 0;
    }

    public List<RowSessionRecord> splitNext(List<RowSessionRecord> rowSessionRecords) {
        List<RowSessionRecord> result = new ArrayList<>();
        for (int i = 0; i < rowSessionRecords.size(); i++) {
            RowSessionRecord record = rowSessionRecords.get(i);
            long secondDifference = toNextDaySeconds(record.getTimestamp(), record.getSeconds());
            if (secondDifference != 0) {
                //breaking the old record of the session user
                long sessionTime = record.getSeconds();
                //the old record, changing the time of the session, put (secondsBeforeMidnight-1)
                record.setSeconds(secondDifference);//-1
                //after it insert a new record, the session secondsAfterMidnight = seconds - secondsBeforeMidnight
                RowSessionRecord nextDayRecord = new RowSessionRecord(record);
                nextDayRecord.setSeconds(sessionTime - secondDifference);
                nextDayRecord.setTimestamp(record.getTimestamp() + secondDifference + 1);
                //save the old record
                result.add(record);
                //insert a new entry after it
                result.add(nextDayRecord);
            } else {
                result.add(record);
            }
        }
        return result;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public abstract List<RowSessionRecord> getRecords();

    public Map<LocalDate, List<RowSessionRecord>> groupDate(List<RowSessionRecord> records) {
        Map<LocalDate, List<RowSessionRecord>> result = new TreeMap<>();
        records.forEach(record -> {
            Instant instant = Instant.ofEpochMilli(record.getTimestamp() * 1000);
            LocalDate dateFromMap = LocalDateTime.ofInstant(instant, ZoneOffset.of("+3")).toLocalDate();
            List<RowSessionRecord> groupList;
            if (result.get(dateFromMap) != null) {
                groupList = result.get(dateFromMap);
            } else {
                groupList = new ArrayList<>();
            }
            groupList.add(record);
            result.put(dateFromMap, groupList);
        });
        return result;
    }

    public List<AvgRecord> getAverage(Map<LocalDate, List<RowSessionRecord>> groupList) {
        List<AvgRecord> result = new LinkedList<>();
        groupList.forEach(
                (date, list) -> {
                    Map<String, List<RowSessionRecord>> userGroupMap = list.stream()//-> User, List<RECORD>
                            .sorted(Comparator.comparing(RowSessionRecord::getUserId))
                            .collect(Collectors.groupingBy(RowSessionRecord::getUserId));
                    userGroupMap.forEach(
                            (userName, listRecord) -> {
                                Map<String, Double> urlGroupMap = listRecord.stream()//-> URL, AVG
                                        .collect(Collectors.groupingBy(RowSessionRecord::getUrl, (Collectors.averagingDouble(RowSessionRecord::getSeconds))));
                                urlGroupMap.forEach(
                                        (url, avg) -> {
                                            AvgRecord currentAvgRecord = new AvgRecord(date, userName, url, avg);
                                            result.add(currentAvgRecord);
                                        });
                            }
                    );
                }
        );
        return result;
    }

    @Override
    public List<AvgRecord> call() {
        LOGGER.debug("Thread for read : " + Thread.currentThread().getName());
        List<RowSessionRecord> rowSessionRecordBeforeSplit = getRecords();
        if (rowSessionRecordBeforeSplit != null && rowSessionRecordBeforeSplit.size() != 0) {
            List<RowSessionRecord> rowSessionRecordsAfterSplit = splitNext(rowSessionRecordBeforeSplit);
            Map<LocalDate, List<RowSessionRecord>> dateMap = groupDate(rowSessionRecordsAfterSplit);
            return getAverage(dateMap);
        }
        return null;
    }
}




























