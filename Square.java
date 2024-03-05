import swiftbot.SwiftBotAPI;

/**
 * The Square class represents a square shape and provides methods for
 * validating input and Moves the SwiftBot in Square.
 */
public class Square {

    // An instance of ConsoleUtils.
    private ConsoleUtils utils = new ConsoleUtils();

    /**
     * Validates the input for creating a square.
     *
     * @param input An array containing input parameters.
     * @return True if the input is valid, false otherwise.
     */
    public boolean Validate(String[] input) {
        if (input.length != 2) {
            utils.ShowError("Please Give input in Format \" S length_of_side \" ex. \"S 30\" ");
            utils.waitForTime(1000);
            return false;
        }
        int length = utils.ConvertInt(input[1]);
        if (length == -1) {
            utils.ShowError("Please Give Length of Side as Integer Only Between 15 - 85 ");
            utils.waitForTime(1000);
            return false;
        }
        if (length < 15 || length > 85) {
            utils.ShowError("Please Give length only between 15 - 85");
            utils.waitForTime(1000);
            return false;
        }
        return true;
    }

    /**
     * Draws a square using the SwiftBotAPI.
     *
     * @param length The length of each side of the square.
     * @param sb     An instance of SwiftBotAPI for moving.
     */
    public void Draw(int length, SwiftBotAPI sb) {
        // Constants for calculating movement speed and turning time.
        final double calcSpeed = 20.88;
        final int speed = 55;
        final double time = (length / calcSpeed) * 1000;
        final int turningTime = 800;
        Thread randomUnderlights = new Thread(() -> {
            while (!Thread.interrupted()) {
                int randomNum = utils.getRandomNumberUsingNextInt(1, 5);
                try {
                    utils.ShowRandomUnderlights(randomNum, sb);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Preserve the interrupted status
                    break;  // Break out of the loop if interrupted
                }
            }
        }, "RandomUnderlightsThread");


        randomUnderlights.start();
        
        // Draw the square by moving and turning in a loop.
        for (int i = 0; i < 4; i++) {
            sb.move(speed, speed, (int) time);
            try {
                Thread.sleep(200); // Pause for a short duration between turns for consistent movements.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sb.move(100, 0, turningTime);
            try {
                Thread.sleep(200); // Pause for a short duration between turns for consistent movements.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        randomUnderlights.interrupt();
        
        sb.disableUnderlights();
    }
}
