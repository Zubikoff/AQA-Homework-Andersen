package lesson_15;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

import java.util.Iterator;
import java.util.Set;

public class SwitchBetweenPages {
    public enum Pages{
        AUTOMATIONPRACTICE("http://www.automationpractice.pl/index.php"),
        PL_ZOO("https://zoo.waw.pl/"),
        W3SCHOOLS("https://www.w3schools.com/"),
        CLICK_COUNTER("https://www.clickspeedtester.com/click-counter/"),
        ANDERSEN("https://andersenlab.com/");

        public final String url;

        Pages(String url) {
            this.url = url;
        }
    }

    public static void switchBetweenPages() {
        WebDriver driver = DriverSetup.getDriver();

        WindowType window = WindowType.WINDOW; // to easily switch between windows/tabs
        driver.get(Pages.AUTOMATIONPRACTICE.url);
        driver.switchTo().newWindow(window);
        driver.get(Pages.PL_ZOO.url);
        driver.switchTo().newWindow(window);
        driver.get(Pages.W3SCHOOLS.url);
        driver.switchTo().newWindow(window);
        driver.get(Pages.CLICK_COUNTER.url);
        driver.switchTo().newWindow(window);
        driver.get(Pages.ANDERSEN.url);

        Set<String> windowHandles = driver.getWindowHandles();
        String windowHandleToClose = "";
        String windowHandleToSwitchTo = ""; // to switch to another window
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
            System.out.println("Page: " + driver.getTitle() + "   URL: " + driver.getCurrentUrl());
            if (driver.getTitle().contains("Zoo")) {
                windowHandleToClose = windowHandle;
            }
            else {
                windowHandleToSwitchTo = windowHandle;
            }
        }
        driver.switchTo().window(windowHandleToClose).close();
        driver.switchTo().window(windowHandleToSwitchTo);

    }
}
