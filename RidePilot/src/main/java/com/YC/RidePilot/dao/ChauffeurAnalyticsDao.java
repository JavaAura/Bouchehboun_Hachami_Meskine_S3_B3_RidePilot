package com.YC.RidePilot.dao;

import com.YC.RidePilot.entity.dto.ChauffeurDto;
import com.YC.RidePilot.entity.dto.DisponibilitePlageDto;
import com.YC.RidePilot.enums.StatutChauffeur;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ChauffeurAnalyticsDao {
    double calculateTauxOccupation();
    Map<StatutChauffeur, Long> getStatutDistribution();
    Map<LocalDateTime, Long> getDisponibiliteDistribution();
    Map<LocalDateTime, Long> getDisponibiliteDistributionfin();
    List<DisponibilitePlageDto>getDisponibilite();
}
