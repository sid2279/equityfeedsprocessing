package com.example.equityfeedsprocessing.repository;

import com.example.equityfeedsprocessing.model.EquityFeeds;

import java.util.List;

public interface EquityFeedsRedisRepository {

    EquityFeeds save(EquityFeeds equityFeeds);
    EquityFeeds saveExternalTransactionId(EquityFeeds equityFeeds);
    EquityFeeds saveClientId(EquityFeeds equityFeeds);
    EquityFeeds saveSecurityId(EquityFeeds equityFeeds);
    EquityFeeds findById(String id);
    EquityFeeds findByExternalTransactionId(String externalTransactionId);
    List<EquityFeeds> findByClientId(String clientId);
    List<EquityFeeds> findBySecurityId(String securityId);

}
