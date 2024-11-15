package com.YC.RidePilot.utilis;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {
    private int status;
    private String message;
}