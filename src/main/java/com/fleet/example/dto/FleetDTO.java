package com.fleet.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FleetDTO {
    private long id;
    private String name;
    private String vin;
    private String licensePlateNumber;
    private Object properties;
}
