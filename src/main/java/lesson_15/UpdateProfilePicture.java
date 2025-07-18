package lesson_15;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UpdateProfilePicture {
    public static void updatePicture(String email, String password, String newImagePath) {
        WebDriver driver = DriverSetup.getDriver();

        driver.get("https://qa-course-01.andersenlab.com/login");

        driver.findElement(By.xpath("//input[@name=\"email\"]"))
                .sendKeys(email);

        driver.findElement(By.xpath("//input[@name=\"password\"][@placeholder=\"Enter password\"]"))
                .sendKeys(password);

        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

        WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt=''][contains(@src,'data:image')]")));

        //emulate user hovering mouse above the image to reveal Upload image/button
        WebElement picture = driver.findElement(By.xpath("//img[@alt=''][contains(@src,'data:image')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(picture).perform();
        if (driver.findElement(By.xpath("//img[contains(@src, 'upload_photo')]")).isDisplayed()) {
            //String imageSrc = driver.findElement(By.xpath("//img[@alt=''][contains(@src,'data:image')]")).getAttribute("src");
            // AFAIK, there should be a hidden input to upload a file
            driver.findElement(By.xpath("//input")).sendKeys(newImagePath);
            //String updatedSrc = driver.findElement(By.xpath("//img[@alt=''][contains(@src,'data:image')]")).getAttribute("src");
            //if (imageSrc.equals(updatedSrc)) { System.out.println("Provided image is the same as already in the profile!"); }
        }

        Wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section/div/img[@alt='Close']"))).click();
    }
}
