package com.ecneb.Hibernate.dtos;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonName implements Serializable {

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;
}