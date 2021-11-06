package com.valdas.usermodule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserModuleTest {

    @BeforeEach
    void setUp() {
        UserModule.init();
    }

    @Test
    void initLoadsValidators() {
        UserModule.init();
        assertNotNull(UserModule.emailValidator);
        assertNotNull(UserModule.passwordValidator);
        assertNotNull(UserModule.phoneValidator);
    }

    @Test
    void testCheckEmail() {
        assertTrue(UserModule.checkEmail("name.surname12@email.com"));
    }

    @Test
    void testCheckPassword() {
        assertTrue(UserModule.checkPassword("Slapta@dd12"));
    }

    @Test
    void testCheckPhoneNumber() {
        assertTrue(UserModule.checkPhoneNumber("+37067455111"));
    }

    @Test
    void testUserCreatesNullWithBadData() {
        assertNull(UserModule.createUser("John", "Brown", "370777", "john@brown", "Mazeikiai", "Slapta12"));
    }

    @Test
    void testUserCreatesWithGoodData() {
        assertNotNull(UserModule.createUser("John", "Brown", "+37067455111", "john12@brown.com", "Mazeikiai", "Slapta@22lt"));
    }
}
