package com.parcom.classroom.model.school;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
