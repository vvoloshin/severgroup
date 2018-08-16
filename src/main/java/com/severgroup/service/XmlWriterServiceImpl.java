package com.severgroup.service;

import com.severgroup.to.AvgRecord;
import com.severgroup.util.AvgRecordList;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static com.severgroup.util.Config.WRITEXMLPATH;

public class XmlWriterServiceImpl implements WriterService<AvgRecord> {
    private final static Logger LOGGER = Logger.getLogger(XmlWriterServiceImpl.class);

    private List<AvgRecord> records;
    private String filename;

    public XmlWriterServiceImpl(String filename, List<AvgRecord> records) {
        this.records = records;
        this.filename = filename;
    }

    @Override
    public void write() {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(AvgRecordList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            String newextensionfile = filename.substring(0, filename.length() - 3) + "xml";
            File file = Paths.get(WRITEXMLPATH + "/avg_" + newextensionfile).toFile();
            LOGGER.debug("Xml Writer write out file to: " + file.toString());
            AvgRecordList listtomarshall = new AvgRecordList();
            listtomarshall.setList(records);
            marshaller.marshal(listtomarshall, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        LOGGER.debug("Thread for write XML out: " + Thread.currentThread().getName());
        write();
    }
}
