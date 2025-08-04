Feature: I as user can log in the site

  Background:
    Given I am on login page

  Scenario Outline: Successful log in the system
    When I set email "<email>"
    And I set password "<password>"
    And I click Sign in button
    Then I see user cabinet page
    And I see the email "<email>" I have set on previous page
    Examples:
      | email            | password |
      | aqastud@mail.com | Qwer123$ |

  Scenario Outline: Unsuccessful log in using email with multiple '@' signs
    When I set email "<email>" with more then one @ sign
    And I set password "<password>"
    And I click Sign in button
    Then I see the error message "<message>"
    And I see the Sign In button is disabled
    Examples:
      | email             | password | message               |
      | aqastud@ma@il.com | Qwer123$ | Invalid email address |

  Scenario Outline: Unsuccessful log in using email containing Russian alphabet
    When I set email "<email>" containing Russian alphabet
    And I set password "<password>"
    And I click Sign in button
    Then I see the error message "<message>"
    And I see the Sign In button is disabled
    Examples:
      | email             | password | message               |
      | aqaфtud@ma@il.com | Qwer123$ | Invalid email address |