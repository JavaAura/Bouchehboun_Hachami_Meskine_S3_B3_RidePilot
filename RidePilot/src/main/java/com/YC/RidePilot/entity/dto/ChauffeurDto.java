package com.YC.RidePilot.entity.dto;

import com.YC.RidePilot.entity.Vehicule;
import com.YC.RidePilot.enums.StatutChauffeur;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChauffeurDto {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;

    @NotNull(message = "Le statut est obligatoire")
    private StatutChauffeur statut;

    @NotNull(message = "La date de début de disponibilité est obligatoire")
    @Future(message = "La date de début doit être dans le futur")
    private LocalDateTime disponibiliteDebut;

    @NotNull(message = "La date de fin de disponibilité est obligatoire")
    @Future(message = "La date de fin doit être dans le futur")
    private LocalDateTime disponibiliteFin;

}
