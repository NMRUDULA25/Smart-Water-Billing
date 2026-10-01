package com.smartwater.smartwaterbilling.service;

import com.smartwater.smartwaterbilling.dto.HouseholdDTO;

import java.util.List;

public interface HouseholdService {

    HouseholdDTO createHousehold(HouseholdDTO dto);

    HouseholdDTO getHouseholdById(Long id);

    List<HouseholdDTO> getAllHouseholds();

    HouseholdDTO updateHousehold(Long id, HouseholdDTO dto);

    void deleteHousehold(Long id);
}