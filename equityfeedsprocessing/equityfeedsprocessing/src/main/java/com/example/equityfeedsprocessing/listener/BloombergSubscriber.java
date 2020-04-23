package com.example.equityfeedsprocessing.listener;


import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRedisRepository;
import com.example.equityfeedsprocessing.repository.EquityFeedsRepository;
import com.example.equityfeedsprocessing.util.ProcessingLogic;
import com.example.equityfeedsprocessing.util.SavingEquityData;
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
import java.lang.reflect.Type;
import java.time.LocalDate;

@Component
@EnableJms
public class BloombergSubscriber {

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

    @Value("${bloomberg.outboundTopicName}")
    private String bloombergOutboundTopicName;

    private static final Logger logger = LoggerFactory.getLogger(BloombergSubscriber.class);

//    @JmsListener(destination = "${bloomberg.inboundTopicName}", containerFactory = "jmsListenerContainerFactory")
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

            logger.info("Calling deserialize method og Gson for LocalDate class.");

            final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {

                public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    logger.info("Inside Gson Deserializer");
                    return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
                }
            }).create();

            EquityFeeds equityFeeds = gson.fromJson(jsMessage, EquityFeeds.class);

            logger.info("POJO returned after deserialization is: {}", equityFeeds);

            logger.info("Calling the Processing Logic class to process the POJO as per Intra-day / Normal transaction.");

            processingLogic.processDataLogic(bloombergOutboundTopicName,equityFeeds);

        }

    }

}
