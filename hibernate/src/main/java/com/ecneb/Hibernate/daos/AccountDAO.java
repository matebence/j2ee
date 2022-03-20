package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.entities.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Account account) {
        entityManager.persist(account);
    }

    @Transactional
    public void update(Account account, String name) {
        account.setName(name);
        entityManager.flush();
    }

    @Transactional
    public void delete(Long id) {
        entityManager.remove(search(id));
    }

    @Transactional
    public Account search(Long id) {
        return entityManager.find(Account.class, id);
    }
}
