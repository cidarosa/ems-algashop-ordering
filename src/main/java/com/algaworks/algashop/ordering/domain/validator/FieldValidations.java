package com.algaworks.algashop.ordering.domain.validator;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidations {

    private FieldValidations() {
    }

    public static void requireNonBlank(String value){

        requireNonBlank(value, "");

    }
    public static void requireNonBlank(String value, String messageError){

        Objects.requireNonNull(value);

        if (value.isBlank()){
            throw new IllegalArgumentException("");
        }

    }

    public static void requiresValidEmail(String email){

        requiresValidEmail(email, null);
    }
       public static void requiresValidEmail(String email, String errorMessage){

           Objects.requireNonNull(email, errorMessage);

           if (email.isBlank()){
               throw new IllegalArgumentException(errorMessage);
           }

           // usa a biblioteca commons-validator
           if (!EmailValidator.getInstance().isValid(email)){
               throw new IllegalArgumentException(errorMessage);
           }

    }


}
