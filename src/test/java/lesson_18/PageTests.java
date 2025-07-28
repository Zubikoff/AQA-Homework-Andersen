package lesson_18;

import lesson_18.pages.LoginPage;
import lesson_18.pages.RegistrationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static LoginPage loginPage;
    private static RegistrationPage registrationPage;

    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        contactUsPage = new ContactUsPage(driver);
        contactUsFluentPage = new ContactUsFluentPage(driver);
    }

    @AfterClass
    public void close(){
        //driver.quit();
    }

    @Test
    public void contactUsPositive(){
        driver.get("http://www.automationpractice.pl/index.php?controller=contact");

        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys("some@mail.geg");
        new Select(driver.findElement(select)).selectByValue("2");
        driver.findElement(id_order).sendKeys("61253");
        driver.findElement(message).sendKeys("Hello!");
        driver.findElement(submit).click();

        String expectedMessage = "Your message has been successfully sent to our team.";
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(alert)).getText();
        //System.out.println(actualMessage);
        Assert.assertEquals(expectedMessage, actualMessage, "Message is incorrect!");

    }
}
