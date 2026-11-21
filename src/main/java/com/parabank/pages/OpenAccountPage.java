package com.parabank.pages;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenAccountPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(OpenAccountPage.class);

    // 1. Form Locators
    private final By openNewAccountLink = By.xpath("//a[@href='openaccount.htm']");
    private final By pageHeading = By.xpath("//h1[contains(text(),'Open New Account')]");
    private final By accountTypeDropdown = By.id("type");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By fromAccountOptions = By.cssSelector("#fromAccountId option");
    private final By openNewAccountButton = By.xpath("//input[@type='button' and @value='Open New Account']");

    // 2. Confirmation Locators (scoped to #openAccountResult)
    private final By openAccountResultDiv = By.id("openAccountResult");
    private final By accountOpenedHeading = By.xpath("//div[@id='openAccountResult']//h1[contains(text(),'Account Opened!')]");
    private final By confirmationMessage = By.xpath("//div[@id='openAccountResult']//p[contains(text(),'Congratulations, your account is now open.')]");
    private final By newAccountIdLink = By.id("newAccountId");

    // 3. Constructor
    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // 4. Page Actions
    public void goToOpenNewAccountPage() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        log.info("Navigated to Open New Account page");
    }

    public void selectAccountType(String accountType) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(accountTypeDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(accountType);
        log.info("Selected account type: {}", accountType);
    }

    public void selectFromAccount() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(fromAccountDropdown));
        // Wait for AJAX to populate at least 1 <option> inside #fromAccountId
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(fromAccountOptions, 0));
        Select select = new Select(dropdown);
        select.selectByIndex(0);
        log.info("Selected the first account from 'From Account' dropdown");
    }

    public void clickOpenNewAccountButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(openNewAccountButton));
        button.click();
        log.info("Clicked Open New Account button");

        // Retry click once if AngularJS hadn't finished binding the click handler yet
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(openAccountResultDiv));
        } catch (Exception e) {
            log.warn("Result panel did not appear after first click, retrying click...");
            wait.until(ExpectedConditions.elementToBeClickable(openNewAccountButton)).click();
        }
    }

    // 5. Verification & Data Extraction Methods
    public boolean isAccountOpenedHeadingDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountOpenedHeading)).isDisplayed();
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage)).getText();
    }

    public String getNewAccountId() {
        String accountId = wait.until(ExpectedConditions.visibilityOfElementLocated(newAccountIdLink)).getText();
        log.info("New Savings Account ID created: {}", accountId);
        System.out.println("✅ New Savings Account ID created: " + accountId);
        return accountId;
    }
}