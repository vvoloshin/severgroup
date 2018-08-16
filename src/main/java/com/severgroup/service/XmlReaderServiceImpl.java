package com.severgroup.service;


import com.severgroup.model.RowSessionRecord;
import com.severgroup.util.RowRecordList;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XmlReaderServiceImpl extends AbstractReaderService {
    private final static Logger LOGGER = Logger.getLogger(XmlReaderServiceImpl.class);

    public XmlReaderServiceImpl(Path fileName) {
        super(fileName);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<RowSessionRecord> getRecords() {
        JAXBContext context = null;
        RowRecordList rowRecordList = null;
        try {
            context = JAXBContext.newInstance(RowRecordList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = fileName.toFile();
            rowRecordList = (RowRecordList) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return rowRecordList != null ? rowRecordList.getList() : new ArrayList<>();
    }

}




























