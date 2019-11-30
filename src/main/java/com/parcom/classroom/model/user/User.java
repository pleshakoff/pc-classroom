package com.parcom.classroom.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcom.classroom.model.student.Student;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", referencedColumnName = "id")
    private Student group;


}