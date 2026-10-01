package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.ConsumerDTO;
import com.smartwater.smartwaterbilling.service.ConsumerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/consumers")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping
    public ConsumerDTO createConsumer(
            @RequestBody ConsumerDTO consumerDTO) {

        return consumerService.createConsumer(consumerDTO);
    }

    @GetMapping
    public List<ConsumerDTO> getAllConsumers() {

        return consumerService.getAllConsumers();
    }

    @GetMapping("/{id}")
    public ConsumerDTO getConsumer(
            @PathVariable Long id) {

        return consumerService.getConsumerById(id);
    }

    @PutMapping("/{id}")
    public ConsumerDTO updateConsumer(
            @PathVariable Long id,
            @RequestBody ConsumerDTO dto) {

        return consumerService.updateConsumer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteConsumer(
            @PathVariable Long id) {

        consumerService.deleteConsumer(id);
    }
}