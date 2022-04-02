package com.ecneb.Hibernate.inheritances.tablePerClass;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Inheritance;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.InheritanceType;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TABLE_PER_CLASS_ITEM")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {

    @Id
    @Getter
    @Setter
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    @Getter
    @Setter
    @Column(name = "STATUS")
    private String status;
}