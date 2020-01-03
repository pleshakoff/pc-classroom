package com.parcom.classroom.model.group;

import com.parcom.classroom.model.school.School;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public
class GroupServiceImpl implements GroupService {

    private static final String GROUP_NOT_FOUND = "group.not_found";

    private  final  GroupRepository groupRepository;
    private  final  GroupToUserRepository groupToUserRepository;

    @Override
    public Group getCurrentGroup() {
        return getById(UserUtils.getIdGroup());
    }

    public List<Group> getMyGroups() {
        return groupToUserRepository.findMyGroups(UserUtils.getIdUser());
    }

    @Override
    public Group getById(Long idGroup) {
        return groupRepository.findById(idGroup).orElseThrow(() -> new NotFoundParcomException(GROUP_NOT_FOUND));
    }

    @Override
    public Group create(String name, School school) {
        log.info("Group creation");
        return groupRepository.save(Group.builder().name(name).
                                                    school(school).build());
    }


}
