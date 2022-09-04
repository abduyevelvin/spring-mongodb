package com.fleet.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDTO {
    private String message;
    private List<String> details;
}
