package com.YC.RidePilot.entity;
import com.YC.RidePilot.enums.StatutChauffeur;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "chauffeurs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chauffeur {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String nom;

    @NotBlank(message = "Last name is required")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private StatutChauffeur statut;

    @NotNull(message = "Availability start time is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime disponibiliteDebut;

    @NotNull(message = "Availability end time is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime disponibiliteFin;

    @OneToOne(mappedBy = "chauffeur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Vehicule vehicule;
}
