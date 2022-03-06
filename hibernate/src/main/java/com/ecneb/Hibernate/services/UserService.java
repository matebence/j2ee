package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.entities.User;
import com.ecneb.Hibernate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void persist(User user) {
        userRepository.save(user);
    }

    @Transactional
    public Optional<User> search(Long id) {
        return userRepository.findById(id);
    }
}
