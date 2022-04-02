package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.daos.CurrencyDAO;
import com.ecneb.Hibernate.entities.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyDAO currencyDAO;

    @Transactional
    public void persist(Currency currency) {
        currencyDAO.persist(currency);
    }

    @Transactional
    public Optional<Currency> search(String name, String countryName) {
        return Optional.of(currencyDAO.search(name, countryName));
    }
}
