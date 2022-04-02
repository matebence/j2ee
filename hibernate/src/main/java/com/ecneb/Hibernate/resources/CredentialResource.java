package com.ecneb.Hibernate.resources;

import org.springframework.http.HttpStatus;
import com.ecneb.Hibernate.entities.Credential;
import com.ecneb.Hibernate.services.CredentialService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "credential-resource/api", produces = "application/json")
public class CredentialResource {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Credential credential){
        credentialService.persist(credential);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Credential credential){
        Credential result = credentialService.search(id).orElse(new Credential());
        credentialService.update(result, credential.getUsername(), credential.getPassword());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        credentialService.delete(id);
    }

    @GetMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Credential search(@PathVariable Long id) {
        return credentialService.search(id).orElse(new Credential());
    }
}
