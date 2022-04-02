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
@Table(name="JOINED_DEFECT")
public class Defect extends Job {

    @Getter
    @Setter
    @Column(name = "SEVERITY")
    private Integer severity;

    @Getter
    @Setter
    @Column(name = "ROOT_CAUSE")
    private String rootCause;
}
