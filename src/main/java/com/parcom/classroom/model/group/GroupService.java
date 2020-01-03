package com.parcom.classroom.model.group;

import com.parcom.classroom.model.school.School;

import java.util.List;

public interface GroupService {
    Group getCurrentGroup();

    Group getById(Long idGroup);

    Group create(String name, School school);

    List<Group> getMyGroups();
}
