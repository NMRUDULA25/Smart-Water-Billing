package com.smartwater.smartwaterbilling.impl;

import com.smartwater.smartwaterbilling.dto.HouseholdDTO;
import com.smartwater.smartwaterbilling.entity.Household;
import com.smartwater.smartwaterbilling.mapper.HouseholdMapper;
import com.smartwater.smartwaterbilling.repository.HouseholdRepository;
import com.smartwater.smartwaterbilling.service.HouseholdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(
            HouseholdRepository householdRepository) {

        this.householdRepository = householdRepository;
    }

    @Override
    public HouseholdDTO createHousehold(HouseholdDTO dto) {

        if (dto.getConsumerNumber() == null ||
                dto.getConsumerNumber().isBlank()) {

            throw new RuntimeException(
                    "Consumer number is required");
        }

        if (householdRepository
                .findByConsumerNumber(dto.getConsumerNumber())
                .isPresent()) {

            throw new RuntimeException(
                    "Consumer number already exists");
        }

        Household household =
                HouseholdMapper.toEntity(dto);

        Household saved =
                householdRepository.save(household);

        return HouseholdMapper.toDTO(saved);
    }

    @Override
    public HouseholdDTO getHouseholdById(Long id) {

        Household household =
                householdRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Household not found"));

        return HouseholdMapper.toDTO(household);
    }

    @Override
    public List<HouseholdDTO> getAllHouseholds() {

        return householdRepository.findAll()
                .stream()
                .map(HouseholdMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HouseholdDTO updateHousehold(
            Long id,
            HouseholdDTO dto) {

        Household household =
                householdRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Household not found"));

        if (!household.getConsumerNumber()
                .equals(dto.getConsumerNumber())) {

            if (householdRepository
                    .findByConsumerNumber(
                            dto.getConsumerNumber())
                    .isPresent()) {

                throw new RuntimeException(
                        "Consumer number already exists");
            }
        }

        household.setConsumerNumber(
                dto.getConsumerNumber());

        household.setHouseholdName(
                dto.getHouseholdName());

        household.setFlatArea(
                dto.getFlatArea());

        household.setOccupants(
                dto.getOccupants());

        Household updated =
                householdRepository.save(household);

        return HouseholdMapper.toDTO(updated);
    }

    @Override
    public void deleteHousehold(Long id) {

        if (!householdRepository.existsById(id)) {

            throw new RuntimeException(
                    "Household not found");
        }

        householdRepository.deleteById(id);
    }
}