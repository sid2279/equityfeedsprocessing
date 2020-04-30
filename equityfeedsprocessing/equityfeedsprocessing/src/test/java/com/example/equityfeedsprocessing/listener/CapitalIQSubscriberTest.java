package com.example.equityfeedsprocessing.listener;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


class CapitalIQSubscriberTest {

    @Test
    public void testObject() {
        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        assertThat(equityFeeds.getId(), is(423));
        assertThat(equityFeeds.getExternalTransactionId(), is("SAPEXTXN1"));
        assertThat(equityFeeds.getClientId(), is("GS"));
        assertThat(equityFeeds.getSecurityId(), is("ICICI"));
        assertThat(equityFeeds.getTransactionType(), is("BUY"));
        assertThat(equityFeeds.getTransactionDate(), is(LocalDate.of(2013, 11, 22)));
        assertThat(equityFeeds.getMarketValue(), is(101.9f));
        assertThat(equityFeeds.getSourceSystem(), is("BLO"));
        assertThat(equityFeeds.getPriorityFlag(), is("Y"));
        assertThat(equityFeeds.getProcessingFee(), is(0l));

    }

}
