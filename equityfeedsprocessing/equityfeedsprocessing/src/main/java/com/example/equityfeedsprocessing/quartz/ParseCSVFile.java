package com.example.equityfeedsprocessing.quartz;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ParseCSVFile {

    @Autowired
    private ProcessingLogic processingLogic;

    @Value("${nasdaq.outboundTopicName}")
    private String nasdaqOutboundTopicName;

    private static final Logger logger = LoggerFactory.getLogger(ParseCSVFile.class);

//    public static final String CSV_FILE = "src/main/resources/nasdaq.csv";
    public static final String CSV_FILE = "src/main/resources/nasdaqlarge.csv";

    public void parse() {

        Pattern pattern = Pattern.compile(",");

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {

            logger.info("Inside the parseCSVFile method of the parseCSVFile class.");

            logger.info("Now processing the csv file which has been downloaded from remote FTP Server to local system.");

            logger.info("Converting the csv file to POJO");

            List<EquityFeeds> equityFeeds = br.lines().skip(1).map(line -> {
                String[] x = pattern.split(line);
                return new EquityFeeds(x[0], x[1], x[2], x[3], LocalDate.parse(x[4]), Float.parseFloat(x[5]), x[6], x[7],Long.parseLong(x[8]));
            }).collect(Collectors.toList());

            logger.info("CSV file has been successfully converted to POJO.");

            logger.info(" START | Starting to process csv file.");

            for(EquityFeeds equity : equityFeeds) {

                logger.info("Equity Feeds POJO: {}",equity);

                logger.info("Starting to process each csv record one by one.");

                processingLogic.processDataLogic(nasdaqOutboundTopicName,equity);
            }

            logger.info(" END | Finished processing csv file.");

        } catch (IOException e) {
            logger.error("IO Exception: {}", e);
        }

    }

}