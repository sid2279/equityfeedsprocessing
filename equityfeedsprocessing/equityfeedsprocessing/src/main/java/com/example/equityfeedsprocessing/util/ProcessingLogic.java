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

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
public class ProcessingLogic {

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

    @Autowired
    private SavingEquityData savingEquityData;

    @Autowired
    private ProcessingRule processingRule;

    private static final Logger logger = LoggerFactory.getLogger(ProcessingLogic.class);

    public void processDataLogic(String topicName, EquityFeeds equityFeeds) {

//        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        logger.info("Inside the processDataLogic method of the ProcessingLogic class");

        logger.info("Processing Rules as per Intra-day / Normal Transaction are to be applied.");

        logger.info("Key as a combination of clientId, securityId and transactionDate to be checked in REDIS cache.");

        String key = equityFeeds.getClientId() + equityFeeds.getSecurityId() + equityFeeds.getTransactionDate();

        logger.info("Key generated is: {}", key);

        logger.info("Checking whether the key is found in the REDIS cache or not.");

        Optional<EquityFeeds> processedEquityFeeds = Optional.ofNullable(equityFeedsService.findById(key));

        if (processedEquityFeeds.isPresent()) {

            logger.info("Key {} was found in REDIS cache.", key);

            logger.info("Checking the additional condition of transactionType i.e. one should be buy and other should be SELL");

            if ((equityFeeds.getTransactionType().equals("BUY") && processedEquityFeeds.get().getTransactionType().equals("SELL")) || ((equityFeeds.getTransactionType().equals("SELL") && processedEquityFeeds.get().getTransactionType().equals("BUY")))) {

                logger.info("The Trade is an Intra-day transaction. One trade is BUY and the other is SELL.");

                logger.info("Charging $10 for the newer transaction as intra-day fee.");

                equityFeeds.setProcessingFee(10);

                savingEquityData.savingData(topicName, equityFeeds);

            } else {

                logger.info("The clientId, securityId and transactionDate of the trades match but the transactionType are not opposite.");

                logger.info("Treating the newer trade as a Normal transaction.");

//                EquityFeeds normalProcessedEquityFeeds = ProcessingRule.processData(equityFeeds);
                EquityFeeds normalProcessedEquityFeeds = processingRule.processData(equityFeeds);

                savingEquityData.savingData(topicName, normalProcessedEquityFeeds);

            }

        } else {

            logger.info("Key is not found in the REDIS cache. Treating this as a Normal transaction.");

//            EquityFeeds normalProcessedEquityFeeds = ProcessingRule.processData(equityFeeds);

            EquityFeeds normalProcessedEquityFeeds = processingRule.processData(equityFeeds);

            savingEquityData.savingData(topicName, normalProcessedEquityFeeds);

        }

    }
}
