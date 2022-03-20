package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.entities.Bank;
import com.ecneb.Hibernate.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Transactional
    public void persist(Bank bank) {
        bankRepository.save(bank);
    }

    @Transactional
    public Optional<Bank> search(Long id) {
        return bankRepository.findById(id);
    }
}
