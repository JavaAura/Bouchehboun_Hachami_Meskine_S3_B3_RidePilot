package com.YC.RidePilot.services;

import com.YC.RidePilot.entity.dto.ChauffeurDto;
import com.YC.RidePilot.enums.StatutChauffeur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChauffeurServicesIntegrationTest {

    @Autowired
    private ChauffeurServices chauffeurServices;



    @Test
    void testCreateAndRetrieveChauffeur() {

        LocalDateTime now = LocalDateTime.now();
        ChauffeurDto chauffeurDto = ChauffeurDto.builder()
                .nom("Integration")
                .prenom("Test")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build();


        ChauffeurDto created = chauffeurServices.create(chauffeurDto);


        assertNotNull(created.getId());
        Optional<ChauffeurDto> found = chauffeurServices.getById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Integration", found.get().getNom());
    }

    @Test
    void testUpdateChauffeur() {

        LocalDateTime now = LocalDateTime.now();
        ChauffeurDto initial = chauffeurServices.create(ChauffeurDto.builder()
                .nom("Initial")
                .prenom("Test")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build());


        initial.setNom("Updated");
        ChauffeurDto updated = chauffeurServices.update(initial);


        assertEquals("Updated", updated.getNom());
        assertEquals(initial.getId(), updated.getId());
    }

    @Test
    void testDeleteChauffeur() {

        LocalDateTime now = LocalDateTime.now();
        ChauffeurDto chauffeur = chauffeurServices.create(ChauffeurDto.builder()
                .nom("ToDelete")
                .prenom("Test")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build());


        chauffeurServices.delete(chauffeur.getId());


        Optional<ChauffeurDto> found = chauffeurServices.getById(chauffeur.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByNom() {

        LocalDateTime now = LocalDateTime.now();
        chauffeurServices.create(ChauffeurDto.builder()
                .nom("SearchTest")
                .prenom("Test")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build());


        List<ChauffeurDto> found = chauffeurServices.findByNom("Search");


        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(c -> c.getNom().equals("SearchTest")));
    }

    @Test
    void testGetAnalytics() {

        LocalDateTime now = LocalDateTime.now();
        chauffeurServices.create(ChauffeurDto.builder()
                .nom("Analytics")
                .prenom("Test")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build());


        var analytics = chauffeurServices.getAnalytics();


        assertNotNull(analytics);
        assertNotNull(analytics.getRepartitionStatuts());
        assertTrue(analytics.getTauxOccupation() >= 0);
    }
}
