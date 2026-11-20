package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.parabank.pages.RegisterPage;
import com.parabank.utils.RegistrationData;

public class RegistrationTest extends BaseTest {

    @Test
    public void testUserRegistration() {
        RegisterPage registerPage = new RegisterPage(driver);

        // 1. Validate Logo
        Assert.assertTrue(registerPage.isLogoDisplayed(), "ParaBank logo should be visible");
        Assert.assertEquals(registerPage.getLogoAttribute("title"), "ParaBank");
        Assert.assertEquals(registerPage.getLogoAttribute("alt"), "ParaBank");
        Assert.assertTrue(registerPage.getLogoAttribute("src").contains("logo.gif"));

        // 2. Open Register Page & Validate Labels
        registerPage.openRegistrationPage();
        Assert.assertEquals(registerPage.getPageHeading(), "Signing up is easy!");

        String[] expectedLabels = {
            "First Name:", "Last Name:", "Address:", "City:", "State:",
            "Zip Code:", "Phone #:", "SSN:", "Username:", "Password:", "Confirm:"
        };
        for (String label : expectedLabels) {
            Assert.assertTrue(registerPage.isLabelDisplayed(label), label + " label is missing");
        }
        Assert.assertTrue(registerPage.isRegisterButtonDisplayed());

        // 3. Fill & Submit Form
        RegistrationData.generateRegistrationData();
        registerPage.fillRegistrationForm(
            RegistrationData.firstName, RegistrationData.lastName,
            RegistrationData.address, RegistrationData.city,
            RegistrationData.state, RegistrationData.zipCode,
            RegistrationData.phoneNumber, RegistrationData.ssn,
            RegistrationData.username, RegistrationData.password
        );
        registerPage.clickRegisterButton();

        // 4. Verify Success
        Assert.assertTrue(registerPage.isSuccessMessageDisplayed(), "Success message not displayed");
        Assert.assertEquals(registerPage.getWelcomeHeading(), "Welcome " + RegistrationData.username);
    }
}