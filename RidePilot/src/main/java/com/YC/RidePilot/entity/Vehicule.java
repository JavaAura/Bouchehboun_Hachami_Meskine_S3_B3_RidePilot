package com.YC.RidePilot.entity;

import com.YC.RidePilot.enums.StatutVehicule;
import com.YC.RidePilot.enums.TypeVehicule;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "vehicules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Model is required")
    private String modele;

    @NotNull(message = "Registration number is required")
    private String immatriculation;

    @NotNull(message = "Kilometrage is required")
    @Min(value = 0, message = "Kilometrage must be positive")
    private Double kilometrage;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private StatutVehicule statut;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vehicle type is required")
    private TypeVehicule type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;
}
