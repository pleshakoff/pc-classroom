package com.parcom.classroom.model.school;

public interface SchoolService {
    School getOrCreate(Long id, String name);
}
