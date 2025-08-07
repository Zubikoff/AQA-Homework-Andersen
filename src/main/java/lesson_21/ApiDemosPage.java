package lesson_21;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ApiDemosPage {
    AppiumDriver driver;
    WebDriverWait wait;

    private final By views = AppiumBy.accessibilityId("Views");
    private final By dateWidgets = AppiumBy.accessibilityId("Date Widgets");
    private final By dialog = AppiumBy.accessibilityId("1. Dialog");
    private final By changeTheDate = AppiumBy.accessibilityId("change the date");
    private final By changeTheTime = AppiumBy.accessibilityId("change the time (spinner)");
    private final By nextMonth = AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"Next month\"]");
    private final By dateOKButton = AppiumBy.id("android:id/button1");
    private final By textSwitcher = AppiumBy.accessibilityId("TextSwitcher");
    private final By nextButton = AppiumBy.accessibilityId("Next");

    public ApiDemosPage(AppiumDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public ApiDemosPage openViewPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(views)).click();
        return this;
    }

    public int countViewElementsOnPage(){
        List<WebElement> elements;
        Set<String> uniqueElements = new HashSet<>();

        //"(//android.widget.FrameLayout)[3]//android.widget.TextView" //to exclude "API Demos" text view
        elements = driver.findElements(AppiumBy.xpath("(//android.widget.FrameLayout)[3]//android.widget.TextView"));
        for (WebElement element: elements){
            uniqueElements.add(element.getText()); // may and WILL fail if the elements have the same text,
            // but it technically can and what do I do with that?
        }

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"Grid\"))"
        ));
        elements = driver.findElements(AppiumBy.xpath("(//android.widget.FrameLayout)[3]//android.widget.TextView"));
        for (WebElement element: elements){
            uniqueElements.add(element.getText());
        }

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"Rotating Button\"))"
        ));
        elements = driver.findElements(AppiumBy.xpath("(//android.widget.FrameLayout)[3]//android.widget.TextView"));
        for (WebElement element: elements){
            uniqueElements.add(element.getText());
        }

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"TextSwitcher\"))"
        ));
        elements = driver.findElements(AppiumBy.xpath("(//android.widget.FrameLayout)[3]//android.widget.TextView"));
        for (WebElement element: elements){
            uniqueElements.add(element.getText());
        }

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"WebView3\"))"
        ));
        elements = driver.findElements(AppiumBy.xpath("(//android.widget.FrameLayout)[3]//android.widget.TextView"));
        for (WebElement element: elements){
            uniqueElements.add(element.getText());
        }

        return uniqueElements.size();
    }

    public ApiDemosPage openDateWidgetsPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateWidgets)).click();
        return this;
    }

    public ApiDemosPage openDateWidgetsDialog(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialog)).click();
        return this;
    }

    public ApiDemosPage tapChangeTheDateButton(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeTheDate)).click();
        return this;
    }

    public ApiDemosPage setTomorrowDate(){
        LocalDate nextDay = LocalDate.now().plusDays(1);
        if (nextDay.getDayOfMonth() == 1) {
            // next day is next month - click on month button
            wait.until(ExpectedConditions.visibilityOfElementLocated(nextMonth)).click();
        } else {
            // simply click on the next day
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                    .xpath("//android.view.View[@text=\"" + nextDay.getDayOfMonth() + "\"]"))).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateOKButton)).click();
        return this;
    }

    public ApiDemosPage tapChangeTheTimeButton(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(changeTheTime)).click();
        return this;
    }

    public ApiDemosPage setRequiredTime(){
        String hours = "11";
        String minutes = "11";

        // if minutes go past 59 or below 00 the hour will change, so this need to be set BEFORE hours
        while (!wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                .xpath("(//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"])[2]"))).getText().equals(minutes)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                    .xpath("(//android.widget.Button)[4]"))).click();
        }

        // changing hour may change AM/PM, so it should be BEFORE AM/PM
        while (!wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                .xpath("(//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"])[1]"))).getText().equals(hours)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                    .xpath("(//android.widget.Button)[2]"))).click();
        }

        String timeFormat = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                .xpath("(//android.widget.EditText[@resource-id=\"android:id/numberpicker_input\"])[3]"))).getText();
        if (timeFormat.equals("AM")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                    .xpath("//android.widget.Button[@text=\"PM\"]"))).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(dateOKButton)).click();
        return this;
    }


    //Task 3: //android.widget.TextView[@content-desc="Views"] -> //android.widget.Button[@content-desc="Next"]
    // text label: (//android.widget.TextView)[2]

    public ApiDemosPage openTextSwitcherPage(){
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"TextSwitcher\"))"
        ));
        wait.until(ExpectedConditions.visibilityOfElementLocated(textSwitcher)).click();
        return this;
    }

    public ApiDemosPage tapNextButtonNTimes(int n){
        for (int i = 0; i < n; i++) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(nextButton)).click();
        }
        return this;
    }

    public int getNumberOfTapsText(){
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy
                .xpath("(//android.widget.TextView)[2]"))).getText());
    }
}
