package lesson_18.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class RegistrationPage {
    private static WebDriver driver;
    private static WebDriverWait wait;

    private static final String pageURL = "https://qa-course-01.andersenlab.com/registration";
    private static String email;
    private static String password;

    @FindBy(xpath = "//input[@name=\"firstName\"]")
    private static WebElement firstNameField;

    @FindBy(xpath = "//input[@name=\"lastName\"]")
    private static WebElement lastNameField;

    @FindBy(xpath = "//input[@name=\"dateOfBirth\"]")
    private static WebElement dateOfBirthField;

    @FindBy(xpath = "//input[@name=\"email\"]")
    private static WebElement emailField;

    @FindBy(xpath = "//input[@name=\"password\"]")
    private static WebElement passwordField;

    @FindBy(xpath = "//input[@name=\"passwordConfirmation\"]")
    private static WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private static WebElement submitButton;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        PageFactory.initElements(driver, this);
    }

    public RegistrationPage openRegistrationPage() {
        driver.get(pageURL);
        return this;
    }

    public RegistrationPage sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
        return this;
    }

    public RegistrationPage setFirstName(String text) {
        sendKeys(firstNameField, text);
        return this;
    }

    public RegistrationPage setLastName(String text) {
        sendKeys(lastNameField, text);
        return this;
    }

    public RegistrationPage setDataOfBirth(String text) {
        sendKeys(dateOfBirthField, text);
        dateOfBirthField.sendKeys(Keys.ESCAPE); // to close the datepicker
        return this;
    }

    public RegistrationPage setEmail(String text) {
        sendKeys(emailField, text);
        email = text;
        return this;
    }

    public RegistrationPage setPassword(String text) {
        sendKeys(passwordField, text);
        password = text;
        return this;
    }

    public RegistrationPage setConfirmPassword(String text) {
        sendKeys(confirmPasswordField, text);
        return this;
    }

    public RegistrationPage clickOnSubmitButton() {
        wait.until(ExpectedConditions.visibilityOf(submitButton)).click();
        return this;
    }

    public RegistrationPage verifyExpectedTextOnPage(String text) {
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '"
                + text + "')]"))).isDisplayed(), "Unable to verify expected text on page");
        return this;
    }

    public RegistrationPage verifySubmitButtonIsDisabled() {
        Assert.assertFalse(wait.until(ExpectedConditions.visibilityOf(submitButton)).isEnabled(), "Unable to verify Submit button is disabled");
        return this;
    }

    /**
     * Verify that we can log in using the email and password from registration process
     */
    public RegistrationPage verifyRegistrationSuccessful() {
        LoginPage page = new LoginPage(driver);
        page.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyLogInSuccessful();
        return this;
    }
}
