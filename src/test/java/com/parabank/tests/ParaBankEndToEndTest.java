package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.parabank.pages.LoginPages;
import com.parabank.pages.RegisterPage;
import com.parabank.utils.RegistrationData;

public class ParaBankEndToEndTest extends BaseTest {

    @Test(priority = 1, description = "Step 1: Register a new user")
    public void step1_registerUser() {
        RegisterPage registerPage = new RegisterPage(driver);

        // Validate Logo
        Assert.assertTrue(registerPage.isLogoDisplayed(), "ParaBank logo should be visible");
        Assert.assertEquals(registerPage.getLogoAttribute("title"), "ParaBank");
        Assert.assertEquals(registerPage.getLogoAttribute("alt"), "ParaBank");
        Assert.assertTrue(registerPage.getLogoAttribute("src").contains("logo.gif"));

        // Open Register Page & Validate Labels
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

        // Generate Credentials & Submit Form
        RegistrationData.generateRegistrationData();
        registerPage.fillRegistrationForm(
            RegistrationData.firstName, RegistrationData.lastName,
            RegistrationData.address, RegistrationData.city,
            RegistrationData.state, RegistrationData.zipCode,
            RegistrationData.phoneNumber, RegistrationData.ssn,
            RegistrationData.username, RegistrationData.password
        );
        registerPage.clickRegisterButton();

        // Verify Registration Success
        Assert.assertTrue(registerPage.isSuccessMessageDisplayed(), "Success message not displayed");
        Assert.assertEquals(registerPage.getWelcomeHeading(), "Welcome " + RegistrationData.username);
    }

    @Test(priority = 2, dependsOnMethods = "step1_registerUser", description = "Step 2: Logout newly registered user")
    public void step2_logoutUser() {
        LoginPages loginPage = new LoginPages(driver);

        Assert.assertTrue(loginPage.isLogoutLinkDisplayed(), "Log Out link should be visible");
        loginPage.logout();
        Assert.assertTrue(loginPage.isLoginPanelDisplayed(), "Login panel should be visible after logout");
    }

    @Test(priority = 3, dependsOnMethods = "step2_logoutUser", description = "Step 3: Login using registered credentials")
    public void step3_loginWithRegisteredUser() {
        LoginPages loginPage = new LoginPages(driver);

        // Validate Login Section UI
        Assert.assertTrue(loginPage.isLoginHeadingDisplayed(), "Customer Login heading missing");
        Assert.assertTrue(loginPage.isUsernameLabelDisplayed(), "Username label missing");
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username input missing");
        Assert.assertTrue(loginPage.isPasswordLabelDisplayed(), "Password label missing");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password input missing");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button missing");

        // Login with the exact username & password created in Step 1
        loginPage.login(RegistrationData.username, RegistrationData.password);

        // Verify Login Succeeded
        Assert.assertTrue(loginPage.isAccountsOverviewDisplayed(), "Accounts Overview page did not load");
        Assert.assertTrue(loginPage.isLogoutLinkDisplayed(), "Log Out link should be visible after login");

        // Final Logout
        loginPage.logout();
    }
}