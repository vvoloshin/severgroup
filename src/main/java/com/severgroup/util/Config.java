package com.severgroup.util;

import com.severgroup.to.AvgRecord;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    public static Path READPATH;
    public static Path WRITECSVPATH;
    public static Path WRITEXMLPATH;
    public static Path ERRORPATH;
    public static Path PARSEDPATH;
    public static Path XSDSCHEMA;

    public static void init(Path path) {
        Properties prop = new Properties();
        try (InputStream is = new FileInputStream(String.valueOf(path))) {
            if (is.available() == 0) {
                throw new IllegalArgumentException("error, properties file is empty or corrupted");
            } else {
                prop.load(is);
                READPATH = Paths.get(prop.getProperty("dir.read"));
                WRITECSVPATH = Paths.get(prop.getProperty("dir.writecsv"));
                WRITEXMLPATH = Paths.get(prop.getProperty("dir.writexml"));
                ERRORPATH = Paths.get(prop.getProperty("dir.error"));
                PARSEDPATH = Paths.get(prop.getProperty("dir.parsed"));
                XSDSCHEMA = Paths.get(prop.getProperty("dir.xsd"));

                generateXsd();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void generateXsd() {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(AvgRecord.class);
            SchemaOutputResolver resolver = new MySchemaOutputResolver(new File(XSDSCHEMA.toString() + "/outrecord.xsd"));
            context.generateSchema(resolver);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
