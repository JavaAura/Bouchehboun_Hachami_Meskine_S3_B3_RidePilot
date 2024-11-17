package com.YC.RidePilot.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    @NotNull(message = "La ville est obligatoire")
    private String ville;

    @NotNull(message = "Le quartier est obligatoire")
    private String quartier;
}
