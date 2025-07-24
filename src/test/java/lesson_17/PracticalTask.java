package lesson_17;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class PracticalTask {
    //aqastud@mail.com
    //Qwer123$

    public WebDriver driver;

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }

    public static void selectMenuItem(String){
        driver.findElement(By.xpath("//div[text()='AQA Practice']"));
    }

    @Test
    public void selectChoosing() {
        //div[text()='AQA Practice']
        //div[text()='Select']
    }
}
