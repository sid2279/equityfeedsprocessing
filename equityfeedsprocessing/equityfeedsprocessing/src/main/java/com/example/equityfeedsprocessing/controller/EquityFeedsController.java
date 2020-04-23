package com.example.equityfeedsprocessing.controller;

import com.example.equityfeedsprocessing.exception.ClientIdNotFoundException;
import com.example.equityfeedsprocessing.exception.ExternalTransactionIdNotFoundException;
import com.example.equityfeedsprocessing.exception.SecurityIdNotFoundException;
import com.example.equityfeedsprocessing.model.EquityFeeds;
import com.example.equityfeedsprocessing.service.EquityFeedsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equityFeeds")
public class EquityFeedsController {

    @Autowired
    private EquityFeedsService equityFeedsService;

    private static final Logger logger = LoggerFactory.getLogger(EquityFeedsController.class);

    @GetMapping("/getByExternalTransactionId/{externalTransactionId}")
    public ResponseEntity<Optional<EquityFeeds>> getByExternalTransactionId(@PathVariable("externalTransactionId") final String externalTransactionId) {

        logger.info("Inside getByExternalTransactionId method.");

        logger.info("Calling findByExternalTransactionId method of the REDIS cache.");

        Optional<EquityFeeds> extnTransactionId = Optional.ofNullable(equityFeedsService.findByExternalTransactionId(externalTransactionId));

         if(extnTransactionId.isPresent()) {
             logger.info("ExternalTransactionId is present in the REDIS cache.");
             return ResponseEntity.ok(extnTransactionId);
         } else {
            logger.info("ExternalTransactionId is NOT present in the REDIS cache.");
            extnTransactionId.orElseThrow(() -> new ExternalTransactionIdNotFoundException(externalTransactionId));
         }
         return ResponseEntity.ok(extnTransactionId);
        }

    @GetMapping("/getByClientId/{clientId}")
    public ResponseEntity<List<EquityFeeds>> getByClientId(@PathVariable("clientId") final String clientId) {

        logger.info("Inside getByClientId method.");

        logger.info("Calling findByClientId method of the REDIS cache.");

        List<EquityFeeds> cId = equityFeedsService.findByClientId(clientId);

        if(!cId.isEmpty()) {
            logger.info("ClientId is present in the REDIS cache.");
            return ResponseEntity.ok(cId);
        } else {
            logger.info("ClientId is NOT present in the REDIS cache.");
            throw new ClientIdNotFoundException(clientId);
        }

     }

    @GetMapping("/getBySecurityId/{securityId}")
    public ResponseEntity<List<EquityFeeds>> getBySecurityId(@PathVariable("securityId") final String securityId) {

        logger.info("Inside getBySecurityId method.");

        logger.info("Calling findBySecurityId method of the REDIS cache.");

        List<EquityFeeds> sId = equityFeedsService.findBySecurityId(securityId);

        if(!sId.isEmpty()) {
            logger.info("SecurityId is present in the REDIS cache.");
            return ResponseEntity.ok(sId);
        } else {
            logger.info("SecurityId is NOT present in the REDIS cache.");
            throw new SecurityIdNotFoundException(securityId);
        }

    }

}
