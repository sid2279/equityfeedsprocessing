package com.example.equityfeedsprocessing.listener;

import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRedisRepository;
import com.example.equityfeedsprocessing.repository.EquityFeedsRepository;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import com.example.equityfeedsprocessing.util.SavingEquityData;
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

@Component
@EnableJms
public class CapitalIQSubscriber {

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

    @Value("${capitalIQ.outboundTopicName}")
    private String capitalIQOutboundTopicName;

    private static final Logger logger = LoggerFactory.getLogger(CapitalIQSubscriber.class);

//    @JmsListener(destination = "${capitalIQ.inboundTopicName}", containerFactory = "jmsListenerContainerFactory")
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

            EquityFeeds equityFeeds = (EquityFeeds) object;

            logger.info("POJO returned is: {}", equityFeeds);

            logger.info("Calling the Processing Logic class to process the POJO as per Intra-day / Normal transaction.");

            processingLogic.processDataLogic(capitalIQOutboundTopicName,equityFeeds);

        }

    }

}
