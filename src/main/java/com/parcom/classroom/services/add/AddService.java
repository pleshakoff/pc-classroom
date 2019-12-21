package com.parcom.classroom.services.add;

import com.parcom.classroom.model.user.User;

import javax.transaction.Transactional;

public interface AddService {
    @Transactional
    User registerByGroup(AddMemberDto addDto);

    @Transactional
    User registerByStudent(AddParentDto addDto);

    @Transactional
    User registerNewGroup(AddGroupDto addDto);
}
