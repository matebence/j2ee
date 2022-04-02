package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.entities.User;

import java.util.List;

public interface UserDAO extends DAO<User,Long>{

    List<User> findByFirstName(String firstName);
}