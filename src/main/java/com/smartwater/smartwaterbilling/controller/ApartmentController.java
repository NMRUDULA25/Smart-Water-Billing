package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.ApartmentDTO;
import com.smartwater.smartwaterbilling.service.ApartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ApartmentDTO createApartment(
            @Valid @RequestBody ApartmentDTO dto) {

        return apartmentService.createApartment(dto);
    }

    @GetMapping("/{id}")
    public ApartmentDTO getApartmentById(
            @PathVariable Long id) {

        return apartmentService.getApartmentById(id);
    }

    @GetMapping
    public List<ApartmentDTO> getAllApartments() {

        return apartmentService.getAllApartments();
    }

    @PutMapping("/{id}")
    public ApartmentDTO updateApartment(
            @PathVariable Long id,
            @Valid @RequestBody ApartmentDTO dto) {

        return apartmentService.updateApartment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteApartment(
            @PathVariable Long id) {

        apartmentService.deleteApartment(id);
    }
}