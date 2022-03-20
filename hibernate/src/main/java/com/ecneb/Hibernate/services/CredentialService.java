package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.daos.CredentialDAO;
import com.ecneb.Hibernate.entities.Credential;
import com.ecneb.Hibernate.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CredentialService {

    @Autowired
    private CredentialDAO credentialDAO;

    @Autowired
    private CredentialRepository credentialRepository;

    @Value("${hibernate.repository.example}")
    private Boolean repositoryExample;

    @Transactional
    public void persist(Credential credential) {
        if (repositoryExample) {
            credentialRepository.save(credential);
        } else {
            credentialDAO.persist(credential);
        }
    }

    @Transactional
    public void update(Credential credential, String username, String password) {
        if (repositoryExample) {
            credential.setUsername(username);
            credential.setPassword(password);
            credentialRepository.save(credential);
        } else {
            credentialDAO.update(credential, username, password);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (repositoryExample) {
            credentialRepository.deleteById(id);
        } else {
            credentialDAO.delete(id);
        }
    }

    @Transactional
    public Optional<Credential> search(Long id) {
        Optional<Credential> optionalCredential;
        if (repositoryExample) {
            optionalCredential = credentialRepository.findById(id);
        } else {
            optionalCredential = Optional.of(credentialDAO.search(id));
        }
        return optionalCredential;
    }
}
