package com.severgroup.service;

import com.opencsv.CSVWriter;
import com.severgroup.to.AvgRecord;
import com.severgroup.util.FileUtil;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.opencsv.ICSVWriter.*;
import static com.severgroup.util.Config.*;

public class CsvWriterServiceImpl implements WriterService<AvgRecord> {
    private final static Logger LOGGER = Logger.getLogger(CsvWriterServiceImpl.class);

    private List<AvgRecord> records;
    private String filename;

    public CsvWriterServiceImpl(String filename, List<AvgRecord> records) {
        this.records = records;
        this.filename = filename;
    }

    public void write() {
        try (FileOutputStream fos = new FileOutputStream(Paths.get(WRITEPATH + "/avg_" + filename).toFile(), false);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(osw, DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END)
        ) {
            writer.writeNext(new String[]{"id", "url", "average"});
            Map<LocalDate, List<AvgRecord>> groupingCollect = new TreeMap<>(records.stream()
                    .collect(Collectors.groupingBy(AvgRecord::getDate)));

            groupingCollect.forEach(
                    (key, value) -> {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
                        String headerToWrite = formatter.format(key).toUpperCase();
                        writer.writeNext(new String[]{
                                headerToWrite
                        });
                        value.forEach(
                                avgRecord -> writer.writeNext(new String[]{avgRecord.getUserName(), avgRecord.getUrl(),
                                        String.valueOf(avgRecord.getAvgSeconds())}));
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUtil.rename(READPATH.resolve(filename), PARSEDPATH.resolve(filename));
    }

    @Override
    public void run() {
        LOGGER.debug("Thread for write : " + Thread.currentThread().getName());
        write();
    }


}
