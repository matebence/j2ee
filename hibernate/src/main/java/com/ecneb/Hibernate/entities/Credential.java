package com.ecneb.Hibernate.entities;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CREDENTIAL")
public class Credential {

    @Id
    @Getter
    @Setter
    @Column(name="CREDENTIAL_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long credentialId;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public User user;

    @Getter
    @Setter
    @Column(name="USERNAME")
    private String username;

    @Getter
    @Setter
    @Column(name="PASSWORD")
    private String password;
}
