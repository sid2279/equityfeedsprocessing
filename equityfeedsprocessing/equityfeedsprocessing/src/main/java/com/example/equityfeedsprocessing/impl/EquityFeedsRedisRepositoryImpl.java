package com.example.equityfeedsprocessing.impl;

import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Repository
public class EquityFeedsRedisRepositoryImpl implements EquityFeedsRedisRepository {

    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    private static final Logger logger = LoggerFactory.getLogger(EquityFeedsRedisRepositoryImpl.class);

    @Autowired
    private RedisTemplate<String, EquityFeeds> redisTemplate;

    private HashOperations hashOperations;

    private ListOperations listOperations;

    public EquityFeedsRedisRepositoryImpl(RedisTemplate<String, EquityFeeds> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public EquityFeeds save(EquityFeeds equityFeeds) {
        logger.info("Inside the save method of the REDIS cache.");
        logger.info("Equity Feeds POJO in save: {}",equityFeeds);
        String key = equityFeeds.getClientId()+equityFeeds.getSecurityId()+equityFeeds.getTransactionDate();
        logger.info("Saving the record in the REDIS cache with key as {}", key);
        hashOperations.put("EQUITY", key, equityFeeds);
        return equityFeeds;
    }

    @Override
    public EquityFeeds saveExternalTransactionId(EquityFeeds equityFeeds) {
        logger.info("Inside the save externalTransactionId of the REDIS cache.");
        logger.info("Saving the record in the REDIS cache with key as {}", equityFeeds.getExternalTransactionId());
        logger.info("Equity Feeds POJO in saveExternalTransactionId: {}",equityFeeds);
        hashOperations.put("EXTNTID", equityFeeds.getExternalTransactionId(), equityFeeds);
        return equityFeeds;
    }

    @Override
    public EquityFeeds saveClientId(EquityFeeds equityFeeds) {
        logger.info("Inside the save clientId of the REDIS cache.");
        logger.info("Saving the record in the REDIS cache with key as {}", equityFeeds.getClientId());
        logger.info("Equity Feeds POJO in saveClientId: {}",equityFeeds);
        listOperations.rightPush(equityFeeds.getClientId(), equityFeeds);
        return equityFeeds;
    }

    @Override
    public EquityFeeds saveSecurityId(EquityFeeds equityFeeds) {
        logger.info("Inside the save securityId of the REDIS cache.");
        logger.info("Saving the record in the REDIS cache with key as {}", equityFeeds.getSecurityId());
        logger.info("Equity Feeds POJO in saveSecurityId: {}",equityFeeds);
        listOperations.rightPush(equityFeeds.getSecurityId(), equityFeeds);
        return equityFeeds;
    }

    @Override
    public Map<String, EquityFeeds> findAll() {
        return hashOperations.entries("EQUITY");
    }

    @Override
    public EquityFeeds findById(String id) {
        logger.info("Inside the findById method of the REDIS cache");
        logger.info("Trying to find the id {} in REDIS Cache ",id);
        return (EquityFeeds) hashOperations.get("EQUITY", id);
    }

    @Override
    public EquityFeeds findByExternalTransactionId(String externalTransactionId) {
        logger.info("Inside the findByExternalTransactionId method of the REDIS cache");
        logger.info("Trying to find the ExternalTransactionId {} in REDIS Cache ",externalTransactionId);
        return (EquityFeeds) hashOperations.get("EXTNTID", externalTransactionId);
    }

    @Override
    public List<EquityFeeds> findByClientId(String clientId) {
        logger.info("Inside the findByClientId method of the REDIS cache");
        logger.info("Trying to find the ClientId {} in REDIS Cache ",clientId);
        return (List<EquityFeeds>) listOperations.range(clientId, 0, -1);
    }

    @Override
    public List<EquityFeeds> findBySecurityId(String securityId) {
        logger.info("Inside the findBySecurityId method of the REDIS cache");
        logger.info("Trying to find the SecurityId {} in REDIS Cache ",securityId);
        return (List<EquityFeeds>) listOperations.range(securityId, 0, -1);
    }

    @Override
    public void update(EquityFeeds equityFeeds) {
        hashOperations.put("EQUITY", equityFeeds.getId(), equityFeeds);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete("EQUITY", id);
    }
}
