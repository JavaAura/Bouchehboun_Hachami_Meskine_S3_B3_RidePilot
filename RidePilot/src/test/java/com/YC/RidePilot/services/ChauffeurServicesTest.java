package com.YC.RidePilot.services;

import com.YC.RidePilot.entity.Chauffeur;
import com.YC.RidePilot.entity.dto.ChauffeurDto;
import com.YC.RidePilot.enums.StatutChauffeur;
import com.YC.RidePilot.exception.Chauffeur.ValidationException;
import com.YC.RidePilot.repository.ChauffeurRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChauffeurServicesTest {

    @Mock
    private ChauffeurRepo chauffeurRepository;


    @InjectMocks
    private ChauffeurServices chauffeurServices;

    private ChauffeurDto chauffeurDto;
    private Chauffeur chauffeur;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        chauffeurDto = ChauffeurDto.builder()
                .id(1L)
                .nom("Test")
                .prenom("User")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build();

        chauffeur = Chauffeur.builder()
                .id(1L)
                .nom("Test")
                .prenom("User")
                .statut(StatutChauffeur.DISPONIBLE)
                .disponibiliteDebut(now)
                .disponibiliteFin(now.plusHours(8))
                .build();
    }

    @Test
    void createChauffeur_Success() {
        when(chauffeurRepository.findOverlappingChauffeurs(any(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(chauffeurRepository.save(any())).thenReturn(chauffeur);

        ChauffeurDto result = chauffeurServices.create(chauffeurDto);

        assertNotNull(result);
        assertEquals("Test", result.getNom());
    }

    @Test
    void createChauffeur_ThrowsException() {
        when(chauffeurRepository.findOverlappingChauffeurs(any(), any(), any()))
                .thenReturn(List.of(chauffeur));

        assertThrows(ValidationException.class, () -> 
            chauffeurServices.create(chauffeurDto)
        );
    }

    @Test
    void updateChauffeur_Success() {
        when(chauffeurRepository.findById(1L)).thenReturn(Optional.of(chauffeur));
        when(chauffeurRepository.save(any())).thenReturn(chauffeur);

        ChauffeurDto result = chauffeurServices.update(chauffeurDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteChauffeur_Success() {
        when(chauffeurRepository.findById(1L)).thenReturn(Optional.of(chauffeur));

        chauffeurServices.delete(1L);

        verify(chauffeurRepository).delete(any());
    }

    @Test
    void getAllChauffeurs_Success() {
        when(chauffeurRepository.findAll()).thenReturn(List.of(chauffeur));

        List<ChauffeurDto> result = chauffeurServices.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
