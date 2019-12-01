package com.parcom.classroom.model.school;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public School getOrCreate(Long id, String name) {

        if (id != null) {
            return schoolRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        }
        else
           return schoolRepository.save(School.builder().name(name).build());
    }


}
