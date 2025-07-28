package lesson_18;

import java.util.Random;

public class Utilities {
    /**
     * @return Randomly generated email
     */
    public static String getValidEmail() {
        return getRandomEmail();
    }

    /**
     * @return Randomly generated email that is NOT equals to specified email
     */
    public static String getInvalidEmail(String email) {
        String invalidEmail = getRandomEmail();
        while (invalidEmail.equals(email)) {
            invalidEmail = getRandomEmail(); // just in case randomizer returned the same email, reroll until it isn't
        }
        return invalidEmail;
    }

    private static String getRandomEmail() {
        Random rand = new Random();
        return "aqastud" + (rand.nextInt(9000) + 999)
                + "@mail" + (rand.nextInt(9000) + 999) + ".com";
    }
}
