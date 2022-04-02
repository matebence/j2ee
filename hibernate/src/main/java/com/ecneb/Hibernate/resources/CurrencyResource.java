package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.entities.Currency;
import com.ecneb.Hibernate.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "currency-resource/api", produces = "application/json")
public class CurrencyResource {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Currency currency){
        currencyService.persist(currency);
    }

    @GetMapping("/search/{name}/{countryName}")
    @ResponseStatus(HttpStatus.OK)
    public Currency search(@PathVariable String name, @PathVariable String countryName) {
        return currencyService.search(name, countryName).orElse(new Currency());
    }
}