package util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Attribute converter for LocalDate.
 * Store a LocalDate attribute in a DATE column in database.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<java.time.LocalDate, java.sql.Date> {

    public java.sql.Date convertToDatabaseColumn(java.time.LocalDate attribute) {
        return attribute == null ? null : java.sql.Date.valueOf(attribute);
    }

    public java.time.LocalDate convertToEntityAttribute(java.sql.Date dbData) {
        return dbData == null ? null : dbData.toLocalDate();
    }
}