package com.severgroup.service;


import com.opencsv.CSVParser;
import com.opencsv.bean.CsvToBeanBuilder;
import com.severgroup.model.CsvRecord;
import com.severgroup.to.AvgRecord;
import com.severgroup.util.FileUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static com.severgroup.util.Config.ERRORPATH;
import static com.severgroup.util.Config.READPATH;
import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvReaderService implements Callable<List<AvgRecord>> {
    private final static Logger LOGGER = Logger.getLogger(CsvReaderService.class);
    private Path fileName;

    public CsvReaderService(Path fileName) {
        this.fileName = fileName;
    }

    private long toNextDaySeconds(long firstTimeStamp, long seconds) {
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

    private List<CsvRecord> splitNext(List<CsvRecord> csvRecords) {
        List<CsvRecord> result = new ArrayList<>();
        for (int i = 0; i < csvRecords.size(); i++) {
            CsvRecord record = csvRecords.get(i);
            long secondDifference = toNextDaySeconds(record.getTimestamp(), record.getSeconds());
            if (secondDifference != 0) {
                //breaking the old record of the session user
                long sessionTime = record.getSeconds();
                //the old record, changing the time of the session, put (secondsBeforeMidnight-1)
                record.setSeconds(secondDifference);//-1
                //after it insert a new record, the session secondsAfterMidnight = seconds - secondsBeforeMidnight
                CsvRecord nextDayRecord = new CsvRecord(record);
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
    private List<CsvRecord> getRecords() {
        List<CsvRecord> result = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(fileName, UTF_8)) {
            result = new CsvToBeanBuilder(reader)
                    .withType(CsvRecord.class)
                    .withSeparator(CSVParser.DEFAULT_SEPARATOR)
                    .build()
                    .parse();
        } catch (RuntimeException | IOException e) {
            LOGGER.error("error, CSV file is corrupted!");
            result.clear();
            FileUtil.rename(READPATH.resolve(fileName.getFileName()), ERRORPATH.resolve(fileName.getFileName()));

        }
        return result;
    }

    private Map<LocalDate, List<CsvRecord>> groupDate(List<CsvRecord> records) {
        Map<LocalDate, List<CsvRecord>> result = new TreeMap<>();
        records.forEach(record -> {
            Instant instant = Instant.ofEpochMilli(record.getTimestamp() * 1000);
            LocalDate dateFromMap = LocalDateTime.ofInstant(instant, ZoneOffset.of("+3")).toLocalDate();
            List<CsvRecord> groupList;
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

    private List<AvgRecord> getAverage(Map<LocalDate, List<CsvRecord>> groupList) {
        List<AvgRecord> result = new LinkedList<>();
        groupList.forEach(
                (date, list) -> {
                    Map<String, List<CsvRecord>> userGroupMap = list.stream()//-> User, List<RECORD>
                            .sorted(Comparator.comparing(CsvRecord::getUserId))
                            .collect(Collectors.groupingBy(CsvRecord::getUserId));
                    userGroupMap.forEach(
                            (userName, listRecord) -> {
                                Map<String, Double> urlGroupMap = listRecord.stream()//-> URL, AVG
                                        .collect(Collectors.groupingBy(CsvRecord::getUrl, (Collectors.averagingDouble(CsvRecord::getSeconds))));
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
        List<CsvRecord> csvRecordBeforeSplit = getRecords();
        if (csvRecordBeforeSplit != null && csvRecordBeforeSplit.size() != 0) {
            List<CsvRecord> csvRecordsAfterSplit = splitNext(csvRecordBeforeSplit);
            Map<LocalDate, List<CsvRecord>> dateMap = groupDate(csvRecordsAfterSplit);
            return getAverage(dateMap);
        }
        return null;
    }
}




























