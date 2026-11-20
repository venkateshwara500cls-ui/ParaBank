package com.parabank.pages;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(RegisterPage.class);

    // Locators
    private final By registerLink = By.linkText("Register");
    private final By logoImg = By.xpath("//img[@class='logo']");
    private final By pageHeading = By.xpath("//h1[text()='Signing up is easy!']");

    private final By firstNameInput = By.name("customer.firstName");
    private final By lastNameInput = By.name("customer.lastName");
    private final By addressInput = By.name("customer.address.street");
    private final By cityInput = By.name("customer.address.city");
    private final By stateInput = By.name("customer.address.state");
    private final By zipCodeInput = By.name("customer.address.zipCode");
    private final By phoneInput = By.name("customer.phoneNumber");
    private final By ssnInput = By.name("customer.ssn");
    private final By usernameInput = By.name("customer.username");
    private final By passwordInput = By.name("customer.password");
    private final By confirmPasswordInput = By.name("repeatedPassword");
    private final By registerButton = By.xpath("//input[@type='submit' and @value='Register']");

    private final By successMessage = By.xpath("//p[contains(text(),'Your account was created successfully')]");
    private final By welcomeHeading = By.xpath("//div[@id='rightPanel']/h1");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openRegistrationPage() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        log.info("Opened Registration page");
    }

    public boolean isLogoDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logoImg)).isDisplayed();
    }

    public String getLogoAttribute(String attributeName) {
        return driver.findElement(logoImg).getAttribute(attributeName);
    }

    public String getPageHeading() {
        return driver.findElement(pageHeading).getText();
    }

    public boolean isLabelDisplayed(String labelText) {
        By dynamicLabel = By.xpath("//td/b[text()='" + labelText + "']");
        return driver.findElement(dynamicLabel).isDisplayed();
    }

    public boolean isRegisterButtonDisplayed() {
        return driver.findElement(registerButton).isDisplayed();
    }

    public void fillRegistrationForm(String fName, String lName, String address, String city,
                                     String state, String zip, String phone, String ssn,
                                     String username, String password) {
        driver.findElement(firstNameInput).sendKeys(fName);
        driver.findElement(lastNameInput).sendKeys(lName);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(zipCodeInput).sendKeys(zip);
        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(ssnInput).sendKeys(ssn);
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(confirmPasswordInput).sendKeys(password);
        log.info("Filled registration form for user: {}", username);
    }

    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        log.info("Clicked Register button");
    }

    public boolean isSuccessMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public String getWelcomeHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeHeading)).getText();
    }
}