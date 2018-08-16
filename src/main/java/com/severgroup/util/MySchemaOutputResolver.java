package com.severgroup.util;

import org.apache.log4j.Logger;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class MySchemaOutputResolver extends SchemaOutputResolver {
    private final static Logger LOGGER = Logger.getLogger(MySchemaOutputResolver.class);

    private File className;

    MySchemaOutputResolver(File className) {
        this.className = className;
    }

    @Override
    public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
        File file = className;
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        LOGGER.debug("generate XSD: " + file.toURI().toURL().toString());
        return result;
    }
}
