package com.ecneb.Hibernate.inheritances.singleTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name="INCIDENT_TYPE")
public class Incident extends Task {

    @Getter
    @Setter
    @Column(name = "TEAM")
    private String team;
}
