package com.severgroup.service;

import com.severgroup.to.AvgRecord;
import com.severgroup.util.Config;
import com.severgroup.util.TestData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvReaderServiceImplTest {
    private final static String regularfile = Config.READPATH + "regular.csv";
    private final static String corrfilename = Config.READPATH + "corrupt.csv";
    private final static String splitrecord = Config.READPATH + "split.csv";

    @Before
    public void init() {
        Config.init(Paths.get("usr/home/starlord/csvparsing"));
        try {
            Files.write(Paths.get(regularfile), TestData.regularRecord.getBytes());
            Files.write(Paths.get(splitrecord), TestData.splitRecord.getBytes());
            Files.write(Paths.get(corrfilename), "corrupt".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void regularCsvFileTest() {
        Path path = Paths.get(regularfile);
        CsvReaderServiceImpl csvReaderServiceImpl = new CsvReaderServiceImpl(path);
        List<AvgRecord> call = csvReaderServiceImpl.call();
        Assert.assertEquals(TestData.regularList, call);
    }

    @Test
    public void corruptedCsvFileTest() {
        Path path = Paths.get(corrfilename);
        CsvReaderServiceImpl csvReaderServiceImpl = new CsvReaderServiceImpl(path);
        Assert.assertNull(csvReaderServiceImpl.call());
    }

    @Test
    public void splitRecordForTwoDates() {
        Path path = Paths.get(splitrecord);
        CsvReaderServiceImpl csvReaderServiceImpl = new CsvReaderServiceImpl(path);
        List<AvgRecord> call = csvReaderServiceImpl.call();
        Assert.assertEquals(TestData.splitBean, call);
    }

}
