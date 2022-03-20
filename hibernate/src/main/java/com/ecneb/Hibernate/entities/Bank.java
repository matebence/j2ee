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
import javax.persistence.Embedded;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Temporal;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.TemporalType;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BANK")
public class Bank {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="BANK_ID")
    private Long bankId;

    @Getter
    @Setter
    @Column(name="NAME")
    private String name;

    @Getter
    @Setter
    @Column(name="IS_INTERNATIONAL")
    private boolean international;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Getter
    @Setter
    @Column(name="LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Getter
    @Setter
    @Column(name="CREATED_BY")
    private String createdBy;

    @Getter
    @Setter
    @Embedded
    @Column(name="ADDRESS")
    @AttributeOverrides({@AttributeOverride(name="addressLine1", column=@Column(name="USER_ADDRESS_LINE_1"))})
    private Address address = new Address();

    @Getter
    @Setter
    @ElementCollection
    @Column(name="ADDRESS_2")
    @CollectionTable(name="USER_ADDRESS", joinColumns=@JoinColumn(name="USER_ID"))
    @AttributeOverrides({@AttributeOverride(name="addressLine1", column=@Column(name="USER_ADDRESS_LINE_1"))})
    private List<Address> address2 = new ArrayList<>();

    @Getter
    @Setter
    @ElementCollection
    @Column(name="CONTACTS")
    @CollectionTable(name="BANK_CONTACT", joinColumns=@JoinColumn(name="BANK_ID"))
    private Set<String> contacts = new HashSet<>();

    @Getter
    @Setter
    @ElementCollection
    @Column(name="CONTACTS_2")
    @CollectionTable(name="BANK_CONTACT2", joinColumns=@JoinColumn(name="BANK_ID"))
    @MapKeyColumn(name="POSITION_TYPE")
    private Map<String, String> contacts2 = new HashMap<>();
}
