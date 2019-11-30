package com.parcom.classroom.model.student;


import com.parcom.classroom.model.group.Group;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String familyName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", referencedColumnName = "id", nullable = false)
    private Group group;

}
