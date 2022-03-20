package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.daos.AccountDAO;
import com.ecneb.Hibernate.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Transactional
    public void persist(Account account) {
        accountDAO.persist(account);
    }

    @Transactional
    public void update(Account account, String name) {
        accountDAO.update(account, name);
    }

    @Transactional
    public void delete(Long id) {
        accountDAO.delete(id);
    }

    @Transactional
    public Optional<Account> search(Long id) {
        return Optional.of(accountDAO.search(id));
    }
}
