package com.YC.RidePilot.services;

import com.YC.RidePilot.entity.Address;
import com.YC.RidePilot.entity.dto.ChauffeurDto;
import com.YC.RidePilot.entity.dto.ReservationResponseDTO;
import com.YC.RidePilot.enums.StatutReservation;
import com.YC.RidePilot.enums.TypeVehicule;
import com.YC.RidePilot.repository.ChauffeurRepo;
import com.YC.RidePilot.repository.ReservationRepository;
import com.YC.RidePilot.repository.VehiculeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YC.RidePilot.entity.Chauffeur;
import com.YC.RidePilot.entity.Reservation;
import com.YC.RidePilot.entity.Vehicule;
import com.YC.RidePilot.entity.dto.ReservationDTO;
import com.YC.RidePilot.entity.mapper.ReservationMapper;
import com.YC.RidePilot.services.InterfacesServices.IReservation;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ReservationService implements IReservation {

    private final ReservationRepository reservationRepository;
    private final ChauffeurRepo chauffeurRepo;
    private final VehiculeRepository vehiculeRepository;
    private final ChauffeurServices chauffeurServices;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository , ChauffeurServices chauffeurServices,VehiculeRepository vehiculeRepository ,ChauffeurRepo chauffeurRepo){
        this.reservationRepository = reservationRepository;
        this.chauffeurServices = chauffeurServices;
        this.chauffeurRepo = chauffeurRepo;
        this.vehiculeRepository = vehiculeRepository;
    }


    @Override
    public ReservationResponseDTO createReservation(ReservationDTO reservationDTO) {

        // Validate required fields for arrival address
        if (reservationDTO.getAdresseArriveeVille() == null || reservationDTO.getAdresseArriveeQuartier() == null) {
            throw new IllegalArgumentException("Both 'ville' and 'quartier' for 'adresseArrivee' are required");
        }

        // Validate and fetch Chauffeur entity
        Chauffeur chauffeur = chauffeurRepo.findById(reservationDTO.getChauffeurId())
                .orElseThrow(() -> new EntityNotFoundException("Chauffeur with ID " + reservationDTO.getChauffeurId() + " not found"));

        // Log chauffeur details
        log.info("Chauffeur Name: {}", chauffeur.getNom());

        // Validate and fetch Vehicule entity
        Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicule with ID " + reservationDTO.getVehiculeId() + " not found"));

        // Log vehicule details
        log.info("Vehicule ID: {}, Type: {}", vehicule.getId(), vehicule.getType());

        // Verify Chauffeur availability (assuming this checks if the chauffeur is free)
        ChauffeurDto chauffeurDto = chauffeurServices.verifyAvailability(reservationDTO.getChauffeurId());
        if (chauffeurDto == null) {
            throw new IllegalStateException("Chauffeur with ID " + reservationDTO.getChauffeurId() + " is not available");
        }

        // Verify Vehicule availability (optional, if you implement such logic)
        // Vehicule vehiculeDetails = vehiculeService.verifyAvailability(reservationDTO.getVehiculeId());

        // Calculate price based on distance and vehicule type
        double prix = calculatePrice(reservationDTO.getDistanceKm(), vehicule.getType());
        reservationDTO.setPrix(prix);

        // Map DTO to Entity
        Reservation reservation = ReservationMapper.INSTANCE.toEntity(reservationDTO);
        reservation.setChauffeur(chauffeur); // Set the resolved Chauffeur entity
        reservation.setVehicule(vehicule);   // Set the resolved Vehicule entity
        reservation.setStatut(StatutReservation.CREATED); // Set default status if not provided

        // Save the reservation entity to the database
        reservation = reservationRepository.save(reservation);

        // Map Entity back to DTO for the response
        ReservationResponseDTO responseDTO = ReservationMapper.INSTANCE.toDto(reservation);

        // Set additional chauffeur and vehicule details in the response DTO
        responseDTO.setChauffeurName(chauffeur.getNom() + " " + chauffeur.getPrenom());
        responseDTO.setVehiculeType(vehicule.getType().toString());

        return responseDTO;
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        existingReservation.setDateHeure(reservationDTO.getDateHeure());
        existingReservation.setPrix(reservationDTO.getPrix());
        existingReservation.setStatut(reservationDTO.getStatut());
        existingReservation.setDistanceKm(reservationDTO.getDistanceKm());
        existingReservation.setHeureDebutCourse(reservationDTO.getHeureDebutCourse());
        existingReservation.setHeureFinCourse(reservationDTO.getHeureFinCourse());

        // Convert Address from DTO fields
        Address adresseDepart = new Address(reservationDTO.getAdresseDepartVille(), reservationDTO.getAdresseDepartQuartier());
        Address adresseArrivee = new Address(reservationDTO.getAdresseArriveeVille(), reservationDTO.getAdresseArriveeQuartier());

        existingReservation.setAdresseDepart(adresseDepart);
        existingReservation.setAdresseArrivee(adresseArrivee);

        if (reservationDTO.getChauffeurId() != null) {
            Chauffeur chauffeur = chauffeurRepo.findById(reservationDTO.getChauffeurId())
                    .orElseThrow(() -> new EntityNotFoundException("Chauffeur not found"));
            existingReservation.setChauffeur(chauffeur);
        }

     /*   if (reservationDTO.getVehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                    .orElseThrow(() -> new EntityNotFoundException("Vehicule not found"));
            existingReservation.setVehicule(vehicule);
        }*/

        Reservation updatedReservation = reservationRepository.save(existingReservation);

        return ReservationMapper.INSTANCE.toDto(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        reservationRepository.delete(reservation);
    }

    public ReservationDTO getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> new ReservationDTO(
                        reservation.getId(),
                        reservation.getDateHeure(),
                        reservation.getHeureDebutCourse(),
                        reservation.getHeureFinCourse(),
                        reservation.getAdresseDepart().getVille(),
                        reservation.getAdresseDepart().getQuartier(),
                        reservation.getAdresseArrivee().getVille(),
                        reservation.getAdresseArrivee().getQuartier(),
                        reservation.getDistanceKm(),
                        reservation.getPrix(),
                        reservation.getStatut(),
                        reservation.getChauffeur().getId(),
                        reservation.getVehicule().getId()))
                .orElse(null); // If reservation not found, return null
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        // Map each reservation to a DTO
        return reservations.stream().map(reservation -> {
            // Fetch related entities
            Chauffeur chauffeur = reservation.getChauffeur()!= null
                    ? chauffeurRepo.findById(reservation.getChauffeur().getId()).orElse(null)
                    : null;

            Vehicule vehicule = reservation.getVehicule() != null
                    ? vehiculeRepository.findById(reservation.getVehicule().getId()).orElse(null)
                    : null;

            // Map to DTO
            ReservationResponseDTO dto = new ReservationResponseDTO();
            dto.setId(reservation.getId());
            dto.setDateHeure(reservation.getDateHeure().toString());
            dto.setHeureDebutCourse(reservation.getHeureDebutCourse().toString());
            dto.setHeureFinCourse(reservation.getHeureFinCourse().toString());

            dto.setAdresseDepartVille(reservation.getAdresseDepart().getVille());
            dto.setAdresseDepartQuartier(reservation.getAdresseDepart().getQuartier());

            dto.setAdresseArriveeVille(reservation.getAdresseArrivee().getVille());
            dto.setAdresseArriveeQuartier(reservation.getAdresseArrivee().getQuartier());

            dto.setAdresseArriveeQuartier(reservation.getAdresseArrivee().getQuartier());
            dto.setDistanceKm(reservation.getDistanceKm());
            dto.setPrix(reservation.getPrix());
            dto.setStatut(reservation.getStatut().toString());
            dto.setChauffeurName(chauffeur != null ? chauffeur.getNom() + " " + chauffeur.getPrenom() : null);
            dto.setVehiculeType(vehicule != null ? vehicule.getType().toString() : null);

            return dto;
        }).collect(Collectors.toList());
    }
//
//    @Override
//    public ReservationAnalyticsDTO getReservationAnalytics() {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'getReservationAnalytics'");
//    }
//
//

    public static double calculatePrice(double distanceKm, TypeVehicule vehicleType) {
        if (distanceKm <= 0 || distanceKm > 100) {
            throw new IllegalArgumentException("Distance must be greater than 0 and less than or equal to 100 km.");
        }

        // Base rates for each vehicle type
        double ratePerKm;
        switch (vehicleType) {
            case BERLINE:
                ratePerKm = 5.0;
                break;
            case VAN:
                ratePerKm = 7.0;
                break;
            case MINIBUS:
                ratePerKm = 9.0;
                break;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }

        double price = distanceKm * ratePerKm;

        // Round to two decimal places
        return price;
    }
    
}
