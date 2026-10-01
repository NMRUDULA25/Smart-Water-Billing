package com.smartwater.smartwaterbilling.mapper;

import com.smartwater.smartwaterbilling.dto.ApartmentDTO;
import com.smartwater.smartwaterbilling.entity.Apartment;

public class ApartmentMapper {

    private ApartmentMapper() {
        // Utility class
    }

    public static ApartmentDTO toDTO(Apartment apartment) {

        if (apartment == null) {
            return null;
        }

        return new ApartmentDTO(
                apartment.getId(),
                apartment.getApartmentCode(),
                apartment.getApartmentName(),
                apartment.getAddress(),
                apartment.getTotalHouseholds()
        );
    }

    public static Apartment toEntity(ApartmentDTO dto) {

        if (dto == null) {
            return null;
        }

        Apartment apartment = new Apartment();

        apartment.setId(dto.getId());
        apartment.setApartmentCode(dto.getApartmentCode());
        apartment.setApartmentName(dto.getApartmentName());
        apartment.setAddress(dto.getAddress());
        apartment.setTotalHouseholds(dto.getTotalHouseholds());

        return apartment;
    }
}