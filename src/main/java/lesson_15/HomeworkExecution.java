package lesson_15;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class HomeworkExecution {
    public static void main(String[] args) {
        try {

            // Task #1 - Automate two test cases from each module from previous homework
            System.out.println("Registration module tests");
            System.out.println("  First test - User is able to register using valid data: "
                    + returnTestResult(AutomatedTestCases.RegistrationModule.registerWithValidData()));
            System.out.println("  Second test - User is unable to register using email already present in the system: "
                    + returnTestResult(AutomatedTestCases.RegistrationModule.tryRegisterAlreadyRegisteredUser()));
            // I have only one test in user cabinet, so 3 for user registration
            System.out.println("  Third test - User is unable to register if entered password does not match confirm password: "
                    + returnTestResult(AutomatedTestCases.RegistrationModule.tryRegisterUserWithPassNotMatchingConfirmPass()));

            System.out.println("Log In module tests");
            System.out.println("  First test - User is able to log in with valid email and password: "
                    + returnTestResult(AutomatedTestCases.LoginModule.SuccessfulLogin()));
            System.out.println("  Second test - User is unable to log in with valid email and invalid password: "
                    + returnTestResult(AutomatedTestCases.LoginModule.LoginWithInvalidPass()));

            System.out.println("User cabinet module test");
            System.out.println("  First test - User is able to sign out: "
                    + returnTestResult(AutomatedTestCases.UserCabinetModule.LogOut()));

            // Task #2 - Write a program that will open five different pages in new windows;
            // Write a loop that will sequentially switch between all the pages, printing the title and link of each page to the console.
            // Close the page that contains the word "Zoo" in the title.
            System.out.println("============================================================================");
            System.out.println("Switching between pages");
            SwitchBetweenPages.switchBetweenPages();

            //Task #3 - A methods to compare two WebElements
            System.out.println("============================================================================");
            System.out.println("Comparing two Web Elements");
            CompareTwoWebElements.FindAndCompareElementsExample();

            // Task #4 - Write a script that opens a browser and logs in an already registered user
            // on the https://qa-course-01.andersenlab.com/
            System.out.println("============================================================================");
            System.out.println("Logs in as registered user");
            LogInRegisteredUser.logIn(AutomatedTestCases.getValidEmail(), AutomatedTestCases.password);
            System.out.println("User logged in successfully");

            // Task #5 - Replicate the actions in the video (upload an image)
            // trying to use a file uploaded to src/main/resource folder of the project
            System.out.println("============================================================================");
            System.out.println("Uploading new image to the profile");
            URL imageURL = HomeworkExecution.class.getClassLoader().getResource("cat.jpg");
            if (imageURL == null) {
                System.out.println("Could not automatically find an image in resources!");
            } else {
                String imagePath = "";
                imagePath = (new File(imageURL.getFile())).getAbsolutePath();
                System.out.println("Found an image path: " + imagePath);
                UpdateProfilePicture.updatePicture(AutomatedTestCases.getValidEmail(), AutomatedTestCases.password, imagePath);
            }
            System.out.println("Successfully uploaded new image");
        }
        finally {
            // not sure if this is the best solution, but at least the driver should be closed eventually
            DriverSetup.getDriver().quit();
        }
    }

    public static String returnTestResult(boolean testResult) {
        if (testResult) { return "Passed"; }
        else { return "Failed"; }
    }
}
