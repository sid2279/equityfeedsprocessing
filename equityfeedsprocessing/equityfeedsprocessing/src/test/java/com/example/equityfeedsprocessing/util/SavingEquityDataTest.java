package com.example.equityfeedsprocessing.util;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRepository;
import com.example.equityfeedsprocessing.service.EquityFeedsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class SavingEquityDataTest {

    private MockMvc mockMvc;

    @Mock
    private EquityFeedsService equityFeedsService;

    @Test
    public void testSaveData() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsService.save(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }

    @Test
    public void testSaveExternalTransactionId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsService.saveExternalTransactionId(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }

    @Test
    public void testSaveClientId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsService.saveClientId(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }

    @Test
    public void testSaveSecurityId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsService.saveSecurityId(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }
}
