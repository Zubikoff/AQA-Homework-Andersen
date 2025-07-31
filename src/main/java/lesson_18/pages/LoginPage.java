package lesson_18.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {
    private static WebDriver driver;
    private static WebDriverWait wait;

    private static final String pageURL = "https://qa-course-01.andersenlab.com/login";
    private static String email;

    @FindBy(xpath = "//input[@name=\"email\"]")
    private static WebElement emailField;

    @FindBy(xpath = "//input[@name=\"password\"][@placeholder=\"Enter password\"]")
    private static WebElement passwordField;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private static WebElement signInButton;

    protected Logger logger = LogManager.getLogger(this.getClass());

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        PageFactory.initElements(driver, this);
        logger.info("LoginPage initialized.");
    }

    public LoginPage openLoginPage() {
        driver.get(pageURL);
        logger.info("Opened page. URL: " + pageURL);
        return this;
    }

    public LoginPage sendKeys(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
        } catch (TimeoutException e) {
            String message = "Unable to confirm the visibility of the web element for text insertion. Element: "
                    + element.getTagName() + " " + element.getText();
            logger.error(message);
            Assert.fail(message);
        }
        return this;
    }

    public LoginPage setEmail(String text) {
        sendKeys(emailField, text);
        email = text;
        return this;
    }

    public LoginPage setPassword(String text) {
        sendKeys(passwordField, text);
        return this;
    }

    public LoginPage clickOnSignInButton() {
        try {
            wait.until(ExpectedConditions.visibilityOf(signInButton)).click();
        } catch (TimeoutException e) {
            String message = "Unable to click on \"Sign In\" button";
            logger.error(message);
            Assert.fail(message);
        }
        return this;
    }

    public LoginPage verifyExpectedTextOnPage(String text) {
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '"
                + text + "')]"))).isDisplayed(), "Unable to verify expected text on page");
        return this;
    }

    public LoginPage verifySignInButtonIsDisabled() {
        Assert.assertFalse(wait.until(ExpectedConditions.visibilityOf(signInButton)).isEnabled(), "Unable to verify Sign In button is disabled");
        return this;
    }

    /**
     * Verify that the email we logged in with is the same that we entered in 'Email' field
     */
    public LoginPage verifyLogInSuccessful() {
        try {
            wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));
            By locator = By.xpath("//div[text()=\"E-mail\"]/following-sibling::div[text()=\"" + email + "\"]");
            Assert.assertTrue(driver.findElement(locator).isDisplayed(), "Unable to verify the login happened with the expected user");
        } catch (TimeoutException e) {
            logger.error("Unable to verify the location on the expected page: https://qa-course-01.andersenlab.com/");
            Assert.fail("Unable to verify the location on the expected page");
        }
        return this;
    }
}
