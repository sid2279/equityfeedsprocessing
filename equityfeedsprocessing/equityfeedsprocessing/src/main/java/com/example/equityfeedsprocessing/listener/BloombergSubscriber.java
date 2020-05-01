package com.example.equityfeedsprocessing.listener;


import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.util.POJOValidator;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import com.google.gson.*;

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
import javax.validation.ConstraintViolation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Set;

@Component
@EnableJms
public class BloombergSubscriber {

    @Autowired
    private ProcessingLogic processingLogic;

    @Autowired
    private POJOValidator pojoValidator;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${bloomberg.outboundTopicName}")
    private String bloombergOutboundTopicName;

    @Value("${equityFeeds.errorTopicName}")
    private String errorTopicName;

    private static final Logger logger = LoggerFactory.getLogger(BloombergSubscriber.class);

    @JmsListener(destination = "${bloomberg.inboundTopicName}", containerFactory = "jmsListenerContainerFactory")
    public void bloombergProcessor(Message jsonMessage) {

        logger.info("Entered the Bloomberg Processor method.");

        logger.info("JSON Message received from ActiveMQ is: {} ", jsonMessage);

        String jsMessage = null;

        logger.info("Checking whether the JSON Message received from ActiveMQ is an instance of TextMessage");

        if(jsonMessage instanceof TextMessage) {

            logger.info("JSON Message received is an instance of TextMessage");

            logger.info("Casting the JSON Message received into TextMessage. ");

            TextMessage textMessage = (TextMessage) jsonMessage;

            try {
                jsMessage =  textMessage.getText();
            } catch (JMSException e) {
                logger.error("JMS Exception: {}", e);
            }

            if (jsMessage == null) {
                logger.error("The give JSON Message is null");
                jmsTemplate.convertAndSend(errorTopicName, textMessage);
                return;
            }

            logger.info("Calling deserialize method og Gson for LocalDate class.");

            final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {

                public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    logger.info("Inside Gson Deserializer");
                    return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
                }
            }).create();

            EquityFeeds equityFeeds = gson.fromJson(jsMessage, EquityFeeds.class);

            logger.info("Calling the validate method of the POJO validator class to Validate the POJO object.");

            Set<ConstraintViolation<EquityFeeds>> constraintViolations = pojoValidator.validate(equityFeeds);

            if (constraintViolations.size() > 0) {
                for (ConstraintViolation<EquityFeeds> violation : constraintViolations) {
                    logger.error(violation.getMessage());
                }
            } else {
                logger.info("Valid Object. Sending the object for processing.");
                processingLogic.processDataLogic(bloombergOutboundTopicName,equityFeeds);
            }

        }

    }

}
