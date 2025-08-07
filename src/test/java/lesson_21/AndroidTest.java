package lesson_21;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class AndroidTest {
    private static AppiumDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAvd("Pixel_3a_API_34_extension_level_7_x86_64")
                .setPlatformVersion("14.0")
                .setAppPackage("io.appium.android.apis")
                .setAppActivity("io.appium.android.apis.ApiDemos");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        //System.out.println("Session ID: " + driver.getSessionId());
    }

    @AfterMethod
    public void quitDriver(){
        driver.quit();
    }

    @Test
    public void countViews(){
        ApiDemosPage page = new ApiDemosPage(driver);
        page.openViewPage();
        Assert.assertEquals(page.countViewElementsOnPage(), 42);
    }

    @Test
    public void setDateTime(){
        ApiDemosPage page = new ApiDemosPage(driver);
        page.openViewPage()
                .openDateWidgetsPage()
                .openDateWidgetsDialog()
                .tapChangeTheDateButton()
                .setTomorrowDate()
                .tapChangeTheTimeButton()
                .setRequiredTime();
    }

    @Test
    public void countTaps(){
        ApiDemosPage page = new ApiDemosPage(driver);

        int numberOfTaps = new Random().nextInt(15);
        page.openViewPage()
                .openTextSwitcherPage()
                .tapNextButtonNTimes(numberOfTaps);

        Assert.assertEquals(page.getNumberOfTapsText(), numberOfTaps);
    }
}
