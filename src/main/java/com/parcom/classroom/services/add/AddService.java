package com.parcom.classroom.services.add;

import com.parcom.classroom.model.user.User;

import javax.transaction.Transactional;

public interface AddService {

    User registerByGroup(AddMemberDto addDto);

    User registerByStudent(AddParentDto addDto);

    User registerNewGroup(AddGroupDto addDto);
}
