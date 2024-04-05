package com.api.fileuploaddownload.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob  // é usada para indicar que um campo de um objeto Java deve ser mapeado para um tipo de dado de grande tamanho (large object), ou seja, armazenar um grande volume de dados textuais ou binários em uma base de dados relacional.
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;
    
}
