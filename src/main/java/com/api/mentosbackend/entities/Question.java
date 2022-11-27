package com.api.mentosbackend.entities;

import com.api.mentosbackend.util.ISetId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements ISetId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "made_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date madeAt;

    @ManyToOne(fetch = FetchType.LAZY) //muchas preguntas un usuario
    @JoinColumn(name = "user_id", nullable = false) //es la columna con la cual ambas tablas se van a relacionar
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Course course;
}
