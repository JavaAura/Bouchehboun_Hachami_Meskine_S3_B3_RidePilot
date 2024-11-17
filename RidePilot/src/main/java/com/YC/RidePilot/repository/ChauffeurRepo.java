package com.YC.RidePilot.repository;

import com.YC.RidePilot.entity.Chauffeur;
import com.YC.RidePilot.enums.StatutChauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChauffeurRepo extends JpaRepository<Chauffeur,Long> {
    List<Chauffeur> findByNomContainingIgnoreCase(String nom);



   @Query("SELECT c FROM Chauffeur c WHERE " +
            "((c.disponibiliteDebut = :dateDebut AND c.disponibiliteFin <= :dateFin  AND c.nom=:nom))")
    List<Chauffeur> findOverlappingChauffeurs(
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin,
            @Param("nom") String nom
    );

    long countByStatut(StatutChauffeur statut);

}
