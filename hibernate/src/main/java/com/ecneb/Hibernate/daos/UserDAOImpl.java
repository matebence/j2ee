package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.entities.User;

import java.util.List;

public class UserDAOImpl extends AbstractDAO<User,Long> implements UserDAO {

    @Override
    public List<User> findByFirstName(String firstName) {
        return null;
    }
}