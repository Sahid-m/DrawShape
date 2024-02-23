import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ResultFileWriter class provides a method to write results to a file,
 * including information about drawn shapes, the largest shape, the most
 * frequently drawn shape, and the average time to draw a shape.
 */
public class ResultFileWriter {

    /**
     * Writes the results to a file.
     *
     * @param FileName        The name of the file to write results to.
     * @param DrawnShapes     List of drawn shapes.
     * @param LargestShape    List containing the largest shape's information.
     * @param SquareCounter   Counter for the square shape.
     * @param TriangleCounter Counter for the triangle shape.
     * @param avg_time        List containing the average time to draw each shape.
     */
    public static void writeToFile(String FileName, ArrayList<String> DrawnShapes,
                                   ArrayList<Object> LargestShape, int SquareCounter,
                                   int TriangleCounter, ArrayList<Double> avg_time) {

        // Create an instance of ConsoleUtils.
        ConsoleUtils utils = new ConsoleUtils();
        utils.clearScreen(); // Clear the console screen.
        utils.ShowMainHeading(); // Display the main heading.

        // Create a File object with the specified file name.
        File myFile = new File(FileName);
        System.out.println("\n Writing in File");

        // Check if the file already exists.
        if (myFile.exists()) {
            try {
                // Delete the file if it already exists.
                if (myFile.delete()) {

                } else {
                    utils.ShowError("Cannot Delete File that already exists");
                }
            } catch (SecurityException e) {
                utils.ShowError("Can Not Write File Permission Denied \n Error: " + e.getMessage());
            }
        }

        try {
            // Create a new file if it doesn't exist.
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
                FileWriter File = new FileWriter(FileName);

                // Write information about drawn shapes to the file.
                File.write("Drawn Shapes : ");
                for (int i = 0; i < DrawnShapes.size(); i++) {
                    int no = i + 1;
                    File.write(no + ") " + DrawnShapes.get(i) + " ");
                }

                // Write information about the largest shape to the file.
                File.write(" \nLargest Shape : ");
                String Largest_Shape = String.valueOf(LargestShape.get(1)) + " " + String.valueOf(LargestShape.get(0));
                File.write(Largest_Shape + " ( Area )");

                // Write information about the most frequently drawn shape to the file.
                File.write(" \nMost Frequent Drawn Shape : ");
                if (SquareCounter > TriangleCounter) {
                    File.write("Square " + SquareCounter + " times");
                } else if (TriangleCounter > SquareCounter) {
                    File.write("Triangle " + TriangleCounter + " times");
                } else {
                    File.write("Both Drawn Equal Times i.e. " + SquareCounter + " times");
                }

                // Calculate and write the average time to draw a shape to the file.
                File.write("\nAverage Time to draw shape: ");
                double sum = 0;
                for (int i = 0; i < avg_time.size(); i++) {
                    sum += avg_time.get(i);
                }
                double avgTime = (sum / avg_time.size()) / 1000;
                File.write(String.valueOf(avgTime) + " seconds");

                File.close(); // Close the FileWriter.

                System.out.println("Successfully Wrote to file \n Exiting .... \n Have a Nice Day");
                utils.waitFor2s(); // Wait for 2 seconds.

            } else {
                utils.ShowError("Can Not Write File Permission Denied ");
            }
        } catch (IOException e) {
            utils.ShowError("Can Not Write File Permission Denied \n Error: " + e.getMessage());
        }
    }
}
