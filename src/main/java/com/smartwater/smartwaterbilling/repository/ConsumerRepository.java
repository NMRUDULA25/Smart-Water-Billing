package com.smartwater.smartwaterbilling.repository;

import com.smartwater.smartwaterbilling.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByConsumerNumber(String consumerNumber);

    boolean existsByConsumerNumber(String consumerNumber);

    boolean existsByPhone(String phone);
}