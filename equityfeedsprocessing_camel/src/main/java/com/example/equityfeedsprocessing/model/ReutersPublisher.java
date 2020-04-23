package com.example.equityfeedsprocessing.model;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ReutersPublisher {

	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		CamelContext _ctx = new DefaultCamelContext(); 
		_ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		_ctx.addRoutes(new RouteBuilder() {
			
			public void configure() throws Exception {
				
				onException(ValidationException.class).process(new Processor() {

		            public void process(Exchange exchange) throws Exception {
		            	System.out.println("Validation Exception Occurred. XML is not valid against XSD.");
		            }
		            
		        }).log("Received body").handled(true).end();
				
//				from("file:src/main/resources?fileName=reutersdata.csv")
				from("file:src/main/resources?fileName=reutersdatalarge.csv")
					.to("log:?level=INFO&showAll=true&showBody=true&showHeaders=true&multiline=true")
						.log(LoggingLevel.INFO, "Starting to process csv file one record at a time.")
						.log(LoggingLevel.INFO, "Splitting the records and tokenizing it by newline character.")						
					.split()						
					.tokenize("\n")		
						.log(LoggingLevel.INFO, "Starting to process csv file. Calling CSV to XML Transformation method.")
					.process(new CSVToXMLTransformation())
						.log(LoggingLevel.INFO, "Finished processing csv record.")
//					.to("file:src/main/resources/?fileName=equityfeeds.xml")
						.log(LoggingLevel.INFO, "Validating XML file against XSD.")
					.to("validator:file:src/main/resources/XMLSchema.xsd")
						.log(LoggingLevel.INFO, "XML file is valid against given XSD.")
						.log(LoggingLevel.INFO, "Streaming the data to JMS Topic: reuters.inbound.Topic")
					.split(body().tokenizeXML("equityFeeds", null)).streaming().to("jms:topic:reuters.inbound.Topic")
						.log(LoggingLevel.INFO, "Finished the route.");
			}
			
		});
		
		_ctx.start();
		 Thread.sleep(30000);
		_ctx.stop();	

	}

}

class CSVToXMLTransformation implements Processor {
	
	public void process(Exchange exchange) throws Exception {
		
		String myString = exchange.getIn().getBody(String.class);
		String[] lineSeparator = myString.split(System.getProperty("line.separator"));		
		StringBuffer sb = new StringBuffer();

		for (String lineData : lineSeparator){

			String[] commaSeparator = lineData.split(",");
			
			sb.append("<equityFeeds>");
			sb.append("<externalTransactionId>" + commaSeparator[0].toString() + "</externalTransactionId>");
			sb.append("<clientId>" + commaSeparator[1].toString() + "</clientId>");
			sb.append("<securityId>" + commaSeparator[2].toString() + "</securityId>");
			sb.append("<transactionType>" + commaSeparator[3].toString() + "</transactionType>");
			sb.append("<transactionDate>" + commaSeparator[4].toString() + "</transactionDate>");
			sb.append("<marketValue>" + commaSeparator[5].toString() + "</marketValue>");
			sb.append("<sourceSystem>" + commaSeparator[6].toString() + "</sourceSystem>");
			sb.append("<priorityFlag>" + commaSeparator[7].toString() + "</priorityFlag>");
			sb.append("<processingFee>" + commaSeparator[8].toString() + "</processingFee>");
			sb.append("</equityFeeds>");

		}

		exchange.getIn().setBody(sb.toString());
		
	}	
	
}
