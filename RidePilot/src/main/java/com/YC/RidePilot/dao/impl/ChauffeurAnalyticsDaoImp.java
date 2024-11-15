package com.YC.RidePilot.dao.impl;

import com.YC.RidePilot.dao.ChauffeurAnalyticsDao;
import com.YC.RidePilot.entity.Chauffeur;
import com.YC.RidePilot.entity.dto.DisponibilitePlageDto;
import com.YC.RidePilot.enums.StatutChauffeur;
import com.YC.RidePilot.repository.ChauffeurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChauffeurAnalyticsDaoImp implements ChauffeurAnalyticsDao {

    private final ChauffeurRepo chauffeurRepository;

    @Override
    public double calculateTauxOccupation() {
        long totalChauffeurs = chauffeurRepository.count();
        long chauffeursEnCourse = chauffeurRepository.countByStatut(StatutChauffeur.EN_COURSE);
        return totalChauffeurs > 0 ? (double) (chauffeursEnCourse * 100) / totalChauffeurs : 0;
    }

    @Override
    public Map<StatutChauffeur, Long> getStatutDistribution() {
        return chauffeurRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Chauffeur::getStatut,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<LocalDateTime, Long> getDisponibiliteDistribution() {
        return chauffeurRepository.findAll().stream()
                .filter(c -> c.getDisponibiliteDebut() != null)
                .collect(Collectors.groupingBy(
                        Chauffeur::getDisponibiliteDebut,
                        Collectors.counting()
                ));
    }


    @Override
    public Map<LocalDateTime, Long> getDisponibiliteDistributionfin() {
        return chauffeurRepository.findAll().stream()
                .filter(c -> c.getDisponibiliteFin() != null )
                .collect(Collectors.groupingBy(
                        Chauffeur::getDisponibiliteFin,
                        Collectors.counting()
                ));
    }

    @Override
    public List<DisponibilitePlageDto> getDisponibilite() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return chauffeurRepository.findAll().stream()
                .filter(chauffeur ->
                        chauffeur.getDisponibiliteDebut().isAfter(startOfDay) &&
                                chauffeur.getDisponibiliteDebut().isBefore(endOfDay) &&
                                chauffeur.getDisponibiliteFin().isAfter(startOfDay) &&
                                chauffeur.getDisponibiliteFin().isBefore(endOfDay))
                .sorted(Comparator.comparing(Chauffeur::getDisponibiliteDebut))
                .map(DisponibilitePlageDto::toDisponibilitePlageDto)
                .collect(Collectors.toList());


    }


} 