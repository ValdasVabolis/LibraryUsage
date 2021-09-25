package com.foreign.tests.implementation;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Arrays;
import java.util.List;

public class EmailValidator {

    private final PropertiesConfiguration propertiesConfiguration;

    public EmailValidator() throws ConfigurationException {
        this.propertiesConfiguration = new PropertiesConfiguration();
        this.propertiesConfiguration.load("application.yml");
    }
    private final List<String> topLevelDomainList = Arrays.asList("com", "net", "lt");

    public boolean validateEmailETA(String emailToValidate) {
        return emailToValidate.contains("@");
    }

    public boolean validateEmailDomainAndTLD(String emailToValidate) {
        int indexOfAtSymbol = emailToValidate.lastIndexOf("@");
        int indexOfDot = emailToValidate.lastIndexOf(".");

        if (indexOfAtSymbol < 0 || indexOfDot < 0) return false;

        if (indexOfDot - indexOfAtSymbol <= 1) return false;

        return topLevelDomainList.contains(emailToValidate.substring(indexOfDot + 1));
    }

    public boolean validateEmailSymbols(String emailToValidate) {
        String specialSymbolList = propertiesConfiguration.getString("characters");

        for (int i = 0; i < specialSymbolList.length(); i++) {
            if (emailToValidate.contains(specialSymbolList.substring(i, i+1))) return true;
        }
        return false;
    }
}
