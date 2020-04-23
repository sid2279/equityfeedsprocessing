package com.example.equityfeedsprocessing.controller;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.service.EquityFeedsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EquityFeedsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EquityFeedsService equityFeedsService;

    @InjectMocks
    private EquityFeedsController equityFeedsController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(equityFeedsController).build();
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void getByExternalTransactionId() throws Exception {
        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0);
        when(equityFeedsService.findByExternalTransactionId("SAPEXTXN1")).thenReturn(equityFeeds);
        mockMvc.perform(MockMvcRequestBuilders.get("/equityFeeds/getByExternalTransactionId/{externalTransactionId}", "SAPEXTXN1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.*", Matchers.hasSize(10)))
                .andExpect(jsonPath("$.id", Matchers.is(423)))
                .andExpect(jsonPath("$.externalTransactionId", Matchers.is("SAPEXTXN1")))
                .andExpect(jsonPath("$.clientId", Matchers.is("GS")))
                .andExpect(jsonPath("$.securityId", Matchers.is("ICICI")))
                .andExpect(jsonPath("$.transactionType", Matchers.is("BUY")))
                .andExpect(jsonPath("$.marketValue", Matchers.is(101.9)))
                .andExpect(jsonPath("$.sourceSystem", Matchers.is("BLO")))
                .andExpect(jsonPath("$.priorityFlag", Matchers.is("Y")))
                .andExpect(jsonPath("$.processingFee", Matchers.is(0)));
        verify(equityFeedsService, times(1)).findByExternalTransactionId("SAPEXTXN1");
    }

    @Test
    public void getByClientId() throws Exception {
        List<EquityFeeds> equityFeeds = Arrays.asList(new EquityFeeds(413,"SAPEXTXN4", "HJ", "RELIND", "WITHDRAW", LocalDate.of(2013, 11, 29), 230.0f, "BLO", "N",100));
        when(equityFeedsService.findByClientId("HJ")).thenReturn(equityFeeds);
        mockMvc.perform(MockMvcRequestBuilders.get("/equityFeeds/getByClientId/{clientId}", "HJ"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(413)))
                .andExpect(jsonPath("$[0].externalTransactionId", Matchers.is("SAPEXTXN4")))
                .andExpect(jsonPath("$[0].clientId", Matchers.is("HJ")))
                .andExpect(jsonPath("$[0].securityId", Matchers.is("RELIND")))
                .andExpect(jsonPath("$[0].transactionType", Matchers.is("WITHDRAW")))
                .andExpect(jsonPath("$[0].marketValue", Matchers.is(230.0)))
                .andExpect(jsonPath("$[0].sourceSystem", Matchers.is("BLO")))
                .andExpect(jsonPath("$[0].priorityFlag", Matchers.is("N")))
                .andExpect(jsonPath("$[0].processingFee", Matchers.is(100)));
        verify(equityFeedsService, times(1)).findByClientId("HJ");

    }

    @Test
    public void getBySecurityId() throws Exception {
        List<EquityFeeds> equityFeeds = Arrays.asList(new EquityFeeds(412,"SAPEXTXN3", "AP", "HINDALCO", "DEPOSIT", LocalDate.of(2013, 11, 18), 120.0f, "BLO", "Y",500));
        when(equityFeedsService.findBySecurityId("HINDALCO")).thenReturn(equityFeeds);
        mockMvc.perform(MockMvcRequestBuilders.get("/equityFeeds/getBySecurityId/{securityId}", "HINDALCO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(412)))
                .andExpect(jsonPath("$[0].externalTransactionId", Matchers.is("SAPEXTXN3")))
                .andExpect(jsonPath("$[0].clientId", Matchers.is("AP")))
                .andExpect(jsonPath("$[0].securityId", Matchers.is("HINDALCO")))
                .andExpect(jsonPath("$[0].transactionType", Matchers.is("DEPOSIT")))
                .andExpect(jsonPath("$[0].marketValue", Matchers.is(120.0)))
                .andExpect(jsonPath("$[0].sourceSystem", Matchers.is("BLO")))
                .andExpect(jsonPath("$[0].priorityFlag", Matchers.is("Y")))
                .andExpect(jsonPath("$[0].processingFee", Matchers.is(500)));
        verify(equityFeedsService, times(1)).findBySecurityId("HINDALCO");

    }

}