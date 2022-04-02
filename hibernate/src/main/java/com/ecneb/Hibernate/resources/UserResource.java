package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.entities.User;
import com.ecneb.Hibernate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "user-resource/api", produces = "application/json")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody User user){
        userService.persist(user);
    }

    @GetMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User search(@PathVariable Long id) {
        return userService.search(id).orElse(new User());
    }
}
