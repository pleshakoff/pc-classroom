package com.parcom.classroom.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface UserRepository extends CrudRepository<Users, Long> {

    Users findUserByUsername(@Param("username") String username);

}
