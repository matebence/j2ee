package com.ecneb.Hibernate.resources;

import com.ecneb.Hibernate.entities.Market;
import com.ecneb.Hibernate.services.MarketService;
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
@RequestMapping(value = "market-resource/api", produces = "application/json")
public class MarketResource {

    @Autowired
    private MarketService marketService;

    @PostMapping("/persist")
    @ResponseStatus(HttpStatus.CREATED)
    public void persist(@RequestBody Market market){
        marketService.persist(market);
    }

    @GetMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Market search(@PathVariable Long id) {
        return marketService.search(id).orElse(new Market());
    }
}