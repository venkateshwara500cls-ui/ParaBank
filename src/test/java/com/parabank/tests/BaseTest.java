package com.parabank.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.parabank.utils.ScreenRecorder;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    private final String url = "https://parabank.parasoft.com";

    @BeforeClass
    public void setUp() throws Exception {
        log.info("===== TEST SETUP STARTED =====");
        ScreenRecorder.startRecording("ParaBank_E2E_Test");

        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autofillForms", false);
        options.addPreference("signon.generation.enabled", false);
        options.addPreference("signon.management.page.breach-alerts.enabled", false);
        options.addPreference("services.sync.prefs.sync.signon.rememberSignons", false);
        options.addPreference("extensions.formautofill.addresses.enabled", false);
        options.addPreference("extensions.formautofill.creditCards.enabled", false);

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        log.info("Navigated to: " + url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        try {
            if (driver != null) {
                driver.quit();
                log.info("Browser closed");
            }
        } finally {
            ScreenRecorder.stopRecording();
            log.info("===== TEST COMPLETED =====");
        }
    }
}