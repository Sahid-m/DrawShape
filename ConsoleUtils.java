import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import swiftbot.Button;
import swiftbot.SwiftBotAPI;

/**
 * The ConsoleUtils class provides utility methods for console-based operations,
 * such as displaying main headings, showing errors, waiting for a specified
 * duration, clearing the console screen, and getting button input from the
 * user.
 */
public class ConsoleUtils {

    // COLORS FOR ERRORS
    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[0;32m";
    private static final String BLUE = "\033[0;34m";
    private static final String RED = "\033[0;31m";

    /**
     * Converts a string to an integer.
     *
     * @param num The string to be converted.
     * @return The converted integer, or -1 if the conversion fails.
     */
    public int ConvertInt(String num) {
        try {
            int number = Integer.decode(num);
            return number;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Method to get button input from the user.
     *
     * @param Question The question or prompt to be displayed to the user.
     * @param Button1  The button that returns 1 when pressed.
     * @param Button2  The button that returns 2 when pressed.
     * @param sb       The SwiftBotAPI instance.
     * @return The button value (1 or 2) pressed by the user.
     */
    public int getButtonInput(String Question, char Button1, char Button2, SwiftBotAPI sb) {
        final int TIMEOUT_IN_MILLI_SEC = 5_000;
        sb.disableAllButtons();
        System.out.println(Question);
        Button B1 = getButton(Button1);
        Button B2 = getButton(Button2);
        int[] rint = { -1 };
        CountDownLatch latch = new CountDownLatch(1);

        if (B1 == null || B2 == null) {
            throw new IllegalArgumentException("NO BUTTON WITH LETTER : " + Button1 + " or " + Button2);
        }

        sb.enableButton(B1, () -> {
            System.out.println("Button " + Button1 + " Pressed!");
            sb.disableAllButtons();
            rint[0] = 1;
            latch.countDown();
        });
        sb.enableButton(B2, () -> {
            System.out.println("Button " + Button2 + " Pressed!");
            sb.disableAllButtons();
            rint[0] = 2;
            latch.countDown();
        });

        try {
            // Wait for a button press or timeout
            if (!latch.await(TIMEOUT_IN_MILLI_SEC, TimeUnit.MILLISECONDS)) {
                ShowError("Timeout: Please Press the Right Button.");
                sb.disableAllButtons();
                return getButtonInput(Question, Button1, Button2, sb);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rint[0];
    }

    /**
     * Gets the corresponding SwiftBotAPI Button object based on the given letter.
     *
     * @param ButtonLetter The letter representing the button.
     * @return The corresponding Button object.
     */
    private Button getButton(char ButtonLetter) {
        switch (ButtonLetter) {
            case 'A':
                return Button.A;
            case 'B':
                return Button.B;
            case 'X':
                return Button.X;
            case 'Y':
                return Button.Y;
            default:
                return null;
        }
    }

    /**
     * Displays under lights for a given duration based on the specified color.
     *
     * @param C  The character representing the color ('R' or 'G').
     * @param time The duration to display under lights in seconds.
     * @param sb  The SwiftBotAPI instance.
     */
    public void ShowUnderLightsForGivensec(char C, int time, SwiftBotAPI sb) {

        int[] redColor = { 255, 0, 0 };
        int[] greenColor = { 0, 0, 255 };

        if (C == 'R' || C == 'r') {
            sb.fillUnderlights(redColor);
        } else if (C == 'G' || C == 'g') {
            sb.fillUnderlights(greenColor);
        } else {
            throw new IllegalArgumentException("No Lights Defined For Given Character");
        }

        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sb.disableUnderlights();
    }

    /**
     * Generates a random number within the specified range.
     *
     * @param min The minimum value for the random number.
     * @param max The maximum value for the random number.
     * @return The randomly generated number.
     */
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * Displays random under lights based on the provided number.
     *
     * @param no The number indicating the color of under lights.
     * @param sb The SwiftBotAPI instance.
     */
    public void ShowRandomUnderlights(int no, SwiftBotAPI sb) {
        // Colors
        int[] green = { 0, 0, 255 };
        int[] red = { 255, 0, 0 };
        int[] blue = { 0, 255, 0 };
        int[] yellow = { 255, 0, 255 };
        int[] white = { 255, 255, 255 };

        // Starts the under lights of Swiftbot corresponding to the integer given to the
        // function
        if (no == 1) {
            sb.fillUnderlights(blue);

        } else if (no == 2) {
            sb.fillUnderlights(green);

        } else if (no == 3) {
            sb.fillUnderlights(red);

        } else if (no == 4) {
            sb.fillUnderlights(yellow);
        } else if (no == 5) {
            sb.fillUnderlights(white);
        }
    }

    /**
     * Displays the main heading in the console.
     */
    public void ShowMainHeading() {
        System.out.println(GREEN + "###############################" + RESET);
        System.out.println("    Draw Shape      ");
        System.out.println(GREEN + "###############################" + RESET);
    }

    /**
     * Displays an error message in the console.
     *
     * @param err The error message to be displayed.
     */
    public void ShowError(String err) {
        System.out.println(BLUE + "Error: " + RED + err + RESET);
        waitFor2s();
    }

    /**
     * Pauses the program for 2 seconds.
     */
    public void waitFor2s() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Internal thread Error Please Try to run Program again");
            System.exit(0);
        }
    }

    /**
     * Pauses the program for the specified time.
     *
     * @param time The duration to wait in milliseconds.
     */
    public void waitForTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Internal thread Error Please Try to run Program again");
            System.exit(0);
        }
    }

    /**
     * Clears the console screen.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
