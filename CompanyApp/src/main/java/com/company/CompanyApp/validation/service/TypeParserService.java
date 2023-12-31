package com.company.CompanyApp.validation.service;

import com.company.CompanyApp.exception.TypeParseException;
import com.company.CompanyApp.exception.UnknownTypeException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * This service focuses on parsing String to a requested format.
 */
@Service
public class TypeParserService {

    /**
     * Parse the String to a requested format. If the parsing failed, then
     * a TypeParseException is thrown. If the data type requested is not presented, then
     * the UnknownTypeException is thrown.
     */
    public Object parse(Class<?> toType, String val) throws TypeParseException, UnknownTypeException {
        //Get purely the class name
        int nameStart = toType.getName().lastIndexOf('.') + 1;
        String className = toType.getName().substring(nameStart);

        try {
            switch (className) {
                case "int" -> {
                    return Integer.parseInt(val);
                }

                case "boolean" -> {
                    return Boolean.parseBoolean(val);
                }

                case "LocalDate" -> {
                    return LocalDate.parse(val);
                }

                case "double" -> {
                    return Double.parseDouble(val);
                }

                case "String" -> {
                    return val;
                }

                default -> throw new UnknownTypeException(className);
            }
        } catch (Exception ex) {
            throw new TypeParseException(val, className);
        }
    }
}
