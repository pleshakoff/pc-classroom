package com.parcom.classroom.model.group;

import com.parcom.classroom.security.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class GroupService {

    private  final  GroupRepository groupRepository;


    Group getCurrentGroup() {
        return groupRepository.findById(UserUtils.getGroupId()).orElseThrow(EntityNotFoundException::new);
    }
}
