package com.ecneb.Hibernate.inheritances.mappedSuperClass;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.GenerationType;

@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

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