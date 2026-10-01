package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterReadingRepository
        extends JpaRepository<MeterReading, Long> {

    List<MeterReading> findByConsumerNumber(
            String consumerNumber);

    List<MeterReading> findByConsumerNumberOrderByReadingDateDesc(
            String consumerNumber);
}