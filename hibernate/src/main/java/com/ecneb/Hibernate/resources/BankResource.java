package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.entities.Bank;
import com.ecneb.Hibernate.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "bank-resource/api", produces = "application/json")
public class BankResource {

    @Autowired
    private BankService bankService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Bank bank){
        bankService.persist(bank);
    }

    @PostMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bank search(@PathVariable Long id) {
        return bankService.search(id).orElse(new Bank());
    }
}
