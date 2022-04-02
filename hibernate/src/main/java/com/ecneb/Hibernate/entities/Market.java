package com.ecneb.Hibernate.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumns;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MARKET")
public class Market {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MARKET_ID")
    private Long marketId;

    @Getter
    @Setter
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="CURRENCY_NAME", referencedColumnName="NAME"),
            @JoinColumn(name="COUNTRY_NAME", referencedColumnName="COUNTRY_NAME")
    })
    //CURRENCY_NAME and COUNTRY_NAME will be places inside here (MARKET TABLE)
    private Currency currency;

    @Getter
    @Setter
    @Column(name = "MARKET_NAME")
    private String marketName;
}
