package lesson_18;

import lesson_18.pages.LoginPage;
import lesson_18.pages.RegistrationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

public class PageTests {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static RegistrationPage registrationPage;

    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @AfterClass
    public void close(){
        driver.quit();
    }

    @Test
    public void logInPositive(){
        String email = "aqastud@mail.com";
        String password = "Qwer123$";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyLogInSuccessful();
    }

    @Test
    public void logInWithInvalidPassword(){
        String email = "aqastud@mail.com";
        String password = "Qwer1234";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Email or password is not valid")
                .verifySignInButtonIsDisabled();
    }

    @Test
    public void logInWithInvalidEmail(){
        String email = Utilities.getInvalidEmail("aqastud@mail.com");
        String password = "Qwer123$";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Email or password is not valid")
                .verifySignInButtonIsDisabled();
    }

    @Test
    public void logInWithEmptyFields(){
        loginPage.openLoginPage()
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Required")
                .verifySignInButtonIsDisabled();
    }

    @Test
    public void logInWithTooLongEmail(){
        String email = "aqa@1234567890123456789012345678901234567890123456789012345678901234.com";
        String password = "Qwer123$";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Invalid email address")
                .verifySignInButtonIsDisabled();
    }

    @Test
    public void registerPositive(){
        String email = Utilities.getValidEmail();
        String password = "Qwer123$";
        registrationPage.openRegistrationPage()
                .setFirstName("Aqa")
                .setLastName("Stud")
                .setDataOfBirth("06/03/2010")
                .setEmail(email)
                .setPassword(password)
                .setConfirmPassword(password)
                .clickOnSubmitButton()
                .verifyRegistrationSuccessful();
    }

    @Test
    public void registerAlreadyRegistered(){
        //this should fail - the error message is returned in response body, but never displayed
        String email = "aqastud@mail.com";
        String password = "Qwer123$";
        registrationPage.openRegistrationPage()
                .setFirstName("Aqa")
                .setLastName("Stud")
                .setDataOfBirth("06/03/2010")
                .setEmail(email)
                .setPassword(password)
                .setConfirmPassword(password)
                .clickOnSubmitButton()
                .verifyExpectedTextOnPage("User with email " + email + " already exist.");
    }

    @Test
    public void registerWithFirstNameTooLong(){
        //this should fail - the error message is returned in response body, but never displayed
        String email = "aqastud@mail.com";
        String password = "Qwer123$";
        String firstName = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        registrationPage.openRegistrationPage()
                .setFirstName(firstName)
                .setLastName("Stud")
                .setDataOfBirth("06/03/2010")
                .setEmail(email)
                .setPassword(password)
                .setConfirmPassword(password)
                .clickOnSubmitButton()
                .verifyExpectedTextOnPage("The value length shouldn't exceed 255 symbols.");
    }

    @Test
    public void registerWithPasswordTooShort(){
        String email = "aqastud@mail.com";
        String password = "Qwer123";
        registrationPage.openRegistrationPage()
                .setFirstName("Aqa")
                .setLastName("Stud")
                .setDataOfBirth("06/03/2010")
                .setEmail(email)
                .setPassword(password)
                .setConfirmPassword(password)
                .verifyExpectedTextOnPage("Minimum 8 characters")
                .verifySubmitButtonIsDisabled();
    }

    @Test
    public void registerWithPasswordsNotMatch(){
        String email = "aqastud@mail.com";
        String password = "Qwer123$";
        String password2 = "Qwer1234";
        registrationPage.openRegistrationPage()
                .setFirstName("Aqa")
                .setLastName("Stud")
                .setDataOfBirth("06/03/2010")
                .setPassword(password)
                .setConfirmPassword(password2)
                .setEmail(email)
                .verifyExpectedTextOnPage("Passwords must match")
                .verifySubmitButtonIsDisabled();
    }
}
