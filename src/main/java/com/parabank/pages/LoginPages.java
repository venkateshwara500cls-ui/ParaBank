package com.parabank.pages;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPages {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(LoginPages.class);

    // 1. Locators
    private final By loginHeading = By.xpath("//h2[contains(text(),'Customer Login')]");
    private final By loginPanel = By.id("loginPanel");
    private final By usernameLabel = By.xpath("//div[@id='loginPanel']//b[text()='Username']");
    private final By usernameInput = By.name("username");
    private final By passwordLabel = By.xpath("//div[@id='loginPanel']//b[text()='Password']");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.xpath("//input[@value='Log In']");

    // Post-Login / Logout Locators
    private final By logoutLink = By.xpath("//a[@href='logout.htm']");
    private final By accountsOverviewHeading = By.xpath("//h1[contains(text(),'Accounts Overview')]");

    // 2. Constructor
    public LoginPages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // 3. Visibility Check Methods (used by TestNG Assertions)
    public boolean isLoginHeadingDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginHeading)).isDisplayed();
    }

    public boolean isLoginPanelDisplayed() {
        return driver.findElement(loginPanel).isDisplayed();
    }

    public boolean isUsernameLabelDisplayed() {
        return driver.findElement(usernameLabel).isDisplayed();
    }

    public boolean isUsernameFieldDisplayed() {
        return driver.findElement(usernameInput).isDisplayed();
    }

    public boolean isPasswordLabelDisplayed() {
        return driver.findElement(passwordLabel).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return driver.findElement(passwordInput).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    public boolean isLogoutLinkDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).isDisplayed();
    }

    public boolean isAccountsOverviewDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountsOverviewHeading)).isDisplayed();
    }

    // 4. Page Actions
    public void login(String username, String password) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userField.clear();
        userField.sendKeys(username);
        log.info("Entered username: {}", username);

        WebElement passField = driver.findElement(passwordInput);
        passField.clear();
        passField.sendKeys(password);
        log.info("Entered password");

        driver.findElement(loginButton).click();
        log.info("Clicked Log In button");
    }

    public void logout() {
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutBtn.click();
        log.info("Clicked Log Out link");

        // Wait until redirected back to login panel
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPanel));
        log.info("Logout successful - login panel is displayed again");
    }
}