package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.entities.Market;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class MarketDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Market market) {
        entityManager.persist(market);
    }

    @Transactional
    public Market search(Long id) {
        return entityManager.find(Market.class, id);
    }
}
