package com.parcom.classroom.model.group;

import com.parcom.classroom.model.school.School;
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

    public Group getById(Long idGroup) {
        return groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException("Группа не найдена"));
    }

    public Group create(String name, School school) {
        return groupRepository.save(Group.builder().name(name).
                                                    school(school).build());
    }


}
