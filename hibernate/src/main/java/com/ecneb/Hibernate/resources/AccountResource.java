package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.entities.Account;
import com.ecneb.Hibernate.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "account-resource/api", produces = "application/json")
public class AccountResource {

    @Autowired
    private AccountService accountService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Account account){
        accountService.persist(account);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Account account){
        Account result = accountService.search(id).orElse(new Account());
        accountService.update(result, account.getName());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        accountService.delete(id);
    }

    @GetMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account search(@PathVariable Long id) {
        return accountService.search(id).orElse(new Account());
    }
}
