package com.parcom.classroom.model.group;

import com.parcom.classroom.model.school.School;
import com.parcom.classroom.model.user.User;
import com.parcom.i18n.LocalizationUtils;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private  final  GroupRepository groupRepository;
    private  final  GroupToUserRepository groupToUserRepository;
    private  final MessageSource messageSource;

    public Group getCurrentGroup() {
        return getById(UserUtils.getIdGroup());
    }

    List<Group> getMyGroups() {
        return groupToUserRepository.findMyGroups(UserUtils.getIdUser());
    }

    public Group getById(Long idGroup) {
        return groupRepository.findById(idGroup).orElseThrow(() -> new EntityNotFoundException(LocalizationUtils.getLocalizedResource(messageSource,"group.not_found")));
    }

    public Group create(String name, School school) {
        return groupRepository.save(Group.builder().name(name).
                                                    school(school).build());
    }


}
