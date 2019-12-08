package com.parcom.classroom.model.group;

import com.parcom.classroom.model.school.School;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private  final  GroupRepository groupRepository;
    private  final  GroupToUserRepository groupToUserRepository;

    public Group getCurrentGroup() {
        return getById(UserUtils.getIdGroup());
    }

    public List<Group> getMyGroups() {
        return groupToUserRepository.findMyGroups(UserUtils.getIdUser());
    }


    public Group getById(Long idGroup) {
        return groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException("Group not found"));
    }

    public Group create(String name, School school) {
        return groupRepository.save(Group.builder().name(name).
                                                    school(school).build());
    }


}
