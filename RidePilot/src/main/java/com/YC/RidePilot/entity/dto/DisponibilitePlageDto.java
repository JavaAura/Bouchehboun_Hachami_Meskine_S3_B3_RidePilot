package com.YC.RidePilot.entity.dto;

import com.YC.RidePilot.entity.Chauffeur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilitePlageDto {

    private String debut;
    private String fin;
    private String nom;
    private String prenom;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DisponibilitePlageDto toDisponibilitePlageDto(Chauffeur chauffeur) {
        return new DisponibilitePlageDto(
                chauffeur.getDisponibiliteDebut().format(formatter),
                chauffeur.getDisponibiliteFin().format(formatter),
                chauffeur.getNom(),
                chauffeur.getPrenom()
        );
    }

}
