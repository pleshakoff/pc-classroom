package com.parcom.classroom.model.group;


import com.parcom.classroom.model.school.School;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "Groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_school", referencedColumnName = "id", nullable = false)
    private School school;

}
