package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.ConsumerDTO;
import com.smartwater.smartwaterbilling.entity.Consumer;
import com.smartwater.smartwaterbilling.mapper.ConsumerMapper;
import com.smartwater.smartwaterbilling.repository.ConsumerRepository;
import com.smartwater.smartwaterbilling.service.ConsumerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Override
    public ConsumerDTO createConsumer(ConsumerDTO consumerDTO) {

        if (consumerDTO == null) {
            throw new RuntimeException("Consumer data is required");
        }

        if (consumerRepository.existsByConsumerNumber(
                consumerDTO.getConsumerNumber())) {

            throw new RuntimeException(
                    "Consumer number already exists");
        }

        if (consumerRepository.existsByPhone(
                consumerDTO.getPhone())) {

            throw new RuntimeException(
                    "Phone number already exists");
        }

        Consumer consumer =
                ConsumerMapper.toEntity(consumerDTO);

        Consumer savedConsumer =
                consumerRepository.save(consumer);

        return ConsumerMapper.toDTO(savedConsumer);
    }

    @Override
    public ConsumerDTO getConsumerById(Long id) {

        Consumer consumer =
                consumerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Consumer not found with id: " + id));

        return ConsumerMapper.toDTO(consumer);
    }

    @Override
    public List<ConsumerDTO> getAllConsumers() {

        return consumerRepository.findAll()
                .stream()
                .map(ConsumerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConsumerDTO updateConsumer(
            Long id,
            ConsumerDTO consumerDTO) {

        Consumer consumer =
                consumerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Consumer not found with id: " + id));

        consumer.setConsumerNumber(
                consumerDTO.getConsumerNumber());

        consumer.setOwnerName(
                consumerDTO.getOwnerName());

        consumer.setAddress(
                consumerDTO.getAddress());

        consumer.setPhone(
                consumerDTO.getPhone());

        consumer.setConnectionType(
                consumerDTO.getConnectionType());

        Consumer updatedConsumer =
                consumerRepository.save(consumer);

        return ConsumerMapper.toDTO(updatedConsumer);
    }

    @Override
    public void deleteConsumer(Long id) {

        Consumer consumer =
                consumerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Consumer not found with id: " + id));

        consumerRepository.delete(consumer);
    }
}