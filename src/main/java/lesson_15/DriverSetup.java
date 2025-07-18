package lesson_15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverSetup {
    private static WebDriver driver;

    private static WebDriver setUpDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = setUpDriver();
        }
        return driver;
    }
}
