package com.parcom.classroom.services;

import com.parcom.classroom.data.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface UserRepository extends CrudRepository<Users, Long> {

    Users findUserByUsername(@Param("username") String username);

}
