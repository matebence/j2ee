package com.ecneb.Hibernate.inheritances.mappedSuperClass;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="MAPPED_SUPER_CLASS_BUG")
public class Bug extends Ticket {

    @Getter
    @Setter
    @Column(name = "SEVERITY")
    private Integer severity;

    @Getter
    @Setter
    @Column(name = "ROOT_CAUSE")
    private String rootCause;
}
