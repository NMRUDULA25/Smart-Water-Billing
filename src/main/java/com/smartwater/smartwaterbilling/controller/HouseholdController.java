package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.HouseholdDTO;
import com.smartwater.smartwaterbilling.service.HouseholdService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @PostMapping
    public HouseholdDTO createHousehold(
            @RequestBody HouseholdDTO dto) {

        return householdService.createHousehold(dto);
    }

    @GetMapping("/{id}")
    public HouseholdDTO getHouseholdById(
            @PathVariable Long id) {

        return householdService.getHouseholdById(id);
    }

    @GetMapping
    public List<HouseholdDTO> getAllHouseholds() {

        return householdService.getAllHouseholds();
    }

    @PutMapping("/{id}")
    public HouseholdDTO updateHousehold(
            @PathVariable Long id,
            @RequestBody HouseholdDTO dto) {

        return householdService.updateHousehold(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteHousehold(
            @PathVariable Long id) {

        householdService.deleteHousehold(id);
    }
}