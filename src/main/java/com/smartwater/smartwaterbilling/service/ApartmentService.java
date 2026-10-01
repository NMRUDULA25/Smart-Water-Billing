package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.ApartmentDTO;

import java.util.List;

public interface ApartmentService {

    ApartmentDTO createApartment(ApartmentDTO dto);

    ApartmentDTO getApartmentById(Long id);

    List<ApartmentDTO> getAllApartments();

    ApartmentDTO updateApartment(Long id, ApartmentDTO dto);

    void deleteApartment(Long id);
}