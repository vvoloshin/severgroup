package com.severgroup.util;

import com.severgroup.to.AvgRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {
    public static final String regularRecord =
            "timestamp,id,url,seconds\n" +
                    "1514791800,Ivan,yandex.ru,100\n" +
                    "1514797200,Ivan,yandex.ru,200\n" +
                    "1514826000,Aizek,nasa.com,150\n" +
                    "1514840370,Ivan,google.com,450\n" +
                    "1514878200,Ivan,nasa.com,50\n" +
                    "1514881800,Aizek,yandex.ru,600\n" +
                    "1514966400,Aizek,google.com,70\n" +
                    "1514991600,Aizek,google.com,50\n" +
                    "1515129900,Aizek,nasa.com,50\n" +
                    "1515150000,Ivan,nasa.com,300\n" +
                    "1515582000,Ivan,yandex.ru,300\n" +
                    "1515588300,Ivan,yandex.ru,400\n" +
                    "1515597300,Ivan,nasa.com,150\n" +
                    "1515617940,Ivan,google.com,300";

    public static final List<AvgRecord> regularList = new ArrayList<AvgRecord>(Arrays.asList(
            new AvgRecord(LocalDate.of(2018, 01, 01), "Ivan", "google.com", 29.0),
            new AvgRecord(LocalDate.of(2018, 01, 01), "Ivan", "yandex.ru", 150.0),
            new AvgRecord(LocalDate.of(2018, 01, 01), "Aizek", "nasa.com", 150.0),

            new AvgRecord(LocalDate.of(2018, 01, 02), "Ivan", "google.com", 421.0),
            new AvgRecord(LocalDate.of(2018, 01, 02), "Ivan", "nasa.com", 50.0),
            new AvgRecord(LocalDate.of(2018, 01, 02), "Aizek", "yandex.ru", 600.0),

            new AvgRecord(LocalDate.of(2018, 01, 03), "Aizek", "google.com", 60.0),

            new AvgRecord(LocalDate.of(2018, 01, 05), "Ivan", "nasa.com", 300.0),
            new AvgRecord(LocalDate.of(2018, 01, 05), "Aizek", "nasa.com", 50.0),

            new AvgRecord(LocalDate.of(2018, 01, 10), "Ivan", "google.com", 59.0),
            new AvgRecord(LocalDate.of(2018, 01, 10), "Ivan", "yandex.ru", 350.0),
            new AvgRecord(LocalDate.of(2018, 01, 10), "Ivan", "nasa.com", 150.0),

            new AvgRecord(LocalDate.of(2018, 01, 11), "Ivan", "google.com", 241.0)
    ));

    public static final String splitRecord = "timestamp,id,url,seconds\n" +
            "1455915585,user1,http://ru.wikipedia.org,300";

    public static final List<AvgRecord> splitBean = new ArrayList<>(Arrays.asList(
            new AvgRecord(LocalDate.of(2016, 02, 19), "user1", "http://ru.wikipedia.org", 14),
            new AvgRecord(LocalDate.of(2016, 02, 20), "user1", "http://ru.wikipedia.org", 286)
    ));

}
