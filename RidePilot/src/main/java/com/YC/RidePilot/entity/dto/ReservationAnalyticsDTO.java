package com.YC.RidePilot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ReservationAnalyticsDTO {
    private Double averagePricePerKm;
    private Double averageDistance;
    private Map<Integer, Long> reservationTimeSlotDistribution;
}


