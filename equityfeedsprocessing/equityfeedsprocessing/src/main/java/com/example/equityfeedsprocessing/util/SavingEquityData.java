package com.example.equityfeedsprocessing.util;


import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRedisRepository;
import com.example.equityfeedsprocessing.repository.EquityFeedsRepository;
import com.example.equityfeedsprocessing.service.EquityFeedsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SavingEquityData {

    @Autowired
    private EquityFeedsRepository equityFeedsRepository;

    @Autowired
    private EquityFeedsRedisRepository equityFeedsRedisRepository;

    @Autowired
    private EquityFeedsRedisRepositoryImpl equityFeedsRedisRepositoryImpl;

    @Autowired
    private EquityFeedsService equityFeedsService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SavingEquityData.class);

    @Transactional
    public void savingData(String topicName, EquityFeeds equityFeeds) {

        logger.info("Inside savingData method of the SavingEquityData class.");

        logger.info("Queue Name: {}",topicName);

        logger.info("EquityFeeds POJO received is: {}", equityFeeds);

        logger.info("Saving the record - equityFeeds in the database as well as in REDIS cache.");

        try {

            logger.info("Saving the record in the database");

            equityFeedsRepository.save(equityFeeds);

            logger.info("Saving the record in the REDIS Cache");

            equityFeedsService.save(equityFeeds);

            logger.info("Saving the externalTransactionId, clientId and securityId in REDIS Cache for the search functionality via REST API.");

            equityFeedsService.saveExternalTransactionId(equityFeeds);

            equityFeedsService.saveClientId(equityFeeds);

            equityFeedsService.saveSecurityId(equityFeeds);

            logger.info("Also sending the record to the outbound queue.");

            jmsTemplate.convertAndSend(topicName, equityFeeds);
        } catch (Exception e){
            logger.error("Exception caught: {}", e.getMessage());
        }

    }

}
