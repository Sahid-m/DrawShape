import swiftbot.SwiftBotAPI;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;

public class Main {

	// Initialise all the Classes
	static ConsoleUtils utils = new ConsoleUtils();
	static Square square = new Square();
	static Triangle triangle = new Triangle();
	static SwiftBotAPI sb = new SwiftBotAPI();
	static Scanner getinp = new Scanner(System.in);

	// Initialise all the Data Variables
	static ArrayList<String> Drawn_Shape = new ArrayList<>();
	static ArrayList<Object> Largest_Shape = new ArrayList<>();
	static int Square_counter = 0;
	static int Triangle_counter = 0;
	static ArrayList<Double> Avg_time = new ArrayList<>();
	static String Last_Drawn = "";
	static boolean UserPlay = true;

	public static void main(String[] args) {
		String Decoded_Text = "";

		// Get Starter Input
		DisplayInitialMenu();

		while (UserPlay) {
			// Starter Menu
			int inp = displayMenuAndGetUserInput();
			if (inp == 1) {
				Decoded_Text = getQRCode();
			} else if (inp == 2) {
				Decoded_Text = Last_Drawn;
			}

			String[] input = Decoded_Text.split(" ");

			// Checks if the input's first character is S
			if (input[0].equalsIgnoreCase("S")) {
				// Method To Validate Square Returns true if good, False if Not Valid. Also
				// Displays Errors
				boolean isSValid = square.Validate(input);

				// If Square Format is Not Valid
				if (!isSValid) {
					utils.ShowError(
							"Not Valid Square Format! \nPlease Try Again after giving Text in Format. \"S length\" ");
					continue;
				}

				int length = Integer.decode(input[1]);

				System.out.println("\nPlace Down the SwiftBot! Making Square of Lenght : " + length + " in 2 sec");

				utils.waitFor2s();

				// Method to Make Square
				MakeSquare(length);

				// Asks User if they want to continue or exit
				UserPlay = AskUserContinue();

				// If User exits, Writes the required things in the file as mentioned in
				// requirements
				if (!UserPlay) {
					ResultFileWriter.writeToFile("2362377-DrawShape-log.txt", Drawn_Shape, Largest_Shape,
							Square_counter, Triangle_counter, Avg_time);
					System.out.println("Exiting in 2 sec");
					utils.waitFor2s();
					System.exit(0);
				}

			} else if (input[0].equalsIgnoreCase("T")) {
				// Checks if the input's first character is T
				// Method To Validate Triangle Returns true if good, False if Not Valid
				boolean isTValid = triangle.Validate(input);

				if (!isTValid) {
					utils.ShowError(
							"\nNot Valid Triangle Format! \nPlease Try Again after giving Text in Format. \"T side1 side2 side3\" ");
					utils.waitForTime(1000);
					continue;
				}

				int side1 = Integer.decode(input[1]);
				int side2 = Integer.decode(input[2]);
				int side3 = Integer.decode(input[3]);

				System.out.println("\nPlace Down the SwiftBot! Making Triangle of sides : " + side1 + " " + side2 + " "
						+ side3 + " in 4 sec");

				utils.waitFor2s();
				utils.waitFor2s();

				// Makes Triangle
				MakeTriangle(side1, side2, side3);

				// Asks User if they want to continue or exit
				UserPlay = AskUserContinue();

				// If User exits, Writes the required things in the file as mentioned in
				// requirements
				if (!UserPlay) {
					ResultFileWriter.writeToFile("2362377-DrawShape-log.txt", Drawn_Shape, Largest_Shape,
							Square_counter, Triangle_counter, Avg_time);
					System.out.println("Exiting in 2 sec");
					utils.waitFor2s();
					System.exit(0);
				}
			} else {
				utils.ShowError("\nUnknown Shape in QR Code.");
				utils.ShowError("\nPlease Try Again");
				continue;
			}
		}
	}

	/**
	 * Asks the user to continue or exit the program.
	 *
	 * @return True if the user wants to continue, false if they want to exit.
	 */
	private static boolean AskUserContinue() {
		System.out.println("Press A: To Continue");
		System.out.println("Press X: To Exit");

		int inp = utils.getButtonInput("Buttons:", 'A', 'X', sb);
		return inp == 1;
	}

	/**
	 * Displays the menu that asks the user what they want to do.
	 *
	 * @return The user's choice based on the menu.
	 */
	private static int displayMenuAndGetUserInput() {
		utils.clearScreen();
		utils.ShowMainHeading();

		System.out.println(
				"\n Note: \n If you want to make Square, Give \" S Length \" in QR CODE \n If you want to make triangle Give \" T side1 side2 side3 \"  in QR CODE");
		System.out.println("\nPress A: Scan QR Code");
		System.out.println("Press X: Make Last Drawn Shape ( Cannot Run when you haven't scanned any QR code before )");
		int inp = utils.getButtonInput("Button: ", 'A', 'X', sb);

		if (inp == 2) {
			if (Last_Drawn.length() == 0 || Last_Drawn == null) {
				utils.ShowError("No Last Drawn Shape Found! Make sure you have scanned QR code before.");
				return displayMenuAndGetUserInput();
			} else {
				return inp;
			}
		} else {
			return 1;
		}
	}

