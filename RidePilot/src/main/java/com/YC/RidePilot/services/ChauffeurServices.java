package com.YC.RidePilot.services;

import com.YC.RidePilot.dao.ChauffeurAnalyticsDao;
import com.YC.RidePilot.entity.Chauffeur;
import com.YC.RidePilot.entity.Reservation;
import com.YC.RidePilot.entity.dto.ChauffeurAnalyticsDto;
import com.YC.RidePilot.entity.dto.ChauffeurDto;
import com.YC.RidePilot.entity.mapper.ChauffeurMapperDTO;
import com.YC.RidePilot.enums.StatutChauffeur;
import com.YC.RidePilot.repository.ChauffeurRepo;
import com.YC.RidePilot.repository.ReservationRepository;
import com.YC.RidePilot.services.InterfacesServices.ChauffeurInterfaces;
import com.YC.RidePilot.exception.Chauffeur.NotFoundException;
import com.YC.RidePilot.exception.Chauffeur.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChauffeurServices implements ChauffeurInterfaces {

    private final ChauffeurRepo chauffeurRepository;

    private  ChauffeurMapperDTO chauffeurMapper = ChauffeurMapperDTO.INSTANCE;
    private final ReservationRepository reservationRepository;
    private final ChauffeurAnalyticsDao chauffeurAnalyticsDao;

    @Override
    public ChauffeurDto create(ChauffeurDto chauffeurDto) {
        List<Chauffeur> overlappingChauffeurs = chauffeurRepository.findOverlappingChauffeurs(
                chauffeurDto.getDisponibiliteDebut(),
                chauffeurDto.getDisponibiliteFin(),
                chauffeurDto.getNom()
        );

        if (!overlappingChauffeurs.isEmpty()) {
            throw new ValidationException(
                    "Un chauffeur est déjà programmé pour cette période. "
            );
        }


        Chauffeur chauffeur = chauffeurMapper.toEntity(chauffeurDto);
         chauffeurRepository.save(chauffeur);
      return chauffeurMapper.toDto(chauffeur);

    }

    @Override
    public ChauffeurDto update(ChauffeurDto chauffeurDto) {
        if (chauffeurDto.getId() == null) {
            throw new ValidationException("L'ID du chauffeur ne peut pas être null pour la mise à jour");
        }

      chauffeurRepository.findById(chauffeurDto.getId())
            .orElseThrow(() -> new NotFoundException("Chauffeur non trouvé avec l'ID: " + chauffeurDto.getId()));

        Chauffeur chauffeur = chauffeurMapper.toEntity(chauffeurDto);
        chauffeurRepository.save(chauffeur);
        return chauffeurMapper.toDto(chauffeur);
    }

    @Override
    public void delete(Long id) {
        Chauffeur chauffeur = chauffeurRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Chauffeur non trouvé avec l'ID : " + id));
        chauffeurRepository.delete(chauffeur);
    }

    @Override
    public List<ChauffeurDto> getAll() {
        return chauffeurRepository.findAll().stream()
                .map(chauffeurMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ChauffeurDto> getById(Long id) {
        return chauffeurRepository.findById(id)
                .map(chauffeurMapper::toDto);
    }

    @Override
    public List<ChauffeurDto> findByNom(String nom) {
        return chauffeurRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(chauffeurMapper::toDto)
                .toList();
    }


    @Override
    public long countChauffeurs() {
        return chauffeurRepository.count();
    }

    @Override
    public ChauffeurAnalyticsDto getAnalytics() {
        double tauxOccupation = chauffeurAnalyticsDao.calculateTauxOccupation();
        Map<StatutChauffeur, Long> statutDistribution = chauffeurAnalyticsDao.getStatutDistribution();
        Map<LocalDateTime, Long> disponibiliteDistribution = chauffeurAnalyticsDao.getDisponibiliteDistribution();
        Map<LocalDateTime, Long> disponibiliteDistributionfin = chauffeurAnalyticsDao.getDisponibiliteDistributionfin();
    
    
        Map<String, Long> repartitionStatuts = statutDistribution.entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().name(),
                Map.Entry::getValue
            ));

        Map<String, Long> repartitionParPlageHoraire = disponibiliteDistribution.entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                Map.Entry::getValue,
                (v1, v2) -> v1 + v2  
            ));

        Map<String, Long> repartitionParPlageHoraireFin = disponibiliteDistributionfin.entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                Map.Entry::getValue,
                (v1, v2) -> v1 + v2  
            ));


        return ChauffeurAnalyticsDto.builder()
            .tauxOccupation(tauxOccupation)
            .repartitionStatuts(repartitionStatuts)
            .repartitionParPlageHoraire(repartitionParPlageHoraire)
            .repartitionParPlageHorairefin(repartitionParPlageHoraireFin)
                .disponibilitePlages(chauffeurAnalyticsDao.getDisponibilite())
            .build();
    }

    @Override
    public ChauffeurDto verifyAvailability(Long chauffeurId) {
        // Fetch the chauffeur details from the database (assuming you have a Chauffeur entity)
        Chauffeur chauffeur = chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new NotFoundException("Chauffeur not found with id: " + chauffeurId));
        if(chauffeur.getStatut().equals("DISPONIBLE")){
            throw new RuntimeException("Chauffeur n'est pas disponible");
        }



        List<Reservation> ongoingReservations = reservationRepository.findOngoingReservationsForChauffeur(chauffeurId);

        boolean isAvailable = ongoingReservations.isEmpty();

        // If the chauffeur is available, map the Chauffeur entity to a ChauffeurDto
        if (isAvailable) {
            return ChauffeurMapperDTO.INSTANCE.toDto(chauffeur);
        } else {
            return null;
        }
    }

}
