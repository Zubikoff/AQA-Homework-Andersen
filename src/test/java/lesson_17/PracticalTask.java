package lesson_17;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    public WebDriverWait wait;

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));


    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }

    public void selectMenuItem(String item){
        driver.findElement(By.xpath("//div[text()='AQA Practice']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//div[text()='AQA Practice']"))).perform();
        By menuItemLocator = By.xpath("//div[text()='"+ item + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuItemLocator)).click();

    }

    @Test
    public void selectChoosing() {
        selectMenuItem("Select");
    }
}
