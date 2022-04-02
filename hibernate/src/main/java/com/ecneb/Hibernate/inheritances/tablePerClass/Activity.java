package com.ecneb.Hibernate.inheritances.tablePerClass;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TABLE_PER_CLASS_ACTIVITY")
public class Activity extends Item {

    @Getter
    @Setter
    @Column(name = "SEVERITY")
    private Integer severity;

    @Getter
    @Setter
    @Column(name = "ROOT_CAUSE")
    private String rootCause;
}
