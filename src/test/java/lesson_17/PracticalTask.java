package lesson_17;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PracticalTask {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public static String email = "aqastud@mail.com";
    public static String password = "Qwer123$";

    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        actions = new Actions(driver);

        driver.get("https://qa-course-01.andersenlab.com/login");
        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/login"));

        driver.findElement(By.xpath("//input[@name=\"email\"]"))
                .sendKeys(email);
        driver.findElement(By.xpath("//input[@name=\"password\"]"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));
    }

    @AfterClass
    public void quitDriver() {
        //driver.quit();
    }

    public void selectMenuItem(String item){
        driver.findElement(By.xpath("//div[text()='AQA Practice']"));
        actions.moveToElement(driver.findElement(By.xpath("//div[text()='AQA Practice']"))).perform();
        By menuItemLocator = By.xpath("//div[text()='"+ item + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuItemLocator)).click();
    }

    @Test
    public void selectChoosing() {
        selectMenuItem("Select");

        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/select"));

        WebElement countryDropDown = driver.findElement(By.xpath("//select[@data-lol='SelectCountry']"));
        Select select = new Select(countryDropDown);
        actions.moveToElement(driver.findElement(By.xpath("//select[@data-lol='SelectCountry']")))
                .click()
                .build().perform();

        select.selectByVisibleText("USA");
        actions.moveToElement(driver.findElement(By.xpath("//select[@data-lol='SelectCountry']")))
                .click()
                .build().perform();

        WebElement languageDropDown = driver.findElement(By.xpath("//select[@id='SelectLanguage']"));
        select = new Select(languageDropDown);
        actions.moveToElement(driver.findElement(By.xpath("//select[@id='SelectLanguage']")))
                .click()
                .build().perform();

        select.selectByVisibleText("English");
        actions.moveToElement(driver.findElement(By.xpath("//select[@id='SelectLanguage']")))
                .click()
                .build().perform();

        WebElement typeDropDown = driver.findElement(By.xpath("//select[@data-doubtful-but-ok='SelectType']"));
        select = new Select(typeDropDown);
        actions.moveToElement(driver.findElement(By.xpath("//select[@data-doubtful-but-ok='SelectType']")))
                .click()
                .build().perform();

        select.selectByVisibleText("Testing");
        actions.moveToElement(driver.findElement(By.xpath("//select[@data-doubtful-but-ok='SelectType']")))
                .click()
                .build().perform();

        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        //System.out.println(nextMonday.getDayOfMonth() + " " + nextMonday.getMonthValue() + " " + nextMonday.getYear());
        driver.findElement(By.xpath("//input[@data-calendar='1']")).sendKeys(String.valueOf(nextMonday.getDayOfMonth()));
        driver.findElement(By.xpath("//input[@data-calendar='1']")).sendKeys(String.valueOf(nextMonday.getMonthValue()));
        driver.findElement(By.xpath("//input[@data-calendar='1']")).sendKeys(String.valueOf(nextMonday.getYear()));


        LocalDate twoWeeksFromNextMonday = nextMonday.plusWeeks(2);
        driver.findElement(By.xpath("//input[@data-calendar='2']")).sendKeys(String.valueOf(twoWeeksFromNextMonday.getDayOfMonth()));
        driver.findElement(By.xpath("//input[@data-calendar='2']")).sendKeys(String.valueOf(twoWeeksFromNextMonday.getMonthValue()));
        driver.findElement(By.xpath("//input[@data-calendar='2']")).sendKeys(String.valueOf(twoWeeksFromNextMonday.getYear()));

        WebElement coursesSelectFirst = driver.findElement(By.xpath("//select[@id='MultipleSelect']//option[@value='AQA Python']"));
        WebElement coursesSelectSecond = driver.findElement(By.xpath("//select[@id='MultipleSelect']//option[@value='AQA Java']"));

        actions.moveToElement(coursesSelectFirst)
                .click()
                .build().perform();
    }
}
