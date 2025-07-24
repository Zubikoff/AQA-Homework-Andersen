package lesson_16.userLogin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class DataProviderTest {

    @DataProvider(name = "logInData")
    public Object[][] provideLoginData() {
        return new Object[][]{
                {"aqa521mail@mail.com", "Qwer!234"},
                {"aqa522mail@mail.com", "1234QWER"},
                {"aqa523mail@mail.com", "qweR1@34"}
        };
    }

    public WebDriver driver;

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(dataProvider = "logInData")
    public void testingTest(String email, String password) {

        driver.get("https://qa-course-01.andersenlab.com/login");
        driver.findElement(By.xpath("//input[@name=\"email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys(password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\"submit\"]"))).click();

        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://qa-course-01.andersenlab.com/", "Wrong page URL!");
        By emailLocator = By.xpath("//div[text()=\"" + email + "\"]");
        Assert.assertFalse(driver.findElements(emailLocator).isEmpty(), "Could not confirm the correct user login!");

    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }
}
