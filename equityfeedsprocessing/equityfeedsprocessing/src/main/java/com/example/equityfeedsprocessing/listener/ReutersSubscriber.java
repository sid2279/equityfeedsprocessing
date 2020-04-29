package com.example.equityfeedsprocessing.listener;



import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.util.POJOValidator;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import com.example.equityfeedsprocessing.util.XMLLocalDateDeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Set;

@Component
@EnableJms
public class ReutersSubscriber {

    @Autowired
    private ProcessingLogic processingLogic;

    @Autowired
    private POJOValidator pojoValidator;

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

            logger.info("Calling the validate method of the POJO validator class to Validate the POJO object.");

            Set<ConstraintViolation<EquityFeeds>> constraintViolations = pojoValidator.validate(equityFeeds);

            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<EquityFeeds> violation : constraintViolations) {
                    logger.error(violation.getMessage());
                }
            } else {
                logger.info("Valid Object. Sending the object for processing.");
                processingLogic.processDataLogic(reutersOutboundTopicName,equityFeeds);
            }

        }

    }

    private EquityFeeds generateEntity(String xml) {

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
