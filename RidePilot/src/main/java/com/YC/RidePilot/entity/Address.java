package com.YC.RidePilot.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @NotBlank(message = "City is required")
    private String ville;

    @NotBlank(message = "Neighborhood is required")
    private String quartier;
}
