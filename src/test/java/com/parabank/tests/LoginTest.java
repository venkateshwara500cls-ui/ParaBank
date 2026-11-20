package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.parabank.pages.LoginPages;
import com.parabank.pages.RegisterPage;
import com.parabank.utils.RegistrationData;

public class LoginTest extends BaseTest {

    @Test(description = "Verify registered user can log out and log back in successfully")
    public void testUserLoginAndLogout() {
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPages loginPage = new LoginPages(driver);

        // 1. Create a fresh user first (ParaBank resets test users periodically)
        RegistrationData.generateRegistrationData();
        registerPage.openRegistrationPage();
        registerPage.fillRegistrationForm(
            RegistrationData.firstName, RegistrationData.lastName,
            RegistrationData.address, RegistrationData.city,
            RegistrationData.state, RegistrationData.zipCode,
            RegistrationData.phoneNumber, RegistrationData.ssn,
            RegistrationData.username, RegistrationData.password
        );
        registerPage.clickRegisterButton();
        Assert.assertTrue(registerPage.isSuccessMessageDisplayed(), "User registration failed");

        // 2. Log Out
        Assert.assertTrue(loginPage.isLogoutLinkDisplayed(), "Log Out link should be visible after registration");
        loginPage.logout();

        // 3. Validate Customer Login Section UI
        Assert.assertTrue(loginPage.isLoginHeadingDisplayed(), "Customer Login heading is not displayed");
        Assert.assertTrue(loginPage.isLoginPanelDisplayed(), "Login panel is not displayed");
        Assert.assertTrue(loginPage.isUsernameLabelDisplayed(), "Username label is not displayed");
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username input field is not displayed");
        Assert.assertTrue(loginPage.isPasswordLabelDisplayed(), "Password label is not displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password input field is not displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed");

        // 4. Perform Login & Verify Dashboard
        loginPage.login(RegistrationData.username, RegistrationData.password);
        Assert.assertTrue(loginPage.isAccountsOverviewDisplayed(), "Accounts Overview page should load after login");
        Assert.assertTrue(loginPage.isLogoutLinkDisplayed(), "Log Out link should be visible after login");

        // 5. Final Logout
        loginPage.logout();
        Assert.assertTrue(loginPage.isLoginPanelDisplayed(), "Login panel should reappear after logout");
    }
}