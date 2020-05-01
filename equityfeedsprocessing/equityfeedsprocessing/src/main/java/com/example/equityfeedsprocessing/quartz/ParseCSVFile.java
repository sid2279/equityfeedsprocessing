package com.example.equityfeedsprocessing.quartz;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.util.POJOValidator;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ParseCSVFile {

    @Autowired
    private ProcessingLogic processingLogic;

    @Autowired
    private POJOValidator pojoValidator;

    @Value("${nasdaq.outboundTopicName}")
    private String nasdaqOutboundTopicName;

    private static final Logger logger = LoggerFactory.getLogger(ParseCSVFile.class);

    public static final String CSV_FILE = "src/main/resources/nasdaqdata_invalid_1.csv";

    public void parse() {

        Pattern pattern = Pattern.compile(",");

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {

            logger.info("Inside the parseCSVFile method of the parseCSVFile class.");

            logger.info("Now processing the csv file which has been downloaded from remote FTP Server to local system.");

            logger.info("Converting the csv file to POJO");

            List<EquityFeeds> equityFeeds = br.lines().skip(1).map(line -> {
                String[] x = pattern.split(line);
                return new EquityFeeds.EquityFeedsBuilder().setExternalTransactionId(x[0]).setClientId(x[1]).setSecurityId(x[2]).setTransactionType(x[3]).setTransactionDate(LocalDate.parse(x[4])).setMarketValue(Float.parseFloat(x[5])).setSourceSystem(x[6]).setPriorityFlag(x[7]).setProcessingFee(Long.parseLong(x[8])).build();
            }).collect(Collectors.toList());

            logger.info("CSV file has been successfully converted to POJO.");

            logger.info(" START | Starting to process csv file.");

            for(EquityFeeds equity : equityFeeds) {

                logger.info("Equity Feeds POJO: {}",equity);

                logger.info("Starting to process each csv record one by one.");

                Set<ConstraintViolation<EquityFeeds>> constraintViolations = pojoValidator.validate(equity);

                if (constraintViolations.size() > 0) {
                    for (ConstraintViolation<EquityFeeds> violation : constraintViolations) {
                        logger.error(violation.getMessage());
                    }
                } else {
                    logger.info("Valid Object. Sending the object for processing.");
                    processingLogic.processDataLogic(nasdaqOutboundTopicName,equity);
                }

            }

            logger.info(" END | Finished processing csv file.");

        } catch (IOException e) {
            logger.error("IO Exception: {}", e);
        }

    }

}
