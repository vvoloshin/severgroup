package com.severgroup.service;


import com.opencsv.CSVParser;
import com.opencsv.bean.CsvToBeanBuilder;
import com.severgroup.model.RowSessionRecord;
import com.severgroup.util.FileUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.severgroup.util.Config.ERRORPATH;
import static com.severgroup.util.Config.READPATH;
import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvReaderServiceImpl extends AbstractReaderService {
    private final static Logger LOGGER = Logger.getLogger(CsvReaderServiceImpl.class);

    public CsvReaderServiceImpl(Path fileName) {
        super(fileName);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<RowSessionRecord> getRecords() {
        List<RowSessionRecord> result = new ArrayList<>();
        try (
                BufferedReader reader = Files.newBufferedReader(fileName, UTF_8)) {
            result = new CsvToBeanBuilder(reader)
                    .withType(RowSessionRecord.class)
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

}




























