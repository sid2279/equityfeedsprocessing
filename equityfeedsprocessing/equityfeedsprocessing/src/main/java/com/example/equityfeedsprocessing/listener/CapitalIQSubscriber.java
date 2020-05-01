package com.example.equityfeedsprocessing.listener;


import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.util.POJOValidator;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
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
import javax.jms.ObjectMessage;
import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
@EnableJms
public class CapitalIQSubscriber {

    @Autowired
    private ProcessingLogic processingLogic;

    @Autowired
    private POJOValidator pojoValidator;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${capitalIQ.outboundTopicName}")
    private String capitalIQOutboundTopicName;

    @Value("${equityFeeds.errorTopicName}")
    private String errorTopicName;

    private static final Logger logger = LoggerFactory.getLogger(CapitalIQSubscriber.class);

    @JmsListener(destination = "${capitalIQ.inboundTopicName}", containerFactory = "jmsListenerContainerFactory")
    public void capitalIQProcessor(final Message objectMessage) {

        logger.info("Entered the CapitalIQ Processor method.");

        logger.info("Java Object Message received from ActiveMQ is: {} ", objectMessage);

        logger.info("Checking whether the Java Object Message received from ActiveMQ is an instance of ObjectMessage");

        if(objectMessage instanceof ObjectMessage) {

            logger.info("Java Object Message received is an instance of ObjectMessage");

            logger.info("Casting the Java Object Message received into ObjectMessage. ");

            ObjectMessage objMessage = (ObjectMessage) objectMessage;

            Object object = null;

            try {
                object = objMessage.getObject();
            } catch (JMSException e) {
                logger.error("JMS Exception: {}", e);
            }

            if (object == null) {
                logger.error("The give Object Message is null");
                jmsTemplate.convertAndSend(errorTopicName, objMessage);
                return;
            }

            EquityFeeds equityFeeds = (EquityFeeds) object;

            logger.info("Calling the validate method of the POJO validator class to Validate the POJO object.");

            Set<ConstraintViolation<EquityFeeds>> constraintViolations = pojoValidator.validate(equityFeeds);

            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<EquityFeeds> violation : constraintViolations) {
                    logger.error(violation.getMessage());
                }
            } else {
                logger.info("Valid Object. Sending the object for processing.");
                processingLogic.processDataLogic(capitalIQOutboundTopicName,equityFeeds);
            }

        }

    }

}
