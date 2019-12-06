package com.parcom.classroom.model.news;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcom.classroom.model.group.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String message;

    @Column()
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Long  idUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", referencedColumnName = "id", nullable = false)
    private Group group;



}
