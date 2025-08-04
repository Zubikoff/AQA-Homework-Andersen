package lesson_20.step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lesson_20.pages.LoginPage;
import lesson_20.utility.SetupDriver;

public class LogIn {
    LoginPage page;

    @Given("I am on login page")
    public void i_am_on_login_page() {
        page = new LoginPage(SetupDriver.getDriver());
        page.openLoginPage();
    }

    @When("I set email {string}")
    public void i_set_email(String string) {
        page.setEmail(string);
    }

    @When("I set password {string}")
    public void i_set_password(String string) {
        page.setPassword(string);
    }

    @When("I click Sign in button")
    public void i_click_sign_in_button() {
        page.clickOnSignInButton();
    }

    @Then("I see user cabinet page")
    public void i_see_user_cabinet_page() {
        page.verifyLogInSuccessful();
    }

    @Then("I see the email {string} I have set on previous page")
    public void i_see_the_email_i_have_set_on_previous_page(String string) {
        page.verifyExpectedTextOnPage(string)
                .exitPage();
    }

    @When("I set email {string} with more then one @ sign")
    public void iSetEmailWithMoreThenOneSign(String arg0) {
        page.setEmail(arg0);
    }

    @Then("I see the error message {string}")
    public void iSeeTheErrorMessage(String arg0) {
        page.verifyExpectedTextOnPage(arg0);
    }

    @And("I see the Sign In button is disabled")
    public void iSeeTheSignInButtonIsDisabled() {
        page.verifySignInButtonIsDisabled()
                .exitPage();
    }

    @When("I set email {string} containing Russian alphabet")
    public void iSetEmailContainingRussianAlphabet(String arg0) {
        page.setEmail(arg0);
    }
}
