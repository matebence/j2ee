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
@Table(name="TABLE_PER_CLASS_TEST")
public class Test extends Item {

    @Getter
    @Setter
    @Column(name = "DUPLICATE")
    private Boolean duplicate;

    @Getter
    @Setter
    @Column(name = "PRIORITY")
    private String priority;
}
