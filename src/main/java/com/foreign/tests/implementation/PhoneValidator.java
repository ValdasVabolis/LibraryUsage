package com.foreign.tests.implementation;

import java.util.ArrayList;
import java.util.List;

public class PhoneValidator {

    List<Country> listOfValidPhoneNumberPrefixes = new ArrayList<>();

    public boolean validatePhoneNumbers(Long phoneNumberToValidate) {
        return phoneNumberToValidate.toString().length() >= 9;
    }

    public String buildPhoneByCountry(Long phoneToValidate) {
        if (phoneToValidate.toString().startsWith("8")) {
            return phoneToValidate.toString().replace("8", "+370");
        } else return phoneToValidate.toString();
    }

    public List<Country> updateCountryCodesList(Long length, String prefix) {
        listOfValidPhoneNumberPrefixes.add(new Country(length, prefix));

        return List.of(new Country(length, prefix));
    }

    public static class Country {
        String prefix;
        Long length;

        public Country(Long length, String prefix) {
            this.length = length;
            this.prefix = prefix;
        }
    }
}
