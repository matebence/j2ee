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
@DiscriminatorColumn(name="STORY_TYPE")
public class Story extends Task {

    @Getter
    @Setter
    @Column(name = "LEVEL")
    private Integer level;
}
