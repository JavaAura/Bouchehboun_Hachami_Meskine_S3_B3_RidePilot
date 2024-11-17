package com.YC.RidePilot.entity;

import com.YC.RidePilot.enums.StatutReservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date and time are required")
    private LocalDateTime dateHeure;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ville", column = @Column(name = "depart_ville")),
            @AttributeOverride(name = "quartier", column = @Column(name = "depart_quartier"))
    })
    private Address adresseDepart;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ville", column = @Column(name = "arrivee_ville")),
            @AttributeOverride(name = "quartier", column = @Column(name = "arrivee_quartier"))
    })
    private Address adresseArrivee;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be zero or positive")
    private Double prix;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private StatutReservation statut = StatutReservation.CREATED;

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be positive")
    private Double distanceKm;

    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime heureDebutCourse;

    @NotNull(message = "End time is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime heureFinCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chauffeur_id", nullable = false)
    private Chauffeur chauffeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id", nullable = false)
    private Vehicule vehicule;
}
