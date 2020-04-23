package com.example.equityfeedsprocessing.util;

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


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProcessingRuleTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProcessingRule processingRule;

    @Mock
    private ProcessingRule procesingRule;

    @Mock
    private SavingEquityData savingEquityData;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(processingRule).build();
    }

    @Test
    void testPriorityFlag() {
        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "Y",0l);

        assertThat(equityFeeds.getPriorityFlag(), is("Y"));

        equityFeeds.setProcessingFee(500);

        assertThat(equityFeeds.getProcessingFee(), is(500l));

    }


    @Test
    void testPriorityFlagAndTransactionType1() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "SELL", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "N",0l);

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("SELL"));

        equityFeeds.setProcessingFee(100);

        assertThat(equityFeeds.getProcessingFee(), is(100l));

    }

    @Test
    void testPriorityFlagAndTransactionType2() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "WITHDRAW", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "N",0l);

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("WITHDRAW"));

        equityFeeds.setProcessingFee(100);

        assertThat(equityFeeds.getProcessingFee(), is(100l));

    }

    @Test
    void testPriorityFlagAndTransactionType3() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "BUY", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "N",0l);

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("BUY"));

        equityFeeds.setProcessingFee(50);

        assertThat(equityFeeds.getProcessingFee(), is(50l));

    }

    @Test
    void testPriorityFlagAndTransactionType4() {

        EquityFeeds equityFeeds = new EquityFeeds(423,"SAPEXTXN1", "GS", "ICICI", "DEPOSIT", LocalDate.of(2013, 11, 22), 101.9f, "BLO", "N",0l);

        assertThat(equityFeeds.getPriorityFlag(), is("N"));
        assertThat(equityFeeds.getTransactionType(), is("DEPOSIT"));

        equityFeeds.setProcessingFee(50);

        assertThat(equityFeeds.getProcessingFee(), is(50l));

    }



}