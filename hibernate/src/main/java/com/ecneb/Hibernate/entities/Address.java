package com.ecneb.Hibernate.entities;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Column;

@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Getter
    @Setter
    @Column(name="USER_ADDRESS_LINE_1")
    private String addressLine1;

    @Getter
    @Setter
    @Column(name="ADDRESS_LINE_2")
    private String addressLine2;

    @Getter
    @Setter
    @Column(name="CITY")
    private String city;

    @Getter
    @Setter
    @Column(name="STATE")
    private String state;

    @Getter
    @Setter
    @Column(name="ZIP_CODE")
    private String zipCode;
}