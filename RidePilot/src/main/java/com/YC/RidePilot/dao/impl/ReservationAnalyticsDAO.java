package com.YC.RidePilot.dao.impl;

import com.YC.RidePilot.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationAnalyticsDAO extends JpaRepository<Reservation, Long> {

    @Query("SELECT AVG(r.prix / r.distanceKm) " +
            "FROM Reservation r WHERE r.distanceKm > 0")
    Double calculateAveragePricePerKm();

    @Query("SELECT AVG(r.distanceKm) " +
            "FROM Reservation r WHERE r.distanceKm > 0")
    Double calculateAverageDistance();

    @Query("SELECT CAST(EXTRACT(HOUR FROM r.dateHeure) AS integer) AS hour, COUNT(r) AS reservationCount " +
            "FROM Reservation r " +
            "GROUP BY EXTRACT(HOUR FROM r.dateHeure) " +
            "ORDER BY hour ASC")
    List<Object[]> findReservationsByHourSlot();

/
}
