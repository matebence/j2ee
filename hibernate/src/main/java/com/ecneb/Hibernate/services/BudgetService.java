package com.ecneb.Hibernate.services;

import com.ecneb.Hibernate.entities.Budget;
import com.ecneb.Hibernate.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public void persist(Budget budget) {
        budgetRepository.save(budget);
    }

    @Transactional
    public Optional<Budget> search(Long id) {
        return budgetRepository.findById(id);
    }
}
