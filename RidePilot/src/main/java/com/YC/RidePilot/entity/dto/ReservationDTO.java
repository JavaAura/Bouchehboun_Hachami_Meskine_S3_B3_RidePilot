package com.YC.RidePilot.entity.dto;

import java.time.LocalDateTime;

import com.YC.RidePilot.enums.StatutReservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ReservationDTO {

    private Long id;

    @NotNull(message = "La date et l'heure de la réservation sont obligatoires.")
    private LocalDateTime dateHeure;

    private LocalDateTime heureDebutCourse;
    private LocalDateTime heureFinCourse;

    @NotBlank(message = "La ville de départ est obligatoire.")
    private String adresseDepartVille;

    @NotBlank(message = "Le quartier de départ est obligatoire.")
    private String adresseDepartQuartier;

    @NotBlank(message = "La ville d'arrivée est obligatoire.")
    private String adresseArriveeVille;

    @NotBlank(message = "Le quartier d'arrivée est obligatoire.")
    private String adresseArriveeQuartier;

    @Positive(message = "La distance doit être un nombre positif.")
    private Double distanceKm;


    private Double prix;



    @NotNull(message = "Le statut de la réservation est obligatoire.")
    private StatutReservation statut;

    @NotNull(message = "L'identifiant du chauffeur est obligatoire.")
    private Long chauffeurId;

    @NotNull(message = "L'identifiant du véhicule est obligatoire.")
    private Long vehiculeId;

    public ReservationDTO(
            Long id,
            @NotNull(message = "La date et l'heure de la réservation sont obligatoires.") LocalDateTime dateHeure,
            LocalDateTime heureDebutCourse,
            LocalDateTime heureFinCourse,
            @NotBlank(message = "La ville de départ est obligatoire.") String adresseDepartVille,
            @NotBlank(message = "Le quartier de départ est obligatoire.") String adresseDepartQuartier,
            @NotBlank(message = "La ville d'arrivée est obligatoire.") String adresseArriveeVille,
            @NotBlank(message = "Le quartier d'arrivée est obligatoire.") String adresseArriveeQuartier,
            @Positive(message = "La distance doit être un nombre positif.") Double distanceKm,
            @NotNull(message = "Le prix est obligatoire.") @PositiveOrZero(message = "Le prix ne peut pas être négatif.") Double prix,
            @NotNull(message = "Le statut de la réservation est obligatoire.") StatutReservation statut,
            @NotNull(message = "L'identifiant du chauffeur est obligatoire.") Long chauffeurId,
            @NotNull(message = "L'identifiant du véhicule est obligatoire.") Long vehiculeId) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.heureDebutCourse = heureDebutCourse;
        this.heureFinCourse = heureFinCourse;
        this.adresseDepartVille = adresseDepartVille;
        this.adresseDepartQuartier = adresseDepartQuartier;
        this.adresseArriveeVille = adresseArriveeVille;
        this.adresseArriveeQuartier = adresseArriveeQuartier;
        this.distanceKm = distanceKm;
        this.prix = prix;
        this.statut = statut;
        this.chauffeurId = chauffeurId;
        this.vehiculeId = vehiculeId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public LocalDateTime getHeureDebutCourse() {
        return heureDebutCourse;
    }

    public void setHeureDebutCourse(LocalDateTime heureDebutCourse) {
        this.heureDebutCourse = heureDebutCourse;
    }

    public LocalDateTime getHeureFinCourse() {
        return heureFinCourse;
    }

    public void setHeureFinCourse(LocalDateTime heureFinCourse) {
        this.heureFinCourse = heureFinCourse;
    }

    public String getAdresseDepartVille() {
        return adresseDepartVille;
    }

    public void setAdresseDepartVille(String adresseDepartVille) {
        this.adresseDepartVille = adresseDepartVille;
    }

    public String getAdresseDepartQuartier() {
        return adresseDepartQuartier;
    }

    public void setAdresseDepartQuartier(String adresseDepartQuartier) {
        this.adresseDepartQuartier = adresseDepartQuartier;
    }

    public String getAdresseArriveeVille() {
        return adresseArriveeVille;
    }

    public void setAdresseArriveeVille(String adresseArriveeVille) {
        this.adresseArriveeVille = adresseArriveeVille;
    }

    public String getAdresseArriveeQuartier() {
        return adresseArriveeQuartier;
    }

    public void setAdresseArriveeQuartier(String adresseArriveeQuartier) {
        this.adresseArriveeQuartier = adresseArriveeQuartier;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }



    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public Long getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(Long chauffeurId) {
        this.chauffeurId = chauffeurId;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }


}
