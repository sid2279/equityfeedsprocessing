package com.example.equityfeedsprocessing.listener;


import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileNotFoundException;

class ReutersSubscriberTest {

    private EquityFeeds equityFeeds;

    @Test
    public void testXMLToObject() throws JAXBException, FileNotFoundException {
        File file = new File("C:\\Personal\\SpringBoot\\SpringBootProjects\\equityfeedsprocessingupdated\\equityfeedsprocessingupdated\\src\\main\\resources\\equityFeeds.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(EquityFeeds.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        equityFeeds = (EquityFeeds) unmarshaller.unmarshal(file);
        System.out.println(equityFeeds);
    }
}