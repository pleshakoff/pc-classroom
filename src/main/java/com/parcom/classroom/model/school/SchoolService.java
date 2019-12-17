package com.parcom.classroom.model.school;


import com.parcom.exceptions.NotFoundParcomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private static final String SCHOOL_NOT_FOUND = "school.not_found" ;
    private final SchoolRepository schoolRepository;

    public School getOrCreate(Long id, String name) {

        if (id != null) {
            return schoolRepository.findById(id).orElseThrow(() ->  new NotFoundParcomException(SCHOOL_NOT_FOUND));
        }
        else
           return schoolRepository.save(School.builder().name(name).build());
    }


}
