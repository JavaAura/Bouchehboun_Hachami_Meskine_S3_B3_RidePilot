package com.YC.RidePilot.controllers;

import com.YC.RidePilot.entity.dto.ReservationAnalyticsDTO;
import com.YC.RidePilot.entity.dto.ReservationDTO;
import com.YC.RidePilot.entity.dto.ReservationResponseDTO;
import com.YC.RidePilot.services.InterfacesServices.ReservationAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.YC.RidePilot.services.ReservationService;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reservation")
@Slf4j
public class ReservationController {
    
    private final ReservationService reservationService;

    private ReservationAnalyticsService analyticsService;



    @Autowired
    public ReservationController(ReservationService reservationService , ReservationAnalyticsService analyticsService) {
        this.reservationService = reservationService;
        this.analyticsService = analyticsService;
    }

    // 1. Create reservation
    @PostMapping
    public ReservationResponseDTO createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        log.info("chf id "+reservationDTO.getChauffeurId());
        ReservationResponseDTO createdReservation = reservationService.createReservation(reservationDTO);
        return createdReservation;
    }

    /**
     * Update an existing reservation.
     *
     * @param id the ID of the reservation to update
     * @param reservationDTO the updated reservation data
     * @return the updated reservation
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a reservation", description = "Update an existing reservation based on the reservation ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation data"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @Parameter(description = "The ID of the reservation to be updated") @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {

        ReservationResponseDTO updatedReservation = reservationService.updateReservation(id, reservationDTO);
        return ResponseEntity.ok(updatedReservation);
    }

    /**
     * Delete a reservation by ID.
     *
     * @param id the ID of the reservation to delete
     * @return a response indicating success or failure
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservation", description = "Delete an existing reservation based on the reservation ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<String> deleteReservation(
            @Parameter(description = "The ID of the reservation to be deleted") @PathVariable Long id) {

        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted successfully.");
    }

    /**
     * Get a reservation by ID.
     *
     * @param id the ID of the reservation to retrieve
     * @return the reservation details if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a reservation by ID", description = "Retrieve a reservation's details by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found with the given ID")
    })
    public ResponseEntity<ReservationDTO> getReservationById(
            @Parameter(description = "The ID of the reservation to retrieve") @PathVariable Long id) {

        ReservationDTO reservationDTO = reservationService.getReservationById(id);

        // If the reservation is not found, return a 404 response
        if (reservationDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservationDTO);
    }
    // 5. Display all reservations
    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // Analytics
    @GetMapping("/analytics")
    public ResponseEntity<ReservationAnalyticsDTO> getAnalytics() {
        ReservationAnalyticsDTO analytics = analyticsService.getReservationAnalytics();
        return ResponseEntity.ok(analytics);
    }
    // 2. Update  reservation
  /*  @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody @Valid ReservationDTO reservationDTO) {
        ReservationDTO updatedReservation = reservationService.updateReservation(id, reservationDTO);
        return ResponseEntity.ok(updatedReservation);
    }

    // 3. Delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Display reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }



    // 6. Analytics and stats
    @GetMapping("/analytics")
    public ResponseEntity<ReservationAnalyticsDTO> getReservationAnalytics() {
        ReservationAnalyticsDTO analytics = reservationService.getReservationAnalytics();
        return ResponseEntity.ok(analytics);
    }*/


}
