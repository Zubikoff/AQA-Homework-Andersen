package lesson_18;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import lesson_18.pages.LoginPage;
import lesson_18.pages.RegistrationPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.PageListener;
import utilities.UtilityMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners({PageListener.class})
public class PageTests {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static RegistrationPage registrationPage;

    public void takeScreenshot(String methodName) {
        try {
            // Take screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Define screenshot path and filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = "target/allure-results/screenshot-" + methodName + "_" + timestamp + ".png";

            // Copy the screenshot to the destination
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
            // Add screenshot to Allure
            Allure.addAttachment("Screenshot for " + methodName, new FileInputStream(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void makeScreenshotIfTestFailed(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getMethod().getMethodName());
        }
    }

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @AfterClass
    public void close() {
        driver.quit();
    }

    @Description("User is able to log in with valid email and password")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login user")
    @Test
    public void logInPositive() {
        String email = "aqastud@mail.com";
        String password = "Qwer123$";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyLogInSuccessful();
    }

    @Description("User is unable to log in with valid email and invalid password")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login user")
    @Test
    public void logInWithInvalidPassword() {
        String email = "aqastud@mail.com";
        String password = "Qwer1234";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Email or password is not valid")
                .verifySignInButtonIsDisabled();
    }

    @Description("User is unable to log in with invalid email")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login user")
    @Test
    public void logInWithInvalidEmail() {
        String email = UtilityMethods.getInvalidEmail("aqastud@mail.com");
        String password = "Qwer123$";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Email or password is not valid")
                .verifySignInButtonIsDisabled();
    }

    @Description("User is unable to click \"Sign in\" without filling \"Email\" and \"Password\" fields")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login user")
    @Test
    public void logInWithEmptyFields() {
        loginPage.openLoginPage()
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Required")
                .verifySignInButtonIsDisabled();
    }

    @Description("User is unable to click \"Sign in\" with email domain longer than 63 characters")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login user")
    @Test
    public void logInWithTooLongEmail() {
        String email = "aqa@1234567890123456789012345678901234567890123456789012345678901234.com";
        String password = "Qwer123$";
        loginPage.openLoginPage()
                .setEmail(email)
                .setPassword(password)
                .clickOnSignInButton()
                .verifyExpectedTextOnPage("Invalid email address")
                .verifySignInButtonIsDisabled();
    }

    @Description("User is able to register using valid data")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Register user")
    @Test
    public void registerPositive() {
        String email = UtilityMethods.getValidEmail();
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

    @Description("User is unable to register using email already present in the system")
    @Severity(SeverityLevel.NORMAL)
    @Story("Register user")
    @Test
    public void registerAlreadyRegistered() {
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

    @Description("User is unable to register using first name longer then 255 characters")
    @Severity(SeverityLevel.NORMAL)
    @Story("Register user")
    @Test
    public void registerWithFirstNameTooLong() {
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

    @Description("User is unable to register if entered password is under 8 characters")
    @Severity(SeverityLevel.NORMAL)
    @Story("Register user")
    @Test
    public void registerWithPasswordTooShort() {
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

    @Description("User is unable to register if entered password does not match confirm password")
    @Severity(SeverityLevel.NORMAL)
    @Story("Register user")
    @Test
    public void registerWithPasswordsNotMatch() {
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
