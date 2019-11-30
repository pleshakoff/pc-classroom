package com.parcom.classroom.data;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
