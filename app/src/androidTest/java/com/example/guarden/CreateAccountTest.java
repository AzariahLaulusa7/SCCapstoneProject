package com.example.guarden;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountTest {

    private CreateAccount createAccountActivity;

    @Before
    public void setUp() {
        // Instantiate the object under test
        createAccountActivity = new CreateAccount();
    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue("Email should be valid", createAccountActivity.isValidEmail("name@example.com"));
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse("Email should be invalid", createAccountActivity.isValidEmail("name@example"));
    }

    @Test
    public void passwordValidator_CorrectPassword_ReturnsTrue() {
        assertTrue("Password should be valid", createAccountActivity.isValidPassword("Password1"));
    }

    @Test
    public void passwordValidator_InvalidPasswordShort_ReturnsFalse() {
        assertFalse("Password should be invalid", createAccountActivity.isValidPassword("Pass1"));
    }

    @Test
    public void passwordValidator_InvalidPasswordNoDigit_ReturnsFalse() {
        assertFalse("Password should be invalid", createAccountActivity.isValidPassword("Password"));
    }

    @Test
    public void passwordValidator_InvalidPasswordNoUpperCase_ReturnsFalse() {
        assertFalse("Password should be invalid", createAccountActivity.isValidPassword("password1"));
    }

}
