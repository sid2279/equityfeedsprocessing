package com.example.equityfeedsprocessing.service;

import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRedisRepository;

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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EquityFeedsServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EquityFeedsService equityFeedsService;

    @Mock
    private EquityFeedsRedisRepositoryImpl equityFeedsRedisRepositoryImpl;

    @Mock
    private EquityFeedsRedisRepository equityFeedsRedisRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(equityFeedsService).build();
    }

    @Test
    public void testSaveMethod() {
        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0);

        when(equityFeedsRedisRepository.save(any(EquityFeeds.class))).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds, equityFeedsService.save(equityFeeds));

    }

    @Test
    public void testsaveExternalTransactionId() {
        EquityFeeds equityFeeds = new EquityFeeds(423, "SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y", 0);

        when(equityFeedsRedisRepository.saveExternalTransactionId(any(EquityFeeds.class))).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds, equityFeedsService.saveExternalTransactionId(equityFeeds));
    }

    @Test
    public void testsaveClientId() {
        EquityFeeds equityFeeds = new EquityFeeds(423, "SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y", 0);

        when(equityFeedsRedisRepository.saveClientId(any(EquityFeeds.class))).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds, equityFeedsService.saveExternalTransactionId(equityFeeds));
    }

    @Test
    public void testfindBySecurityId() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0);

        List<EquityFeeds> expectedEquityFeeds = Arrays.asList(equityFeeds);

        doReturn(expectedEquityFeeds).when(equityFeedsRedisRepositoryImpl).findBySecurityId("ICICI");

    }

    @Test
    public void testfindByClientId() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0);

        List<EquityFeeds> expectedEquityFeeds = Arrays.asList(equityFeeds);

        doReturn(expectedEquityFeeds).when(equityFeedsRedisRepositoryImpl).findByClientId("GS");

    }

    @Test
    public void testfindByExternalTransactionId() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0);

        EquityFeeds expectedEquityFeeds = equityFeeds;

        doReturn(expectedEquityFeeds).when(equityFeedsRedisRepositoryImpl).findByExternalTransactionId("SAPEXTXN1");

    }

    @Test
    public void testfindById() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0);

        EquityFeeds expectedEquityFeeds = equityFeeds;

        doReturn(expectedEquityFeeds).when(equityFeedsRedisRepositoryImpl).findById("GSICICI2013-11-22");

    }

}