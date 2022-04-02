package com.ecneb.Hibernate.entities;

import com.ecneb.Hibernate.converts.PersonNameConverter;
import com.ecneb.Hibernate.dtos.PersonName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.Convert;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Id
    @Getter
    @Setter
    @Column(name = "USER_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Account> accounts = new HashSet<>();

    @Getter
    @Setter
    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private Credential credential;

    @Getter
    @Setter
    @Column(name = "PERSON_NAME")
    @Convert(converter = PersonNameConverter.class)
    private PersonName personName;

    @Getter
    @Setter
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Getter
    @Setter
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
}
