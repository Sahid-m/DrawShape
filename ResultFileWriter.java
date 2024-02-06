import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ResultFileWriter {
	public static void writeToFile(String FileName, ArrayList<String> DrawnShapes, ArrayList<Object> LargestShape,
			int SquareCounter, int TriangleCounter, ArrayList<Double> avg_time) {
		
		ConsoleUtils utils = new ConsoleUtils();
		utils.clearScreen();
		utils.ShowMainHeading();
		File myFile = new File(FileName);
		System.out.println("\n Writing in File");

		if (myFile.exists()) {
			try {
				if (myFile.delete()) {

				} else {
					utils.ShowError("Cannot Delete File that alredy exists");
				}
			} catch (SecurityException e) {
				utils.ShowError("Can Not Write File Permission Denied \n Error: " + e.getMessage());
			}
		}

		try {
			if (myFile.createNewFile()) {
				System.out.println("File created: " + myFile.getName());
				FileWriter File = new FileWriter(FileName);

				// ALL DRAWN SHAPES
				File.write("Drawn Shapes : ");
				for (int i = 0; i < DrawnShapes.size(); i++) {
					int no = i + 1;
					File.write(no + ") " + DrawnShapes.get(i) + " ");
				}

				// LARGEST SHAPE
				File.write(" \nLargest Shape : ");
				String Largest_Shape = String.valueOf(LargestShape.get(1)) + " " + String.valueOf(LargestShape.get(0));
				File.write(Largest_Shape + " ( Area )");

				File.write(" \nMost Frequent Drawn Shape : ");
				if (SquareCounter > TriangleCounter) {
					File.write("Square " + SquareCounter + " times");
				} else if (TriangleCounter > SquareCounter) {
					File.write("Triangle " + TriangleCounter + " times");
				} else {
					File.write("Both Drawn Equal Times i.e. " + SquareCounter + " times");
				}
				
				// Average Time 
				File.write("\nAverage Time to draw shape: ");
				double sum = 0;
				for(int i = 0; i < avg_time.size(); i++) {
					sum += avg_time.get(i);
				}
				double avgTime = (sum / avg_time.size()) / 1000;
				
				File.write(String.valueOf(avgTime) + " seconds");

				File.close();

				System.out.println("Successfully Wrote to file \n Exiting .... \n Have a Nice Day");
				utils.waitFor2s();

			} else {
				utils.ShowError("Can Not Write File Permission Denied ");
			}
		} catch (IOException e) {
			utils.ShowError("Can Not Write File Permission Denied \n Error: " + e.getMessage());
		}
	}
}
