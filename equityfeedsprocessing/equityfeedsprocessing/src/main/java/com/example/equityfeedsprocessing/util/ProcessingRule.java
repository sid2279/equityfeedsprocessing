package com.example.equityfeedsprocessing.util;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProcessingRule {

     private static final Logger logger = LoggerFactory.getLogger(ProcessingRule.class);

     public EquityFeeds processData(EquityFeeds equityFeeds) {

        logger.info("Inside processData method of the ProcessingRule class.");

        if (equityFeeds.getPriorityFlag().equals("Y")) {
            logger.info("Priority Flag of the trade is Y");
            logger.info("Charging the trade / transaction $500 as processing Fee.");
            equityFeeds.setProcessingFee(500);
        } else if (equityFeeds.getPriorityFlag().equals("N") && (equityFeeds.getTransactionType().equals("SELL") || (equityFeeds.getTransactionType().equals("WITHDRAW")))) {
            logger.info("Priority Flag of the trade / transaction is N and Transaction Type is SELL / WITHDRAW.");
            logger.info("Charging the trade / transaction $100 as processing Fee.");
            equityFeeds.setProcessingFee(100);
        } else if (equityFeeds.getPriorityFlag().equals("N") && (equityFeeds.getTransactionType().equals("BUY") || (equityFeeds.getTransactionType().equals("DEPOSIT")))) {
            logger.info("Priority Flag of the trade / transaction is N and Transaction Type is BUY / DEPOSIT.");
            logger.info("Charging the trade / transaction $50 as processing Fee.");
            equityFeeds.setProcessingFee(50);
        }

        return equityFeeds;

    }
}
