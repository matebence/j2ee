package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.entities.Budget;
import com.ecneb.Hibernate.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "budget-resource/api", produces = "application/json")
public class BudgetResource {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Budget budget) {
        budgetService.persist(budget);
    }

    @PostMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Budget search(@PathVariable Long id) {
        return budgetService.search(id).orElse(new Budget());
    }
}
