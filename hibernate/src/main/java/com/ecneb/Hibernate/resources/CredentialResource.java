package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.daos.CredentialDAO;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping(value = "credential-resource/api", produces = "application/json")
public class CredentialResource {

    @Autowired
    private CredentialDAO credentialDAO;

    @Autowired
    private CredentialService credentialService;

    @Value("${hibernate.repository.example}")
    private Boolean repositoryExample;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Credential credential){
        if (repositoryExample) {
            credentialService.persist(credential);
        } else {
            credentialDAO.persist(credential);
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Credential credential){
        Credential result;
        if (repositoryExample) {
             result = credentialService.search(id).orElse(new Credential());
            credentialService.update(result, credential.getUsername(), credential.getPassword());
        } else {
            result = credentialDAO.search(id);
            credentialDAO.update(result, credential.getUsername(), credential.getPassword());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        if (repositoryExample) {
            credentialService.delete(id);
        } else {
            credentialDAO.delete(id);
        }
    }

    @PostMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Credential search(@PathVariable Long id) {
        if (repositoryExample) {
            return credentialService.search(id).orElse(new Credential());
        } else {
            return credentialDAO.search(id);
        }
    }
}
