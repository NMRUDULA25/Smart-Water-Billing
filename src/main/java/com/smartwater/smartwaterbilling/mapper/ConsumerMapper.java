package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.ConsumerDTO;
import com.smartwater.smartwaterbilling.entity.Consumer;

public class ConsumerMapper {

    private ConsumerMapper() {
        // Utility class
    }

    public static ConsumerDTO toDTO(Consumer consumer) {

        if (consumer == null) {
            return null;
        }

        return new ConsumerDTO(
                consumer.getId(),
                consumer.getConsumerNumber(),
                consumer.getOwnerName(),
                consumer.getAddress(),
                consumer.getPhone(),
                consumer.getConnectionType()
        );
    }

    public static Consumer toEntity(ConsumerDTO dto) {

        if (dto == null) {
            return null;
        }

        Consumer consumer = new Consumer();

        consumer.setId(dto.getId());
        consumer.setConsumerNumber(dto.getConsumerNumber());
        consumer.setOwnerName(dto.getOwnerName());
        consumer.setAddress(dto.getAddress());
        consumer.setPhone(dto.getPhone());
        consumer.setConnectionType(dto.getConnectionType());

        return consumer;
    }
}