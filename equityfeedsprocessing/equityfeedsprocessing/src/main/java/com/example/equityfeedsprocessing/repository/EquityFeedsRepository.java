package com.example.equityfeedsprocessing.repository;


import com.example.equityfeedsprocessing.model.EquityFeeds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquityFeedsRepository  extends JpaRepository<EquityFeeds, Integer> {

}
