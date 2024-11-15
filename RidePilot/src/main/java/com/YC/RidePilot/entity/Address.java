package com.YC.RidePilot.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @NotNull(message = "City is required")
    private String ville;

    @NotNull(message = "Neighborhood is required")
    private String quartier;

}
