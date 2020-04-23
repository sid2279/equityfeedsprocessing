package com.example.equityfeedsprocessing.listener;


import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRedisRepository;
import com.example.equityfeedsprocessing.repository.EquityFeedsRepository;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import com.example.equityfeedsprocessing.util.SavingEquityData;
import com.example.equityfeedsprocessing.util.XMLLocalDateDeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Component
@EnableJms
public class ReutersSubscriber {

    @Autowired
    private EquityFeedsRepository equityFeedsRepository;

    @Autowired
    private EquityFeedsRedisRepository equityFeedsRedisRepository;

    @Autowired
    private EquityFeedsRedisRepositoryImpl equityFeedsRedisRepositoryImpl;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SavingEquityData savingEquityData;

    @Autowired
    private ProcessingLogic processingLogic;

    @Value("${reuters.outboundTopicName}")
    private String reutersOutboundTopicName;

    private static final Logger logger = LoggerFactory.getLogger(ReutersSubscriber.class);

    @JmsListener(destination = "${reuters.inboundTopicName}", containerFactory = "jmsListenerContainerFactory")
    public void reutersProcessor(final Message xmlMessage) {

        logger.info("Entered the Reuters Processor method.");

        logger.info("XML Message received from ActiveMQ is: {} ", xmlMessage);

        String xmlData = null;
        EquityFeeds equityFeeds = null;

        logger.info("Checking whether the XML Message received from ActiveMQ is an instance of TextMessage");

        if (xmlMessage instanceof TextMessage) {

            logger.info("XML Message received is an instance of TextMessage");

            logger.info("Casting the XML Message received into TextMessage. ");

            TextMessage textMessage = (TextMessage) xmlMessage;
            try {
                xmlData = textMessage.getText();
            } catch (JMSException e) {
                logger.error("JMS Exception: {}", e);
            }

            logger.info("Calling generateEntity method to unmarshal the XML into a EquityFeeds POJO");

            equityFeeds = generateEntity(xmlData);

            logger.info("POJO returned after unmarshalling is: {}", equityFeeds);

            logger.info("Calling the Processing Logic class to process the POJO as per Intra-day / Normal transaction.");

            processingLogic.processDataLogic(reutersOutboundTopicName,equityFeeds);

        }

    }

    private static EquityFeeds generateEntity(String xml) {

        logger.info("Inside generateEntity method.");

        EquityFeeds equityFeeds = null;

        StringReader reader = new StringReader(xml);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(EquityFeeds.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setAdapter(new XMLLocalDateDeSerializer());
            equityFeeds = (EquityFeeds) jaxbUnmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            logger.error("JAXB Exception: ", e);
        }

        return equityFeeds;

    }

}
