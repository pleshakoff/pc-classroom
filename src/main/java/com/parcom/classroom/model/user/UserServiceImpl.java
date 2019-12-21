package com.parcom.classroom.model.user;

import com.parcom.classroom.model.group.Group;
import com.parcom.classroom.model.group.GroupToUser;
import com.parcom.classroom.model.group.GroupToUserRepository;
import com.parcom.classroom.model.student.Student;
import com.parcom.classroom.model.student.StudentToUser;
import com.parcom.classroom.model.student.StudentToUserRepository;
import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.network.Network;
import com.parcom.security_client.Checksum;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "user.not_found";
    private static final String USERS_URL = "users";
    public static final String SERVICE_NAME_SECURITY = "security";

    private final UserRepository userRepository;
    private  final Network network;
    private  final GroupToUserRepository groupToUserRepository;
    private  final StudentToUserRepository studentToUserRepository;


    @Override
    public User create(String email){
        if (userRepository.findUserByEmail(email) != null) {

            throw  new ParcomException("user.duplicate_email");

        }

        return userRepository.save( User.builder().email(email).build());
    }

    @Override
    public User current(){
        return userRepository.findById(UserUtils.getIdUser()).orElseThrow(()->new NotFoundParcomException(USER_NOT_FOUND) );
    }

    @Override
    public void addUserToGroup(@NotNull Group group, @NotNull User user) {
        groupToUserRepository.save(GroupToUser.builder().group(group).user(user).build());
    }

    @Override
    public void addUserToStudent(@NotNull Student student, @NotNull User user) {
        studentToUserRepository.save(StudentToUser.builder().student(student).user(user).build());
    }


    @Override
    public User getById(@NotNull Long id) {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new EntityNotFoundException(USER_NOT_FOUND);
        return allInGroup().stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(()->new NotFoundParcomException(USER_NOT_FOUND) );
    }

    @Override
    public List<User> allInGroup(){
        return groupToUserRepository.findMyGroupUsers(UserUtils.getIdGroup());
    }


    @Override
    @Transactional
    public User update(Long id, UserUpdateDto userUpdateDto)
    {
        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
           throw new ForbiddenParcomException();
        User user = getById(id);
        user.setPhone(userUpdateDto.getPhone());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setFamilyName(userUpdateDto.getFamilyName());
        user.setMiddleName(userUpdateDto.getMiddleName());
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public void delete(Long id){

        if ((UserUtils.getRole().equals(UserUtils.ROLE_PARENT))&&!UserUtils.getIdUser().equals(id))
            throw new ForbiddenParcomException();

        User user = getById(id);
        groupToUserRepository.deleteAllByUser(user);
        studentToUserRepository.deleteAllByUser(user);
        userRepository.deleteById(id);

        Map<String,String> additionalHeaders = new HashMap<>();
        additionalHeaders.put(Checksum.CHECKSUM,Checksum.createChecksum(id));
        network.callDelete(SERVICE_NAME_SECURITY,additionalHeaders,USERS_URL,id.toString());
    }


    @Override
    public void registerInSecurity(UserCreateDto userCreateDto){

        Map<String,String> additionalHeaders = new HashMap<>();
        additionalHeaders.put(Checksum.CHECKSUM,Checksum.createChecksum(userCreateDto.getId()));
        network.callPost(SERVICE_NAME_SECURITY,
                         userSecurityResponseDto.class,
                         userCreateDto,
                         additionalHeaders,
                         USERS_URL,
                         "register");
   }





}
