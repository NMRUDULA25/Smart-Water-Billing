package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.ConsumerDTO;

import java.util.List;

public interface ConsumerService {

    ConsumerDTO createConsumer(ConsumerDTO consumerDTO);

    ConsumerDTO getConsumerById(Long id);

    List<ConsumerDTO> getAllConsumers();

    ConsumerDTO updateConsumer(Long id, ConsumerDTO consumerDTO);

    void deleteConsumer(Long id);
}