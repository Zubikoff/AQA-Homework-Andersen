package lesson_18.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private static WebDriver driver;
    private static WebDriverWait wait;

    private static final String pageURL = "https://qa-course-01.andersenlab.com/login";
    private static String email;

    @FindBy(xpath = "\"//input[@name=\\\"email\\\"]\"")
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

    public LoginPage openLoginPage(){
        driver.get(pageURL);
        return this;
    }

    public LoginPage sendKeys(WebElement element, String text){
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
        return this;
    }

    public LoginPage setEmail(String text){
        sendKeys(emailField, text);
        email = text;
        return this;
    }

    public LoginPage setPassword(String text){
        sendKeys(passwordField, text);
        return this;
    }

    public LoginPage clickOnSignInButton(){
        wait.until(ExpectedConditions.visibilityOf(signInButton)).click();
        return this;
    }

    public boolean verifyExpectedTextOnPage(String text){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '"
                    + text + "')]")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean verifySignInButtonIsDisabled(){
        try {
            return wait.until(ExpectedConditions.visibilityOf(signInButton)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Verify that the email we logged in with is the same that we entered in 'Email' field
     */
    public boolean verifyLogInSuccessful(){
        try {
            wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));
            By locator = By.xpath("//div[text()=\"E-mail\"]/following-sibling::div[text()=\"" + email + "\"]");
            return driver.findElement(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
