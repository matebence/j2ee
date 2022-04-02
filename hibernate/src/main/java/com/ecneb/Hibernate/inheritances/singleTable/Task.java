package com.ecneb.Hibernate.inheritances.singleTable;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.GenerationType;
import javax.persistence.InheritanceType;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="SINGLE_TABLE_TASK")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Task {

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
