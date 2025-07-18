package lesson_15;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompareTwoWebElements {
    public static void FindAndCompareElementsExample() {
        WebDriver driver = DriverSetup.getDriver();
        driver.get("http://www.automationpractice.pl/index.php");
        WebElement first = driver.findElement(By
                .cssSelector("img[src='http://www.automationpractice.pl/modules/themeconfigurator/img/banner-img1.jpg']"));
        WebElement second = driver.findElement(By.cssSelector("input#search_query_top"));

        CompareElements(first, second);
    }

    public static void CompareElements(WebElement firstElement, WebElement secondElement) {
        if (firstElement.equals(secondElement)) {
            System.out.println("You have provided the same element.");
        }

        System.out.println("Element comparison: " + firstElement.getTagName() + " compare to " + secondElement.getTagName());
        if (firstElement.getLocation().getY() < secondElement.getLocation().getY()) {
            System.out.println("First element " + firstElement.getTagName() + " is positioned higher.");
        } else if (firstElement.getLocation().getY() > secondElement.getLocation().getY()) {
            System.out.println("Second element " + secondElement.getTagName() + " is positioned higher.");
        } else {
            System.out.println("Both elements has the same Y position.");
        }

        if (firstElement.getLocation().getX() < secondElement.getLocation().getX()) {
            System.out.println("First element " + firstElement.getTagName() + " is positioned to the left.");
        } else if (firstElement.getLocation().getX() > secondElement.getLocation().getX()) {
            System.out.println("Second element " + secondElement.getTagName() + " is positioned to the left.");
        } else {
            System.out.println("Both elements has the same X position.");
        }

        int firstArea = firstElement.getSize().getWidth() * firstElement.getSize().getHeight();
        int secondArea = secondElement.getSize().getWidth() * secondElement.getSize().getHeight();
        if (firstArea > secondArea) {
            System.out.println("First element " + firstElement.getTagName() + " is bigger.");
        } else if (firstArea < secondArea) {
            System.out.println("Second element " + secondElement.getTagName() + " is bigger.");
        } else {
            System.out.println("Elements has the same area.");
        }
    }
}
