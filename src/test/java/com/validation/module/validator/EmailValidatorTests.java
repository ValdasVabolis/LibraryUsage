package com.validation.module.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailValidatorTests {

    @Test
    public void validate_ValidEmail_True() {
        assertTrue(new EmailValidator().validate("name.surname@email.com"));
    }

    @Test
    public void validate_DoesNotHaveAtSign_False() {
        assertFalse(new EmailValidator().validate("name.surnamemail.com"));
    }

    @Test
    public void validate_DoesNotHaveRecipientName_False() {
        assertFalse(new EmailValidator().validate("@email.com"));
    }

    @Test
    public void validate_DoesNotHaveDomainName_False() {
        assertFalse(new EmailValidator().validate("name.surname@.com"));
    }

    @Test
    public void validate_DoesNotHaveTopLevelDomain_False() {
        assertFalse(new EmailValidator().validate("name.surname@email"));
    }

    @Test
    public void validate_HasSpecialCharacterInFront_False() {
        assertFalse(new EmailValidator().validate("!name.surname@email.com"));
    }

    @Test
    public void validate_HasSpecialCharacterInBack_False() {
        assertFalse(new EmailValidator().validate("name.surname@email.com!"));
    }

    @Test
    public void validate_HasConsecutiveSpecialCharacters_False() {
        assertFalse(new EmailValidator().validate("name..surname@email"));
    }
}
