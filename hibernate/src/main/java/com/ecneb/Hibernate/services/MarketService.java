package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.daos.MarketDAO;
import com.ecneb.Hibernate.entities.Market;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MarketService {

    @Autowired
    private MarketDAO marketDAO;

    @Transactional
    public void persist(Market market) {
        marketDAO.persist(market);
    }

    @Transactional
    public Optional<Market> search(Long id) {
        return Optional.of(marketDAO.search(id));
    }
}
