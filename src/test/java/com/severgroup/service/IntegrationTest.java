package com.severgroup.service;

import com.severgroup.controller.MainController;
import com.severgroup.controller.WebController;
import com.severgroup.repository.AvgRepository;
import com.severgroup.to.AvgRecord;
import com.severgroup.util.Config;
import com.severgroup.util.TestData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {
    private final static String regularfile = Config.READPATH + "regular.csv";
    private final static String corrfilename = Config.READPATH + "corrupt.csv";
    private final static String splitrecord = Config.READPATH + "split.csv";

    @Autowired
    private AvgRepository repository;
    @Autowired
    private MainController mainController;
    @Autowired
    private WebController webController;
    @Autowired
    private MockMvc mockMvc;


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

    //Controller test
    @Test
    public void notNullController() {
        Assert.assertNotNull(mainController);
        Assert.assertNotNull(webController);
    }

    //Web test
    @Test
    public void mvcGetRootPage() throws Exception {
        this.mockMvc.perform(get("/records"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("WEB-INF/jsp/records_List.jsp"));
    }

    @Test
    public void mvcErrorRootPage() throws Exception {
        this.mockMvc.perform(get("/something"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void mvcNotFoundUser() throws Exception {
        this.mockMvc.perform(get("/get?username=Zapp"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("WEB-INF/jsp/notfound.jsp"));
    }

    @Test
    public void mvcRedirect() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/records"));
    }

    //Database test
    @Test
    public void whenFindByName_thenReturnUser() {
        AvgRecord record = new AvgRecord(LocalDate.of(2022, 1, 1), "UserTest", "https://ya.ru", 333);
        repository.save(record);
        AvgRecord found = repository.findByUserName(record.getUserName()).get(0);
        assertThat(found.getUserName()).isEqualTo(record.getUserName());
    }

    @Test
    public void whenFindErrorName_thenReturnEmptyList() {
        assertThat(repository.findByUserName("ErrorName")).isEqualTo(new ArrayList<AvgRecord>());
    }

    //Csv test
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
