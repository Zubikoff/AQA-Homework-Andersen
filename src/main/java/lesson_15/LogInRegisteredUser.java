package lesson_15;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogInRegisteredUser {
    public static void logIn(String email, String password) {
        WebDriver driver = DriverSetup.getDriver();

        driver.get("https://qa-course-01.andersenlab.com/login");

        driver.findElement(By.xpath("//input[@name=\"email\"]"))
                .sendKeys(email);

        driver.findElement(By.xpath("//input[@name=\"password\"][@placeholder=\"Enter password\"]"))
                .sendKeys(password);

        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
    }
}
