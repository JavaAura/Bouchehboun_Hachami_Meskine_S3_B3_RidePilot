package com.YC.RidePilot.repository;

import com.YC.RidePilot.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT r FROM Reservation r WHERE r.chauffeur.id = :chauffeurId AND r.heureFinCourse > CURRENT_TIMESTAMP")
    List<Reservation> findOngoingReservationsForChauffeur(@Param("chauffeurId") Long chauffeurId);

}
