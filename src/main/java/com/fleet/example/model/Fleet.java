package com.fleet.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Fleet")
public class Fleet {

    @Transient
    public static final String SEQUENCE_NAME = "fleet_id_sequence";

    @Id
    private long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name size cannot be more than 50 character")
    private String name;

    @NotBlank(message = "VIN cannot be blank")
    @Size(max = 20, message = "VIN size cannot be more than 20 character")
    @Indexed(unique = true)
    private String vin;

    @NotBlank(message = "Plate Number cannot be blank")
    @Size(max = 30, message = "Plate Number size cannot be more than 30 character")
    @Indexed(unique = true)
    private String licensePlateNumber;

    private Object properties;
}
