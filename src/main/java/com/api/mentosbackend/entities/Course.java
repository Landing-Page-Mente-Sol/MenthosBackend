package com.api.mentosbackend.entities;

import com.api.mentosbackend.util.ISetId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity//indica que es una entidad
@Table(name = "courses")//indica que es un tabla
@Data//Loombok-> get y set
@NoArgsConstructor//Loombok -> constructor vacio
@AllArgsConstructor//Loombok -> constructor con todos sus parametros
public class Course implements ISetId {
    @Id // reconoce como primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //el INDENTITY id se va generar automaticamente
    private Long id;

    @Column(name = "name", length = 50, nullable = false) // columna en la base de datos
    private String name;

    @Column(name = "image", length = 500)
    private String image;
}
