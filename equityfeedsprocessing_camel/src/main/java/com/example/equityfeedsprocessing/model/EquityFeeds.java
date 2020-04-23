package com.example.equityfeedsprocessing.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@CsvRecord(separator = ",",skipFirstLine = true)
public class EquityFeeds implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@DataField(pos = 1)	
	private String externalTransactionId;
	
	@DataField(pos = 2)
	private String clientId;
	
	@DataField(pos = 3)
	private String securityId;
	
	@DataField(pos = 4)
	private String transactionType;
	
	@DataField(pos = 5, pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate transactionDate;
	
	@DataField(pos = 6)
	private float marketValue; 
	
	@DataField(pos = 7)
	private String sourceSystem;
	
	@DataField(pos = 8)
	private String priorityFlag;
	
	@DataField(pos = 9)
	private long processingFee;

	public EquityFeeds() {
	
	}	
		
	public String getExternalTransactionId() {
		return externalTransactionId;
	}
	
	public void setExternalTransactionId(String externalTransactionId) {
		this.externalTransactionId = externalTransactionId;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getSecurityId() {
		return securityId;
	}
	
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")	
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public float getMarketValue() {
		return marketValue;
	}
	
	public void setMarketValue(float marketValue) {
		this.marketValue = marketValue;
	}
	
	public String getSourceSystem() {
		return sourceSystem;
	}
	
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	
	public String getPriorityFlag() {
		return priorityFlag;
	}
	
	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	} 
	
	public long getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(long processingFee) {
		this.processingFee = processingFee;
	}
	
}
