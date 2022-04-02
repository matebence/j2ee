package com.ecneb.Hibernate.inheritances.joinedTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="JOINED_FEATURE")
public class Feature extends Job {

    @Getter
    @Setter
    @Column(name = "LEVEL")
    private Integer level;
}
