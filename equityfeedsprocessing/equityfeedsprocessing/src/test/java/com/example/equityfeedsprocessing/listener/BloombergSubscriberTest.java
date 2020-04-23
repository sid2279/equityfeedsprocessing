package com.example.equityfeedsprocessing.listener;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class BloombergSubscriberTest {

    @Test
    public void testJSONData() throws JSONException {
        String actual = "{\"externalTransactionId\":\"SAPEXTXN6\",\"clientId\":\"GS\",\"securityId\":\"ICICI\",\"transactionType\":\"SELL\",\"transactionDate\":\"2013-11-23\",\"marketValue\":100.0,\"sourceSystem\":\"BLO\",\"priorityFlag\":\"Y\",\"processingFee\":0}";

        JSONAssert.assertEquals("{externalTransactionId:\"SAPEXTXN6\", clientId:\"GS\", securityId:\"ICICI\", transactionType:\"SELL\", transactionDate:\"2013-11-23\", marketValue:100.0, sourceSystem:\"BLO\", priorityFlag: \"Y\", processingFee:0}", actual, true);

    }
}