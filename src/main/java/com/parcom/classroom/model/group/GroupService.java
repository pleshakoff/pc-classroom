package com.parcom.classroom.model.group;

import com.parcom.classroom.model.school.School;

public interface GroupService {
    Group getCurrentGroup();

    Group getById(Long idGroup);

    Group create(String name, School school);
}
