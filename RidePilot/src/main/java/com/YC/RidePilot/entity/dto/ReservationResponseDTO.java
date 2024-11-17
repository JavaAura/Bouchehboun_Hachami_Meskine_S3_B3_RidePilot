package com.YC.RidePilot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationResponseDTO {
    private Long id;
    private String dateHeure;
    private String heureDebutCourse;
    private String heureFinCourse;
    private String adresseDepartVille;
    private String adresseDepartQuartier;
    private String adresseArriveeVille;
    private String adresseArriveeQuartier;
    private double distanceKm;
    private double prix;
    private String statut;
    private String chauffeurName; // Include chauffeur details
    private String vehiculeType;

}
