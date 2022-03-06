package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.entities.Credential;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CredentialDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Credential credential) {
        entityManager.persist(credential);
    }

    @Transactional
    public void update(Credential credential, String username, String password) {
        credential.setUsername(username);
        credential.setPassword(password);
        entityManager.flush();
    }

    @Transactional
    public void delete(Long id) {
        entityManager.remove(search(id));
    }

    @Transactional
    public Credential search(Long id) {
        return entityManager.find(Credential.class, id);
    }
}
