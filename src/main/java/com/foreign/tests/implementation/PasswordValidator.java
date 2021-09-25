package com.foreign.tests.implementation;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PasswordValidator {

    private final PropertiesConfiguration propertiesConfiguration;

    public PasswordValidator() throws ConfigurationException {
        this.propertiesConfiguration = new PropertiesConfiguration();
        this.propertiesConfiguration.load("application.yml");
    }

    public boolean validatePasswordLength(String passwordToValidate) {
        return passwordToValidate.length() >= propertiesConfiguration.getInt("length");
    }

    public boolean validatePasswordUppercase(String passwordToValidate) {
        for (int i = 0; i < passwordToValidate.length(); i++) {
            if (Character.isUpperCase(passwordToValidate.charAt(i))) return true;
        }
        return false;
    }

    public boolean validatePasswordSymbols(String passwordToValidate) {
        String specialSymbolList = propertiesConfiguration.getString("symbols");

        for (int i = 0; i < specialSymbolList.length(); i++) {
            if (passwordToValidate.contains(specialSymbolList.substring(i, i+1))) return true;
        }
        return false;
    }
}
