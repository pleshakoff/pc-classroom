package com.parcom.classroom.model.school;


import com.parcom.exceptions.NotFoundParcomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class SchoolServiceImpl implements SchoolService {

    private static final String SCHOOL_NOT_FOUND = "school.not_found" ;
    private final SchoolRepository schoolRepository;

    @Override
    public School getOrCreate(Long id, String name) {

        if (id != null) {
            log.info("Find school {}",id);
            return schoolRepository.findById(id).orElseThrow(() ->  new NotFoundParcomException(SCHOOL_NOT_FOUND));
        }
        else {
            log.info("Create school {}",name);
            return schoolRepository.save(School.builder().name(name).build());
        }
    }


}
