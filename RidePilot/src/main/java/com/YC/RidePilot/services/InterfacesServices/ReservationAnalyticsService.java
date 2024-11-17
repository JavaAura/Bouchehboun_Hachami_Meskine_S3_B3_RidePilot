package com.YC.RidePilot.services.InterfacesServices;

import com.YC.RidePilot.dao.impl.ReservationAnalyticsDAO;
import com.YC.RidePilot.entity.dto.ReservationAnalyticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationAnalyticsService {


    private ReservationAnalyticsDAO reservationAnalyticsDAO;

    @Autowired
    public ReservationAnalyticsService(ReservationAnalyticsDAO reservationAnalyticsDAO){
        this.reservationAnalyticsDAO = reservationAnalyticsDAO;
    }



    public ReservationAnalyticsDTO getReservationAnalytics() {
        Double averagePricePerKm = reservationAnalyticsDAO.calculateAveragePricePerKm();
        if (averagePricePerKm == null) {
            averagePricePerKm = 0.0;
        }

        Double averageDistance = reservationAnalyticsDAO.calculateAverageDistance();
        if (averageDistance == null) {
            averageDistance = 0.0;
        }

        List<Object[]> results = reservationAnalyticsDAO.findReservationsByHourSlot();
        Map<Integer, Long> reservationsByHour = new HashMap<>();
        if (results != null) {
            for (Object[] row : results) {
                Integer hour = ((Number) row[0]).intValue(); // Convert to Integer
                Long count = ((Number) row[1]).longValue(); // Convert to Long
                reservationsByHour.put(hour, count);
            }
        }


        return new ReservationAnalyticsDTO(averagePricePerKm, averageDistance, reservationsByHour);
    }

}
