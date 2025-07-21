package lesson_15;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class AutomatedTestCases {
    private static String validEmail; // an email that should be successfully registered
    private static String invalidEmail; // an email that should NOT be registered in the system
    public static final String password = "Qwer123$";
    public static final String invalidPassword = "Qwer1234";

    /**
     * @return Randomly generated email that is assigned only once during program execution
     */
    public static String getValidEmail(){
        if (validEmail == null) {
            validEmail = getRandomEmail();
        }
        return validEmail;
    }

    /**
     * @return Randomly generated email that is NOT equals to valid email
     */
    public static String getInvalidEmail() {
        if (invalidEmail == null) {
            invalidEmail = getRandomEmail();
        }
        while (invalidEmail.equals(validEmail)) {
            invalidEmail = getRandomEmail(); // just in case randomizer returned the same email, reroll until it isn't
        }
        return invalidEmail; // method was of no use in the end, but I will keep it
    }

    private static String getRandomEmail(){
        Random rand = new Random();
        return "aqastud" + (rand.nextInt(9000)+999)
                + "@mail" + (rand.nextInt(9000)+999) + ".com";
    }

    public static class RegistrationModule {

        /**
         * Title: User is able to register using valid data.
         * Steps:
         * 1. Go to https://qa-course-01.andersenlab.com/registration
         * 2. Enter first name
         * 3. Enter last name
         * 4. Enter date of birth
         * 5. Enter email
         * 6. Enter password
         * 7. Enter confirm password the same value as password
         * 8. Click "Submit" button
         * @return True if no exceptions occurred and the user was redirected to login page
         */
        public static boolean registerWithValidData() {
            try {
                WebDriver driver = DriverSetup.getDriver();
                driver.get("https://qa-course-01.andersenlab.com/registration");

                driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys("Aqa");

                driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys("Stud");

                driver.findElement(By.cssSelector("input[name='dateOfBirth']")).sendKeys("06/03/2010");
                // this is the easiest way I found to make this annoying react-datepicker dissappear
                driver.findElement(By.cssSelector("input[name='dateOfBirth']")).sendKeys(Keys.ESCAPE);

                driver.findElement(By.cssSelector("input[name='email'][placeholder='Enter email']"))
                        .sendKeys(getValidEmail());

                driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
                driver.findElement(By.cssSelector("input[name='passwordConfirmation']")).sendKeys(password);

                try {
                    // the button becomes enabled not instantly - we have to use waiter
                    WebDriverWait buttonWait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    WebElement button = buttonWait.until(ExpectedConditions
                            .elementToBeClickable(By.cssSelector("button[type='submit']")));
                    button.click();
                } catch (ElementClickInterceptedException e) {
                    // button.click() throws ElementClickInterceptedException because of
                    // react-datepicker div generated for "Date of birth" field overlaps the button,
                    // even though the click on the button still happens.
                    // I found the way to remove the datepicker, but will keep this block just in case
                }

                // successful registration should redirect us to login page
                try {
                    WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    Wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/login"));
                    return true;
                } catch (TimeoutException e) {
                    // unexpected, we should be redirected to the page
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getClass() + "   " + e.getMessage());
                System.out.println("/////////////////////// Unexpected error during registration of new valid user! ///////////////////////");
                return false;
            }
        }

        /**
         * Title: User is unable to register using email already present in the system.
         * Steps:
         * 1. Go to https://qa-course-01.andersenlab.com/registration
         * 2. Enter first name
         * 3. Enter last name
         * 4. Enter date of birth
         * 5. Enter email
         * 6. Enter password
         * 7. Enter confirm password the same value as password
         * 8. Click "Submit" button
         * @return True if no exceptions occurred and the correct error message was displayed
         */
        public static boolean tryRegisterAlreadyRegisteredUser() {
            try {
                // we need to ensure that the user is registered
                // if already registered, this method will simply return false
                registerWithValidData();

                WebDriver driver = DriverSetup.getDriver();

                driver.get("https://qa-course-01.andersenlab.com/registration");

                driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys("Aqa");

                driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys("Stud");

                driver.findElement(By.cssSelector("input[name='dateOfBirth']")).sendKeys("06/03/2010");
                // this is the easiest way I found to make this annoying react-datepicker dissappear
                driver.findElement(By.cssSelector("input[name='dateOfBirth']")).sendKeys(Keys.ESCAPE);

                driver.findElement(By.cssSelector("input[name='email'][placeholder='Enter email']"))
                        .sendKeys(getValidEmail());

                driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
                driver.findElement(By.cssSelector("input[name='passwordConfirmation']")).sendKeys(password);

                // the button becomes enabled not instantly - we have to use waiter
                WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                WebElement button = Wait.until(ExpectedConditions
                        .elementToBeClickable(By.cssSelector("button[type='submit']")));
                button.click();

                String errorMessage = "User with email " + getValidEmail() + " already exist.";
                String errorMessageLocator = "//*[contains(text(), '"+ errorMessage +"')]";
                try {
                    // not sure if reusing the same waiter is right, but I will try
                    Wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                    Wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(errorMessageLocator)));
                } catch (TimeoutException e) {
                    // this is expected to happen, as the error message is returned via API response
                    // and not displayed on the page
                    return false;
                }

                return true; // if somehow the program found the error message, then the test case considered passed
            } catch (Exception e) {
                System.out.println(e.getClass() + "   " + e.getMessage());
                System.out.println("/////////////////////// Unexpected error during registration attempt of already registered user! ///////////////////////");
                return false;
            }
        }

        /**
         * Note: 3rd method in registration module due to fact that I have only 1 test case in User cabinet module
         * Title: User is unable to register if entered password does not match confirm password.
         * Steps:
         * 1. Go to https://qa-course-01.andersenlab.com/registration
         * 2. Enter first name
         * 3. Enter last name
         * 4. Enter date of birth
         * 5. Enter email
         * 6. Enter password
         * 7. Enter confirm password
         * 8. Click "Submit" button
         * @return True if no exceptions occurred and the correct error message
         * was displayed under Confirm password field
         */
        public static boolean tryRegisterUserWithPassNotMatchingConfirmPass() {
            try {
                WebDriver driver = DriverSetup.getDriver();

                driver.get("https://qa-course-01.andersenlab.com/registration");

                driver.findElement(By.cssSelector("input[name='firstName']")).sendKeys("Aqa");

                driver.findElement(By.cssSelector("input[name='lastName']")).sendKeys("Stud");

                driver.findElement(By.cssSelector("input[name='dateOfBirth']")).sendKeys("06/03/2010");
                // this is the easiest way I found to make this annoying react-datepicker dissappear
                driver.findElement(By.cssSelector("input[name='dateOfBirth']")).sendKeys(Keys.ESCAPE);

                driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
                driver.findElement(By.cssSelector("input[name='passwordConfirmation']")).sendKeys(invalidPassword);

                // technically, this is 'cheating', but the error message will be
                // displayed only if the "Confirm password" field will lose focus
                driver.findElement(By.cssSelector("input[name='email'][placeholder='Enter email']"))
                        .sendKeys(getValidEmail());

                // try not only find the right error message, but also confirm that it is under Confirm Password field
                By locator = By.xpath("//input[@name='passwordConfirmation']/../../div/span[text()='Passwords must match']");
                try {
                    WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                    Wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                    // also make sure that Submit button is disabled
                    return !driver.findElement(By.cssSelector("button[type='submit']")).isEnabled();
                } catch (TimeoutException e) {
                    // not expected, we should see the error message
                    //System.out.println("Error message not found");
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getClass() + "   " + e.getMessage());
                System.out.println("/////////////////////// Unexpected error during registration attempt with password not matching confirm password! ///////////////////////");
                return false;
            }
        }
    }

    public static class LoginModule {

        /**
         * Title: User is able to log in with valid email and password.
         * Steps:
         * 1. Go to https://qa-course-01.andersenlab.com/
         * 2. Enter email
         * 3. Enter password
         * 4. Click "Sign in" button
         * @return True if no exceptions occurred and the user was redirected to his cabinet
         */
        public static boolean SuccessfulLogin() {
            WebDriver driver = DriverSetup.getDriver();

            try {
                // we need to ensure that the user is registered
                // if already registered, this method will simply return false
                // no new user will be created and registration attempt will fail
                RegistrationModule.registerWithValidData();

                driver.get("https://qa-course-01.andersenlab.com/login");

                // I tried to use CSS selector in Registration tests, so here I will
                // try to use only XPath (and double quotes)
                driver.findElement(By.xpath("//input[@name=\"email\"]"))
                        .sendKeys(getValidEmail());

                driver.findElement(By.xpath("//input[@name=\"password\"][@placeholder=\"Enter password\"]"))
                        .sendKeys(password);

                driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

                try {
                    WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    Wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/"));
                } catch (TimeoutException e) {
                    // unexpected, we should be redirected to the page
                    // or I might simply confused "https://qa-course-01.andersenlab.com"
                    // with "https://qa-course-01.andersenlab.com/" (might have missed the slash)
                    return false;
                }

                // can probably omit searching for sibling as there should be only one email on the page
                By locator = By.xpath("//div[text()=\"E-mail\"]/following-sibling::div[text()=\"" + getValidEmail() + "\"]");
                return driver.findElement(locator).isDisplayed();

            } catch (Exception e) {
                System.out.println(e.getClass() + "   " + e.getMessage());
                System.out.println("/////////////////////// Unexpected error during login attempt! ///////////////////////");
                return false;
            }
        }

        /**
         * Title: User is unable to log in with valid email and invalid password.
         * Steps:
         * 1. Go to https://qa-course-01.andersenlab.com/
         * 2. Enter email
         * 3. Enter invalid password
         * 4. Click "Sign in" button
         * @return True if no exceptions occurred and expected error message was displayed somewhere on the page
         */
        public static boolean LoginWithInvalidPass() {
            WebDriver driver = DriverSetup.getDriver();

            try {
                // we need to ensure that the user is registered
                // if already registered, this method will simply return false
                // no new user will be created and registration attempt will fail
                RegistrationModule.registerWithValidData();

                driver.get("https://qa-course-01.andersenlab.com/login");

                // I tried to use CSS selector in Registration tests, so here I will
                // try to use only XPath (and double quotes)
                driver.findElement(By.xpath("//input[@name=\"email\"]"))
                        .sendKeys(getValidEmail());

                driver.findElement(By.xpath("//input[@name=\"password\"][@placeholder=\"Enter password\"]"))
                        .sendKeys(invalidPassword);

                driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

                By locator = By.xpath("//*[text()=\"Email or password is not valid\"]");
                try {
                    WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    return Wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
                } catch (TimeoutException e) {
                    // unexpected, there should be error message somewhere on the page
                    // though there should be 2 of them, the method should return the first found
                    return false;
                }

            } catch (Exception e) {
                System.out.println(e.getClass() + "   " + e.getMessage());
                System.out.println("/////////////////////// Unexpected error during login attempt with invalid password! ///////////////////////");
                return false;
            }
        }
    }

    public static class UserCabinetModule {
        /**
         * Title: User is able to sign out.
         * Steps:
         * 1. Go to https://qa-course-01.andersenlab.com/
         * 2. Enter email
         * 3. Enter password
         * 4. Click "Sign in" button
         * 5. Click "Sign Out" button
         * @return True if no exceptions occurred and expected error message was displayed somewhere on the page
         */
        public static boolean LogOut() {
            WebDriver driver = DriverSetup.getDriver();

            try {
                // This should leave us in the user cabinet
                LoginModule.SuccessfulLogin();

                driver.findElement(By.xpath("//div[text()='Sign Out']")).click();

                try {
                    WebDriverWait Wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                    Wait.until(ExpectedConditions.urlToBe("https://qa-course-01.andersenlab.com/login"));
                    return true; // if redirect was successful - consider test passed
                } catch (TimeoutException e) {
                    // unexpected, sign out should redirect us to Sign In page
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getClass() + "   " + e.getMessage());
                System.out.println("/////////////////////// Unexpected error during logout attempt! ///////////////////////");
                return false;
            }
        }
    }
}
