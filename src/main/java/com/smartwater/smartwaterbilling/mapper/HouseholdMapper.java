package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.HouseholdDTO;
import com.smartwater.smartwaterbilling.entity.Household;

public class HouseholdMapper {

    private HouseholdMapper() {
    }

    public static HouseholdDTO toDTO(Household household) {

        if (household == null) {
            return null;
        }

        return new HouseholdDTO(
                household.getId(),
                household.getConsumerNumber(),
                household.getHouseholdName(),
                household.getFlatArea(),
                household.getOccupants()
        );
    }

    public static Household toEntity(HouseholdDTO dto) {

        if (dto == null) {
            return null;
        }

        Household household = new Household();

        household.setId(dto.getId());
        household.setConsumerNumber(dto.getConsumerNumber());
        household.setHouseholdName(dto.getHouseholdName());
        household.setFlatArea(dto.getFlatArea());
        household.setOccupants(dto.getOccupants());

        return household;
    }
}