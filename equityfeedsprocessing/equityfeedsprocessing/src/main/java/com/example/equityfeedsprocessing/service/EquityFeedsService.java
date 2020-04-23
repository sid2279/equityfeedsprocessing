package com.example.equityfeedsprocessing.service;


import com.example.equityfeedsprocessing.impl.EquityFeedsRedisRepositoryImpl;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.repository.EquityFeedsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquityFeedsService {

    @Autowired
    private EquityFeedsRepository equityFeedsRepository;

    @Autowired
    private EquityFeedsRedisRepositoryImpl equityFeedsRedisRepositoryImpl;

    private static final Logger logger = LoggerFactory.getLogger(EquityFeedsService.class);

    public EquityFeeds findByExternalTransactionId(String externalTransactionId) {

        logger.info("Inside the findByExternalTransactionId method of EquityFeedsService");

        EquityFeeds extnTransactionId = equityFeedsRedisRepositoryImpl.findByExternalTransactionId(externalTransactionId);
        return extnTransactionId;
    }

    public List<EquityFeeds> findByClientId(String clientId) {

        logger.info("Inside the findByClientId method of EquityFeedsService");

        List<EquityFeeds> cId = equityFeedsRedisRepositoryImpl.findByClientId(clientId);
        return cId;
    }

    public List<EquityFeeds> findBySecurityId(String securityId) {

        logger.info("Inside the findBySecurityId method of EquityFeedsService");

        List<EquityFeeds> sId = equityFeedsRedisRepositoryImpl.findBySecurityId(securityId);
        return sId;
    }

    public EquityFeeds findById(String id) {

        logger.info("Inside the findById method of EquityFeedsService");

        EquityFeeds equityFeeds = equityFeedsRedisRepositoryImpl.findById(id);
        return equityFeeds;
    }

     public EquityFeeds save(EquityFeeds equityFeeds) {

        logger.info("Inside the save method of EquityFeedsService.");

        equityFeedsRedisRepositoryImpl.save(equityFeeds);
        return equityFeeds;
    }

     public EquityFeeds saveExternalTransactionId(EquityFeeds equityFeeds) {

        logger.info("Inside the saveExternalTransactionId method of EquityFeedsService.");

        equityFeedsRedisRepositoryImpl.saveExternalTransactionId(equityFeeds);
        return equityFeeds;
    }

    public EquityFeeds saveClientId(EquityFeeds equityFeeds) {

        logger.info("Inside the saveClientId method of EquityFeedsService.");

        equityFeedsRedisRepositoryImpl.saveClientId(equityFeeds);
        return equityFeeds;
    }

    public EquityFeeds saveSecurityId(EquityFeeds equityFeeds) {

        logger.info("Inside the saveSecurityId method of EquityFeedsService.");

        equityFeedsRedisRepositoryImpl.saveSecurityId(equityFeeds);

        return equityFeeds;
    }
}
