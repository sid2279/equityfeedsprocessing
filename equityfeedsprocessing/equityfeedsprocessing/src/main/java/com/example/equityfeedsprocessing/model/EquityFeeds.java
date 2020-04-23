package com.example.equityfeedsprocessing.model;

import com.example.equityfeedsprocessing.util.XMLLocalDateDeSerializer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name="equityFeeds")
@Entity
@Table(name = "transaction", catalog = "equityFeedsProcessing")
public class EquityFeeds implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "externalTransactionId")
    private String externalTransactionId;

    @Column(name = "clientId")
    private String clientId;

    @Column(name = "securityId")
    private String securityId;

    @Column(name = "transactionType")
    private String transactionType;

    @Column(name = "transactionDate")
    private LocalDate transactionDate;

    @Column(name = "marketValue")
    private float marketValue;

    @Column(name = "sourceSystem")
    private String sourceSystem;

    @Column(name = "priorityFlag")
    private String priorityFlag;

    @Column(name = "processingFee")
    private long processingFee;

    public EquityFeeds() {
    }

    public EquityFeeds(int id, String externalTransactionId, String clientId, String securityId, String transactionType, LocalDate transactionDate, float marketValue, String sourceSystem, String priorityFlag, long processingFee) {
        this.id = id;
        this.externalTransactionId = externalTransactionId;
        this.clientId = clientId;
        this.securityId = securityId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.marketValue = marketValue;
        this.sourceSystem = sourceSystem;
        this.priorityFlag = priorityFlag;
        this.processingFee = processingFee;
    }

    public EquityFeeds(String externalTransactionId, String clientId, String securityId, String transactionType, LocalDate transactionDate, float marketValue, String sourceSystem, String priorityFlag, long processingFee) {
        this.externalTransactionId = externalTransactionId;
        this.clientId = clientId;
        this.securityId = securityId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.marketValue = marketValue;
        this.sourceSystem = sourceSystem;
        this.priorityFlag = priorityFlag;
        this.processingFee = processingFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    @XmlElement
    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public String getClientId() {
        return clientId;
    }

    @XmlElement
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecurityId() {
        return securityId;
    }

    @XmlElement
    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    @XmlElement
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @XmlJavaTypeAdapter(value = XMLLocalDateDeSerializer.class)
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    @XmlElement
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public float getMarketValue() {
        return marketValue;
    }

    @XmlElement
    public void setMarketValue(float marketValue) {
        this.marketValue = marketValue;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    @XmlElement
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getPriorityFlag() {
        return priorityFlag;
    }

    @XmlElement
    public void setPriorityFlag(String priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public long getProcessingFee() {
        return processingFee;
    }

    @XmlElement
    public void setProcessingFee(long processingFee) {
        this.processingFee = processingFee;
    }

    @Override
    public String toString() {
        return "EquityFeeds{" +
                "id=" + id +
                ", externalTransactionId='" + externalTransactionId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", securityId='" + securityId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", marketValue=" + marketValue +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", priorityFlag='" + priorityFlag + '\'' +
                ", processingFee=" + processingFee +
                '}';
    }

}
