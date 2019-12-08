package com.parcom.classroom.model.group;

import com.parcom.classroom.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GroupToUserRepository extends CrudRepository<GroupToUser, Long> {

    @Query("select  g.group from GroupToUser g " +
            "where g.user.id = :idUser ")
    List<Group> findMyGroups(@Param("idUser") Long idUser);

    @Query("select g.user from GroupToUser g " +
            "where g.group.id = :idGroup ")
    List<User> findMyGroupUsers(@Param("idGroup") Long idGroup);


}
