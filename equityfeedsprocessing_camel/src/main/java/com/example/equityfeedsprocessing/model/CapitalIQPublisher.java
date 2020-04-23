package com.example.equityfeedsprocessing.model;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

public class CapitalIQPublisher {

	public static void main(String[] args) throws Exception {
		final BindyCsvDataFormat bindy = new BindyCsvDataFormat(EquityFeeds.class);
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		CamelContext _ctx = new DefaultCamelContext(); 
		_ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		_ctx.addRoutes(new RouteBuilder() {
			
			public void configure() throws Exception {
				
				onException(Exception.class).process(new Processor() {

		            public void process(Exchange exchange) throws Exception {
		            	System.out.println("Exception Occurred.");
		            }
		            
		        }).log("Received body").handled(true).end();
				
				from("file:src/main/resources?fileName=capitalIQdata.csv")
					.to("log:?level=INFO&showAll=true&showBody=true&showHeaders=true&multiline=true")
					.log(LoggingLevel.INFO, "Starting to process csv file.")
					.log(LoggingLevel.INFO, "Unmarshalling the csv file using BindyCsvDataFormat.")	
				.unmarshal(bindy)
					.log(LoggingLevel.INFO, "Finished unmarshalling the data.")
					.log(LoggingLevel.INFO, "Splitting the body of each record.")				
				.split(body())
				.streaming().to("jms:topic:capitalIQ.inbound.Topic")
					.log(LoggingLevel.INFO, "Finished streaming the data to the CapitalIQ inbound Topic.");

			}
			
		});
		
		_ctx.start();
		 Thread.sleep(45000);
		_ctx.stop();	

	}

}
