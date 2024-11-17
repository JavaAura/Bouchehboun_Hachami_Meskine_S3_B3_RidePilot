package com.YC.RidePilot.services.InterfacesServices;

import com.YC.RidePilot.entity.dto.ReservationDTO;
import com.YC.RidePilot.entity.dto.ReservationResponseDTO;

import java.util.List;

public interface IReservation {

    ReservationResponseDTO createReservation(ReservationDTO reservationDTO);

     // 2. Modifier une réservation
     ReservationResponseDTO updateReservation(Long id, ReservationDTO reservationDTO);

     // 3. Supprimer une réservation
     void deleteReservation(Long id);

     // 4. Afficher une réservation par ID
     ReservationDTO getReservationById(Long id);

     // 5. Afficher toutes les réservations
     List<ReservationResponseDTO> getAllReservations();


 /*
     // 6. Analyses et statistiques
     ReservationAnalyticsDTO getReservationAnalytics();


   */
}
