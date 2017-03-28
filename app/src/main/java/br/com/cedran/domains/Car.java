package br.com.cedran.domains;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "cars")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String brand;

    private String model;

    private String color;

    private Integer year;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastChangeDate;

}
