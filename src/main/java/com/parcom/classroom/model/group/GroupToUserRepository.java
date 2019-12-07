package com.parcom.classroom.model.group;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GroupToUserRepository extends CrudRepository<GroupToUser, Long> {

    @Query("select  g.group from GroupToUser g " +
            "where idUser = :idUser ")

    List<Group> findMyGroups(@Param("idUser") Long idUser);

}
