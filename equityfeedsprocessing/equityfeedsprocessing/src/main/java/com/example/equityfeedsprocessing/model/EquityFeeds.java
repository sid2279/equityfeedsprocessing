package com.example.equityfeedsprocessing.model;

import com.example.equityfeedsprocessing.util.XMLLocalDateDeSerializer;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotEmpty(message = "External Transaction Id cannot be null or empty")
    @Pattern(regexp = "SAPEXTXN[1-9]+[0-9]*")
    @Column(name = "externalTransactionId")
    private String externalTransactionId;

    @NotEmpty(message = "Client Id cannot be null or empty")
    @Pattern(regexp = "AP|AS|GS|HJ")
    @Column(name = "clientId")
    private String clientId;

    @NotEmpty(message = "Security Id cannot be null or empty")
    @Pattern(regexp = "REL|RELIND|ICICI|HINDALCO")
    @Column(name = "securityId")
    private String securityId;

    @NotEmpty(message = "Transaction Type cannot be null or empty")
    @Pattern(regexp = "BUY|SELL|DEPOSIT|WITHDRAW")
    @Column(name = "transactionType")
    private String transactionType;

    @PastOrPresent
    @Column(name = "transactionDate")
    private LocalDate transactionDate;

    @Digits(integer = 5, fraction = 2)
    @Column(name = "marketValue")
    private float marketValue;

    @NotEmpty(message = "Source System cannot be null or empty")
    @Pattern(regexp = "REU|BLO|CIQ|NSQ")
    @Column(name = "sourceSystem")
    private String sourceSystem;

    @NotEmpty(message = "Priority Flag cannot be null or empty")
    @Pattern(regexp = "Y|N")
    @Column(name = "priorityFlag")
    private String priorityFlag;

    @PositiveOrZero
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

    public static class EquityFeedsBuilder {

        private int id;
        private String externalTransactionId;
        private String clientId;
        private String securityId;
        private String transactionType;
        private LocalDate transactionDate;
        private float marketValue;
        private String sourceSystem;
        private String priorityFlag;
        private long processingFee;

        public EquityFeedsBuilder() {

        }

        public EquityFeedsBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public EquityFeedsBuilder setExternalTransactionId(String externalTransactionId) {
            this.externalTransactionId = externalTransactionId;
            return this;
        }

        public EquityFeedsBuilder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public EquityFeedsBuilder setSecurityId(String securityId) {
            this.securityId = securityId;
            return this;
        }

        public EquityFeedsBuilder setTransactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public EquityFeedsBuilder setTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public EquityFeedsBuilder setMarketValue(float marketValue) {
            this.marketValue = marketValue;
            return this;
        }

        public EquityFeedsBuilder setSourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
            return this;
        }

        public EquityFeedsBuilder setPriorityFlag(String priorityFlag) {
            this.priorityFlag = priorityFlag;
            return this;
        }

        public EquityFeedsBuilder setProcessingFee(long processingFee) {
            this.processingFee = processingFee;
            return this;
        }

        public EquityFeeds build() {
            return new EquityFeeds(id, externalTransactionId, clientId, securityId, transactionType, transactionDate, marketValue, sourceSystem, priorityFlag, processingFee);
        }

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
