package com.example.equityfeedsprocessing.util;


import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class POJOValidator {

    private static final Logger logger = LoggerFactory.getLogger(POJOValidator.class);

    public Set<ConstraintViolation<EquityFeeds>> validate(EquityFeeds equityFeeds) {

        logger.info("Inside POJO Validator class.");

        logger.info("Creating Validator Factory which returns Validator");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Validator validator = factory.getValidator();

        logger.info("Calling the validate method which validates the POJO object.");

        return  validator.validate(equityFeeds);

    }
}
