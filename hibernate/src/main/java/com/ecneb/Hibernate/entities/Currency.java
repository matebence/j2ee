package com.ecneb.Hibernate.entities;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.IdClass;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CurrencyId.class)
public class Currency {

    @Id
    @Getter
    @Setter
    @Column(name="NAME")
    private String name;

    @Id
    @Getter
    @Setter
    @Column(name="COUNTRY_NAME")
    private String countryName;

    @Getter
    @Setter
    @Column(name="SYMBOL")
    private String symbol;
}
