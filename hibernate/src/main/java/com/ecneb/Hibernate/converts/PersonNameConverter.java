package com.ecneb.Hibernate.converts;

import com.ecneb.Hibernate.dtos.PersonName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PersonNameConverter implements AttributeConverter<PersonName, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(PersonName personName) {
        if (personName == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (personName.getLastName() != null && !personName.getLastName()
                .isEmpty()) {
            sb.append(personName.getLastName());
            sb.append(SEPARATOR);
        }

        if (personName.getFirstName() != null
                && !personName.getFirstName().isEmpty()) {
            sb.append(personName.getFirstName());
        }

        return sb.toString();
    }

    @Override
    public PersonName convertToEntityAttribute(String dbPersonName) {
        if (dbPersonName == null || dbPersonName.isEmpty()) {
            return null;
        }

        String[] pieces = dbPersonName.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        PersonName personName = new PersonName();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbPersonName.contains(SEPARATOR)) {
            personName.setLastName(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                personName.setFirstName(pieces[1]);
            }
        } else {
            personName.setFirstName(firstPiece);
        }

        return personName;
    }
}