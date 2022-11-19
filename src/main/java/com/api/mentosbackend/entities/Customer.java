package com.api.mentosbackend.entities;

import com.api.mentosbackend.util.ISetId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements ISetId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    @Column(name = "type", length = 11, nullable = false)
    private String customerType;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "upc_code", length = 10, nullable = false)
    private String upcCode;

    @Column(name = "career", length = 100)
    private String career;

    @Column(name = "cycle")
    private int cycle;

    @Column(name = "points")
    private Long points;
}
