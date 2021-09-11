package com.validation.module.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneNumberValidatorTests {

    @Test
    public void validate_ValidPhoneNumber_True() {
        assertTrue(new PhoneNumberValidator().validate("+37061234567"));
    }

    @Test
    public void validate_HasOtherCharactersApartFromNumbers_False() {
        assertFalse(new PhoneNumberValidator().validate("+370612A4567"));
    }

    @Test
    public void validate_NumberBeginsWith8_True() {
        assertTrue(new PhoneNumberValidator().validate("861234567"));
    }

    @Test
    public void validate_NumberBeginsWithDifferentNumber_False() {
        assertFalse(new PhoneNumberValidator().validate("761234567"));
    }

    @Test
    public void validate_IsTooShort_False() {
        assertFalse(new PhoneNumberValidator().validate("+3706123456"));
    }

    @Test
    public void validate_IsTooLong_False() {
        assertFalse(new PhoneNumberValidator().validate("+370612345678"));
    }

    @Test
    public void validate_PrefixIsInvalid_False() {
        assertFalse(new PhoneNumberValidator().validate("+37161234567"));
    }

    @Test
    public void validate_AddLatvianPrefix_True() {
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();

        phoneNumberValidator.addNewCountryPrefix("+371", 8);

        assertTrue(phoneNumberValidator.validate("+37161234567"));
    }
}
