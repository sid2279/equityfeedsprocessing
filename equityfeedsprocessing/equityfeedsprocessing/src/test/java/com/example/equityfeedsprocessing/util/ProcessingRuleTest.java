package com.example.equityfeedsprocessing.util;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProcessingRuleTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProcessingRule processingRule;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(processingRule).build();
    }

    @Test
    void testPriorityFlag() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        assertThat(equityFeeds.getPriorityFlag(), is("Y"));

        equityFeeds.setProcessingFee(500);

        assertThat(equityFeeds.getProcessingFee(), is(500l));

    }


    @Test
    void testPriorityFlagAndTransactionType1() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("SELL").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("N").setProcessingFee(0).build();

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("SELL"));

        equityFeeds.setProcessingFee(100);

        assertThat(equityFeeds.getProcessingFee(), is(100l));

    }

    @Test
    void testPriorityFlagAndTransactionType2() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("WITHDRAW").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("N").setProcessingFee(0).build();

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("WITHDRAW"));

        equityFeeds.setProcessingFee(100);

        assertThat(equityFeeds.getProcessingFee(), is(100l));

    }

    @Test
    void testPriorityFlagAndTransactionType3() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("N").setProcessingFee(0).build();

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("BUY"));

        equityFeeds.setProcessingFee(50);

        assertThat(equityFeeds.getProcessingFee(), is(50l));

    }

    @Test
    void testPriorityFlagAndTransactionType4() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("DEPOSIT").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("N").setProcessingFee(0).build();

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("DEPOSIT"));

        equityFeeds.setProcessingFee(50);

        assertThat(equityFeeds.getProcessingFee(), is(50l));

    }

}
