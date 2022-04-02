package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.entities.Currency;
import com.ecneb.Hibernate.entities.CurrencyId;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CurrencyDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Currency currency) {
        entityManager.persist(currency);
    }

    @Transactional
    public Currency search(String name, String countryName) {
        return entityManager.find(Currency.class, new CurrencyId(name, countryName));
    }
}