	/**
	 * Displays the initial menu when the program starts.
	 */
	private static void DisplayInitialMenu() {
		utils.clearScreen();
		utils.ShowMainHeading();

		System.out.println("\nPress 1: To Start the Draw Shape");
		System.out.println("Press 2: Exit the Program");
		System.out.println("Input: ");
		String scannedString = getinp.nextLine();
		short StartInp = 0;

		try {
			StartInp = Short.valueOf(scannedString);
		} catch (NumberFormatException e) {
			StartInp = -1;
		}

		if (StartInp == -1) {
			utils.ShowError("Please Enter Appropriate Input!");
			DisplayInitialMenu();
		} else if (StartInp == 2) {
			System.out.println("SEE YA");
			System.exit(0);
		} else if (StartInp == 1) {
			return;
		}
	}

	/**
	 * Gets the QR code from the user.
	 *
	 * @return The decoded text from the scanned QR code.
	 */
	public static String getQRCode() {
		System.out.println("Press A or X: When Ready to Scan QR Code");
		int inp = utils.getButtonInput("Button:", 'A', 'X', sb);
		System.out.println("Scanning ...");
		BufferedImage img = sb.getQRImage();
		try {
			String decodedText = sb.decodeQRImage(img);
			if (!decodedText.isEmpty()) {
				utils.ShowUnderLightsForGivensec('G',2,sb);
				return decodedText;
			} else {
				utils.ShowUnderLightsForGivensec('R',1,sb);
				utils.ShowError("Couldn't Scan QR Code Please Try again");
				return getQRCode();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Play Failed SOUND");
			utils.ShowError("Couldn't Scan QR Code Internal Error");
			return getQRCode();
		}
	}

	/**
	 * Makes a square and adds data to data variables.
	 *
	 * @param length The length of the square's side.
	 */
	public static void MakeSquare(int length) {
		utils.clearScreen();
		utils.ShowMainHeading();

		System.out.println("\n Making Square");
		double area = length * length;

		long startTime = System.currentTimeMillis();
		square.Draw(length, sb);
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		
		utils.ShowUnderLightsForGivensec('G', 1, sb);

		Avg_time.add((double) elapsedTime);
		// Adding Shape to arrays to write in the file later
		Drawn_Shape.add("Square " + length);
		Square_counter += 1;
		Last_Drawn = "S " + length;
		if (Largest_Shape.isEmpty()) {
			Largest_Shape.add(0, area);
			Largest_Shape.add(1, "Square");
		} else {
			if ((double) Largest_Shape.get(0) <= area) {
				Largest_Shape.set(0, area);
				Largest_Shape.set(1, "Square");
			}
		}
		utils.waitFor2s();
	}

	/**
	 * Makes a triangle and adds data to data variables.
	 *
	 * @param side1 The length of the first side of the triangle.
	 * @param side2 The length of the second side of the triangle.
	 * @param side3 The length of the third side of the triangle.
	 */
	private static void MakeTriangle(int side1, int side2, int side3) {
		utils.clearScreen();
		utils.ShowMainHeading();

		System.out.println("\n Calculating angles For TRIANGLE RAHHHHHHHH");

		double[] angles = triangle.CalculateAngles(side1, side2, side3);

		double angle1 = angles[0];
		double angle2 = angles[1];
		double angle3 = angles[2];
		double area = triangle.CalculateArea(side1, side2, side3);

		long startTime = System.currentTimeMillis();
		triangle.Draw(side1, side2, side3, angles, sb);
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		
		utils.ShowUnderLightsForGivensec('G', 1, sb);

		Avg_time.add((double) elapsedTime);
		// Adding Shape to arrays to write in the file later
		Drawn_Shape.add(String.format("Triangle %d %d %d ( Angles : %.2f %.2f %.2f )", side1, side2, side3, angle1,
				angle2, angle3));

		Triangle_counter += 1;
		Last_Drawn = "T " + side1 + " " + side2 + " " + side3;
		if (Largest_Shape.isEmpty()) {
			Largest_Shape.add(0, area);
			Largest_Shape.add(1, "Triangle");
		} else {
			if ((double) Largest_Shape.get(0) <= area) {
				Largest_Shape.set(0, area);
				Largest_Shape.set(1, "Triangle");
			}
		}
		utils.waitFor2s();
	}
}
