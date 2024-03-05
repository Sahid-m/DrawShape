import swiftbot.SwiftBotAPI;

/**
 * The Triangle class represents a triangle shape and provides methods for
 * validating input, calculating area, angles, and drawing a triangle using
 * the SwiftBotAPI.
 */
public class Triangle {

    // An instance of ConsoleUtils
    ConsoleUtils utils = new ConsoleUtils();

    /**
     * Validates the input for creating a triangle.
     *
     * @param input An array containing input parameters.
     * @return True if the input is valid, false otherwise.
     */
    public boolean Validate(String[] input) {
        if (input.length != 4) {
            utils.ShowError("Please Only Give input in format \"T side1 side2 side3\"  ");
            utils.waitForTime(1000);
            return false;
        }

        // Convert input sides to integers.
        int side1 = utils.ConvertInt(input[1]);
        int side2 = utils.ConvertInt(input[2]);
        int side3 = utils.ConvertInt(input[3]);

        // Validates if sides are integers within the range of 15 - 85.
        if (side1 == -1 || side2 == -1 || side3 == -1) {
            utils.ShowError("Please Give Length of Sides as Integers Only Between 15 - 85 ");
            utils.waitForTime(1000);
            return false;
        }

        // Validates if sides are between 15 - 85.
        if (side1 < 15 || side1 > 85 || side2 < 15 || side2 > 85 || side3 < 15 || side3 > 85) {
            utils.ShowError("Please Give length only between 15 - 85");
            utils.waitForTime(1000);
            return false;
        }

        // Checks if a triangle with the given sides is possible.
        boolean isTPossible = IsTrianglePossible(side1, side2, side3);

        if (!isTPossible) {
            utils.ShowError("Triangle With Given Sides is not Possible to make");
            utils.waitForTime(1000);
            return false;
        }

        return true;
    }

    /**
     * Checks if a triangle is possible with the given sides.
     *
     * @param side1 Length of the first side.
     * @param side2 Length of the second side.
     * @param side3 Length of the third side.
     * @return True if a triangle is possible, false otherwise.
     */
    private boolean IsTrianglePossible(int side1, int side2, int side3) {
        return (side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1);
    }

    /**
     * Calculates the area of the triangle using Heron's formula.
     *
     * @param side1 Length of the first side.
     * @param side2 Length of the second side.
     * @param side3 Length of the third side.
     * @return The calculated area of the triangle.
     */
    public double CalculateArea(int side1, int side2, int side3) {
        double s = (side1 + side2 + side3) / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }

    /**
     * Calculates the angles of the triangle using the law of cosines.
     *
     * @param side1 Length of the first side.
     * @param side2 Length of the second side.
     * @param side3 Length of the third side.
     * @return An array containing the calculated angles of the triangle.
     */
    public double[] CalculateAngles(int side1, int side2, int side3) {
        double angle1 = Math.toDegrees(Math.acos((side2 * side2 + side3 * side3 - side1 * side1) / (2.0 * side2 * side3)));
        double angle2 = Math.toDegrees(Math.acos((side1 * side1 + side3 * side3 - side2 * side2) / (2.0 * side1 * side3)));
        double angle3 = Math.toDegrees(Math.acos((side1 * side1 + side2 * side2 - side3 * side3) / (2.0 * side1 * side2)));

        // Store angles in an array.
        double[] angles = { angle1, angle2, angle3 };

        return angles;
    }

    /**
     * Draws a triangle using the SwiftBotAPI.
     *
     * @param side1  Length of the first side.
     * @param side2  Length of the second side.
     * @param side3  Length of the third side.
     * @param angles An array containing the angles of the triangle.
     * @param sb     An instance of SwiftBotAPI for moving SwiftBot.
     */
    public void Draw(int side1, int side2, int side3, double[] angles, SwiftBotAPI sb) {
        double angle1 = angles[0];
        double angle2 = angles[1];
        double angle3 = angles[2];
        
        final int speed = 55;
    	final int turnSpeed = 100;
    	double time1 = TimeForSide(side1);
    	
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
    	
    	
    	sb.move(speed, speed, (int)time1);
    	waitForWheels();
    	
    	double timeForAngle1 = getTimeForAngle(180 - angle1);
    	sb.move(turnSpeed, 0, (int)timeForAngle1 );
    	waitForWheels();
    	
    	double time2 = TimeForSide(side2);
    	sb.move(speed, speed, (int)time2);
    	waitForWheels();
    	
    	double timeForAngle2 = getTimeForAngle(180 - angle2);
    	sb.move(turnSpeed,0, (int)timeForAngle2 );
    	waitForWheels();
    	
    	double time3 = TimeForSide(side3);
    	sb.move(speed, speed, (int)time3);
    	waitForWheels();
    	
    	double timeForAngle3 = getTimeForAngle( 180 - angle3);
    	sb.move(turnSpeed, 0, (int)timeForAngle3 );
    	waitForWheels();
    	
    	randomUnderlights.interrupt();
    	sb.disableUnderlights();
    }
    
    private double getTimeForAngle(double angle) {
    	int timeFor360 = 3220;
    	double time = (angle * timeFor360)/360;
    	return time;
    }
    
    private double TimeForSide(int side) {
    	final double calcSpeed = 20.88;
    	final double time = (side / calcSpeed) * 1000;
    	return time;
    }
    
    private void waitForWheels() {
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
