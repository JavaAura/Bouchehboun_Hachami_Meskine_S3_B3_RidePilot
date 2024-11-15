package com.YC.RidePilot.services.InterfacesServices;

import com.YC.RidePilot.entity.dto.ChauffeurAnalyticsDto;
import com.YC.RidePilot.entity.dto.ChauffeurDto;
import java.util.List;
import java.util.Optional;

public interface ChauffeurInterfaces {
    ChauffeurDto create(ChauffeurDto chauffeurDto);
    ChauffeurDto update(ChauffeurDto chauffeurDto);
    void delete(Long id);
    List<ChauffeurDto> getAll();
    Optional<ChauffeurDto> getById(Long id);
    List<ChauffeurDto> findByNom(String nom);
    long countChauffeurs();
    ChauffeurAnalyticsDto getAnalytics();

}
