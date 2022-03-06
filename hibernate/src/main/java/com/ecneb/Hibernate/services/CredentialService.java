package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.entities.Credential;
import com.ecneb.Hibernate.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Transactional
    public void persist(Credential credential) {
        credentialRepository.save(credential);
    }

    @Transactional
    public void update(Credential credential, String username, String password) {
        credential.setUsername(username);
        credential.setPassword(password);
        credentialRepository.save(credential);
    }

    @Transactional
    public void delete(Long id) {
        credentialRepository.deleteById(id);
    }

    @Transactional
    public Optional<Credential> search(Long id) {
        return credentialRepository.findById(id);
    }
}
