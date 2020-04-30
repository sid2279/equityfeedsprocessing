package com.example.equityfeedsprocessing.impl;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EquityFeedsRedisRepositoryImplTest {

    private MockMvc mockMvc;

    @Mock
    private EquityFeedsRedisRepositoryImpl equityFeedsRedisRepositoryImpl;

    @Mock
    RedisTemplate<String, EquityFeeds> redisTemplate;

    @Mock
    private HashOperations hashOperations;

    @Mock
    private ListOperations listOperations;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(equityFeedsRedisRepositoryImpl).build();
        MockitoAnnotations.initMocks(this);
        Mockito.when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        Mockito.when(redisTemplate.opsForList()).thenReturn(listOperations);
        Mockito.doNothing().when(hashOperations).put(anyString(), anyString(),anyString());
    }

    @Test
    public void testSaveData() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.save(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }

    @Test
    public void testSaveExternalTransactionId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.saveExternalTransactionId(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }

    @Test
    public void testSaveClientId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.saveClientId(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getClientId(), "GS");

    }

    @Test
    public void testSaveSecurityId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.saveSecurityId(equityFeeds)).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getSecurityId(), "ICICI");

    }

    @Test
    public void testfindById() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.findById("GSICICI2013-11-22")).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getClientId(), "GS");

    }

    @Test
    public void testfindByExternalTransactionId() {

        EquityFeeds equityFeeds = new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build();

        when(equityFeedsRedisRepositoryImpl.findByExternalTransactionId("SAPEXTXN1")).thenReturn(new EquityFeeds());

        assertEquals(equityFeeds.getExternalTransactionId(), "SAPEXTXN1");

    }

    @Test
    public void testfindByClientId() {

        List<EquityFeeds> equityFeeds = Arrays.asList(new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build());

        when(equityFeedsRedisRepositoryImpl.findByClientId("GS")).thenReturn(equityFeeds);

        assertEquals(equityFeeds.get(0).getClientId(), "GS");

    }

    @Test
    public void testfindBySecurityId() {

        List<EquityFeeds> equityFeeds = Arrays.asList(new EquityFeeds.EquityFeedsBuilder().setId(423).setExternalTransactionId("SAPEXTXN1").setClientId("GS").setSecurityId("ICICI").setTransactionType("BUY").setTransactionDate(LocalDate.of(2013, 11, 22)).setMarketValue(101.9f).setSourceSystem("BLO").setPriorityFlag("Y").setProcessingFee(0).build());

        when(equityFeedsRedisRepositoryImpl.findBySecurityId("ICICI")).thenReturn(equityFeeds);

        assertEquals(equityFeeds.get(0).getSecurityId(), "ICICI");

    }
}
