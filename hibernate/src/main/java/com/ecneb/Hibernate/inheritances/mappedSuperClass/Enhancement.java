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
@Table(name="MAPPED_SUPER_CLASS_ENHANCEMENT")
public class Enhancement extends Ticket {

    @Getter
    @Setter
    @Column(name = "DUPLICATE")
    private Boolean duplicate;

    @Getter
    @Setter
    @Column(name = "PRIORITY")
    private String priority;
}
