package lesson_17;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

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
    }

    @AfterClass
    public void quitDriver() {
        //driver.quit();
    }

    @BeforeMethod
    public void LogIn(){
        driver.get("https://qa-course-01.andersenlab.com/login");
        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/login"));

        driver.findElement(By.xpath("//input[@name=\"email\"]"))
                .sendKeys(email);
        driver.findElement(By.xpath("//input[@name=\"password\"]"))
                .sendKeys(password);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));
    }

    public void selectMenuItem(String item){
        actions.moveToElement(driver.findElement(By.xpath("//div[text()='AQA Practice']"))).perform();
        By menuItemLocator = By.xpath("//div[text()='"+ item + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuItemLocator)).click();
    }

    @Test
    @Ignore
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

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", coursesSelectFirst);
        actions.moveToElement(coursesSelectFirst)
                .keyDown(Keys.CONTROL)
                .click()
                .keyUp(Keys.CONTROL)
                .build().perform();

        js.executeScript("arguments[0].scrollIntoView(true)", coursesSelectSecond);
        actions.moveToElement(coursesSelectSecond)
                .keyDown(Keys.CONTROL)
                .click()
                .keyUp(Keys.CONTROL)
                .build().perform();

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/search_results"));

        Assert.assertTrue(driver
                .findElement(By.xpath("//*[text()='Unfortunately, we did not find any courses matching your chosen criteria.']"))
                .isDisplayed(), "Expected text not found");

    }

    @Test
    public void dragAndDrop(){
        selectMenuItem("Drag & Drop");

        wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/dragndrop"));

        actions.moveToElement(driver.findElement(By.id("manual1")))
                .clickAndHold()
                .moveToElement(driver.findElement(By.id("target-manual1")))
                .release()
                .build().perform();

        actions.moveToElement(driver.findElement(By.id("manual2")))
                .clickAndHold()
                .moveToElement(driver.findElement(By.id("target-manual2")))
                .release()
                .build().perform();

        // Drag & Drop using built-in method
        WebElement firstAutoBlockSource = driver.findElement(By.id("auto1"));
        WebElement firstAutoBlockTarget = driver.findElement(By.id("target-auto1"));
        WebElement secondAutoBlockSource = driver.findElement(By.id("auto2"));
        WebElement secondAutoBlockTarget = driver.findElement(By.id("target-auto2"));

        actions.dragAndDrop(firstAutoBlockSource, firstAutoBlockTarget).perform();
        actions.dragAndDrop(secondAutoBlockSource, secondAutoBlockTarget).perform();
    }
}
