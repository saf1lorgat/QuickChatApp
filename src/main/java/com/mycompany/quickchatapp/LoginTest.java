package com.mycompany.quickchatapp;
// unit test cases
import org.junit.Test;
import static org.junit.Assert.*;
// unit test cases
/**
 * Executes test cases specified in Task 1 rubric.
 */
public class LoginTest {

    @Test
    public void testCheckUserNameSuccess() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserNameFailure() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexitySuccess() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityFailure() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumberSuccess() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberFailure() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}