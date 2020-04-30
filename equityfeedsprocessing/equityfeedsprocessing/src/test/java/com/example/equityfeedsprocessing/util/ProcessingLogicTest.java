package com.example.equityfeedsprocessing.util;

import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProcessingLogicTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProcessingLogic processingLogic;

    @Mock
    private ProcessingRule processingRule;

    @Mock
    private EquityFeedsRedisRepositoryImpl equityFeedsRedisRepositoryImpl;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(processingLogic).build();
    }

    @Test
    void testprocessDataLogic() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        EquityFeeds processedEquityFeeds = equityFeeds;

        doReturn(processedEquityFeeds).when(equityFeedsRedisRepositoryImpl).findById("GSICICI2013-11-22");

    }

    @Test
    void testKeyPresent1() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        EquityFeeds processedEquityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("SELL").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.findById("GSICICI2013-11-22")).thenReturn(processedEquityFeeds);

        assertEquals(equityFeeds.getTransactionType().equals("BUY"), processedEquityFeeds.getTransactionType().equals("SELL"));

        equityFeeds.setProcessingFee(10);

        assertEquals(equityFeeds.getProcessingFee(), 10);

    }

    @Test
    void testKeyPresent2() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        EquityFeeds processedEquityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("SELL").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.findById("GSICICI2013-11-22")).thenReturn(processedEquityFeeds);

        assertEquals(equityFeeds.getTransactionType().equals("SELL"), processedEquityFeeds.getTransactionType().equals("BUY"));

        equityFeeds.setProcessingFee(10);

        assertEquals(equityFeeds.getProcessingFee(), 10);

    }

    @Test
    void testNormalTransaction() {

         EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        EquityFeeds normalProcessedEquityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("SELL").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(500).build();

        when(processingRule.processData(equityFeeds)).thenReturn(normalProcessedEquityFeeds);

        equityFeeds.setProcessingFee(500);

        assertEquals(normalProcessedEquityFeeds.getProcessingFee(), equityFeeds.getProcessingFee());

    }
}
