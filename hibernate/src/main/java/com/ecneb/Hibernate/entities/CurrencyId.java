package com.ecneb.Hibernate.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class CurrencyId implements Serializable {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String countryName;
}