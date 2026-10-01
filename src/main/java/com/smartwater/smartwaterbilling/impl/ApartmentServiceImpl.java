package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.ApartmentDTO;
import com.smartwater.smartwaterbilling.entity.Apartment;
import com.smartwater.smartwaterbilling.mapper.ApartmentMapper;
import com.smartwater.smartwaterbilling.repository.ApartmentRepository;
import com.smartwater.smartwaterbilling.service.ApartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public ApartmentDTO createApartment(ApartmentDTO dto) {

        if (apartmentRepository.existsByApartmentCode(dto.getApartmentCode())) {
            throw new RuntimeException("Apartment code already exists");
        }

        Apartment apartment = ApartmentMapper.toEntity(dto);

        Apartment savedApartment = apartmentRepository.save(apartment);

        return ApartmentMapper.toDTO(savedApartment);
    }

    @Override
    public ApartmentDTO getApartmentById(Long id) {

        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        return ApartmentMapper.toDTO(apartment);
    }

    @Override
    public List<ApartmentDTO> getAllApartments() {

        return apartmentRepository.findAll()
                .stream()
                .map(ApartmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApartmentDTO updateApartment(Long id, ApartmentDTO dto) {

        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        if (!apartment.getApartmentCode().equals(dto.getApartmentCode())
                && apartmentRepository.existsByApartmentCode(dto.getApartmentCode())) {
            throw new RuntimeException("Apartment code already exists");
        }

        apartment.setApartmentCode(dto.getApartmentCode());
        apartment.setApartmentName(dto.getApartmentName());
        apartment.setAddress(dto.getAddress());
        apartment.setTotalHouseholds(dto.getTotalHouseholds());

        Apartment updatedApartment = apartmentRepository.save(apartment);

        return ApartmentMapper.toDTO(updatedApartment);
    }

    @Override
    public void deleteApartment(Long id) {

        if (!apartmentRepository.existsById(id)) {
            throw new RuntimeException("Apartment not found");
        }

        apartmentRepository.deleteById(id);
    }
}