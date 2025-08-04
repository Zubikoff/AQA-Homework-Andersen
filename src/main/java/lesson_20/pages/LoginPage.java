package lesson_20.pages;

import lesson_20.utility.SetupDriver;
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

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        PageFactory.initElements(driver, this);
    }

    public LoginPage openLoginPage() {
        driver.get(pageURL);
        return this;
    }

    public LoginPage sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
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
        wait.until(ExpectedConditions.visibilityOf(signInButton)).click();
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

    public LoginPage verifyLogInSuccessful() {
        try {
            wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));
        } catch (TimeoutException e) {
            Assert.fail("Unable to verify the location on the expected page");
        }
        return this;
    }

    public void exitPage(){
        SetupDriver.closeDriver();
    }
}
