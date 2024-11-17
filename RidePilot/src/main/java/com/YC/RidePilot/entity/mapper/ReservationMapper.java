package com.YC.RidePilot.entity.mapper;

import com.YC.RidePilot.entity.dto.ReservationResponseDTO;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

import com.YC.RidePilot.entity.Reservation;
import com.YC.RidePilot.entity.dto.ReservationDTO;

@Mapper
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "adresseDepartVille", target = "adresseDepart.ville")
    @Mapping(source = "adresseDepartQuartier", target = "adresseDepart.quartier")
    @Mapping(source = "adresseArriveeVille", target = "adresseArrivee.ville")
    @Mapping(source = "adresseArriveeQuartier", target = "adresseArrivee.quartier")
    Reservation toEntity(ReservationDTO reservationDTO);

    @Mapping(source = "adresseDepart.ville", target = "adresseDepartVille")
    @Mapping(source = "adresseDepart.quartier", target = "adresseDepartQuartier")
    @Mapping(source = "adresseArrivee.ville", target = "adresseArriveeVille")
    @Mapping(source = "adresseArrivee.quartier", target = "adresseArriveeQuartier")
    ReservationResponseDTO toDto(Reservation reservation);

}