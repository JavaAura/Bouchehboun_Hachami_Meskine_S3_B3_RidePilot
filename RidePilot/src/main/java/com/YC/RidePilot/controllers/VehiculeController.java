package com.YC.RidePilot.controllers;


import com.YC.RidePilot.entity.dto.ReservationDTO;
import com.YC.RidePilot.services.VehiculeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@Slf4j
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @Autowired
    public VehiculeController(VehiculeService vehiculeService){
        this.vehiculeService = vehiculeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllReservations() {
        List<?> reservations = vehiculeService.getAllVehicules();
        return ResponseEntity.ok(reservations);
    }

}
