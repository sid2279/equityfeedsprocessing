package com.example.equityfeedsprocessing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class XMLLocalDateDeSerializer extends XmlAdapter<String, LocalDate> {

    private static final Logger logger = LoggerFactory.getLogger(XMLLocalDateDeSerializer.class);

    public LocalDate unmarshal(String v) throws Exception {

        logger.info("Inside Unmarshal");

        logger.info("LocalDate: {}",v);

        return LocalDate.parse(v);
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
