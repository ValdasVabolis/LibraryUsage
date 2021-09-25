package com.foreign.tests;

import com.foreign.tests.implementation.EmailValidator;
import com.foreign.tests.implementation.PasswordValidator;
import com.foreign.tests.implementation.PhoneValidator;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static EmailValidator emailValidator;
    private static PasswordValidator passwordValidator;
    private static PhoneValidator phoneValidator;

    @BeforeAll
    static void prepareTest() throws ConfigurationException {
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();
        phoneValidator = new PhoneValidator();
    }

    @Test
    public void validatePasswordLength_False() {
        assertFalse(passwordValidator.validatePasswordLength("pass1"));
    }

    @Test
    public void validatePasswordLength_True() {
        assertTrue(passwordValidator.validatePasswordLength("pass22"));
    }

    @Test
    public void validatePasswordUppercase_True() {
        assertTrue(passwordValidator.validatePasswordUppercase("pasS22"));
    }

    @Test
    public void validatePasswordUppercase_False() {
        assertFalse(passwordValidator.validatePasswordUppercase("pass1"));
    }

    @Test
    public void validatePasswordSymbols_False() {
        assertFalse(passwordValidator.validatePasswordSymbols("pass"));
    }

    @Test
    public void validatePasswordSymbols_True() {
        assertTrue(passwordValidator.validatePasswordSymbols("pass@123"));
    }

    @Test
    public void validatePhoneNumbers_True() {
        assertTrue(phoneValidator.validatePhoneNumbers(101532348L));
    }

    @Test
    public void validatePhoneNumbers_False() {
        assertFalse(phoneValidator.validatePhoneNumbers(86261245L));
    }

    @Test
    public void buildPhoneByCountry_True() {
        assertEquals(phoneValidator.buildPhoneByCountry(866666666L), "+37066666666");
    }

    @Test
    public void buildPhoneByCountry_False() {
        assertNotEquals(phoneValidator.buildPhoneByCountry(866666666L), "+37066666666");
    }

    @Test
    public void updateCountryCodesList_True() {
        List<PhoneValidator.Country> countryList = List.of(new PhoneValidator.Country(8L, "+370"));
        assertIterableEquals(phoneValidator.updateCountryCodesList(8L, "+370"), countryList);
    }

    @Test
    public void validateEmailETA_True() {
        assertTrue(emailValidator.validateEmailETA("a@A.com"));
    }

    @Test
    public void validateEmailETA_False() {
        assertFalse(emailValidator.validateEmailETA("aA.com"));
    }

    @Test
    public void validateEmailDomainAndTLD_False() {
        assertFalse(emailValidator.validateEmailDomainAndTLD("aA@a.idk"));
    }

    @Test
    public void validateEmailDomainAndTLD_True() {
        assertTrue(emailValidator.validateEmailDomainAndTLD("aA@gmai.com"));
    }

    @Test
    public void validateEmailSymbols_False() {
        assertFalse(emailValidator.validateEmailSymbols("emailas@gmailas.com"));
    }

    @Test
    public void validateEmailSymbols_True() {
        assertTrue(emailValidator.validateEmailSymbols("emailas123@gmailas.com"));
    }

}