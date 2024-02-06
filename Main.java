import swiftbot.SwiftBotAPI;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Main {
	
	static ConsoleUtils utils = new ConsoleUtils();
	static Square square = new Square();
	static Triangle triangle = new Triangle();
	static SwiftBotAPI sb = new SwiftBotAPI();
	static Scanner getinp = new Scanner(System.in);

	static ArrayList<String> Drawn_Shape = new ArrayList<>();
	static ArrayList<Object> Largest_Shape = new ArrayList<>();
	static int Square_counter = 0;
	static int Triangle_counter = 0;
	static ArrayList<Double> Avg_time = new ArrayList<>();
	static String Last_Drawn = "S 40";
	static boolean UserPlay = true;

	// IMPLEMENT NEW CLASS THAT INITIATE.
	public static void main(String[] args) {
		String Decoded_Text = "";
		StartInitialGame();

		while (UserPlay) {

			int inp = displayMenuAndGetUserInput();
			if (inp == 1) {
				Decoded_Text = getQRCode();
				System.out.println("Decoded Text : " + Decoded_Text);
			} else if (inp == 2) {
				Decoded_Text = Last_Drawn;
			}
			String[] input = Decoded_Text.split(" ");

			if (input[0].equalsIgnoreCase("S")) {
				boolean isSValid = square.Validate(input);
				if (isSValid == false) {
					System.out.println("Exiting in 2 sec");
					utils.waitFor2s();
					System.exit(0);
				}
				int lenght = Integer.decode(input[1]);
				MakeSquare(lenght);

				UserPlay = AskUserContinue();

				if (UserPlay == false) {
					ResultFileWriter.writeToFile("2362377-DrawShape-log.txt", Drawn_Shape, Largest_Shape, Square_counter, Triangle_counter, Avg_time);
					System.out.println("Exiting in 2 sec");
					utils.waitFor2s();
					System.exit(0);
				}

			} else if (input[0].equalsIgnoreCase("T")) {
				boolean isTValid = triangle.Validate(input);

				if (isTValid == false) {
					System.out.println("Exiting in 2 Sec");
					utils.waitFor2s();
					System.exit(0);
				}

				int side1 = Integer.decode(input[1]);
				int side2 = Integer.decode(input[2]);
				int side3 = Integer.decode(input[3]);

				MakeTriangle(side1, side2, side3);
				UserPlay = AskUserContinue();

				if (UserPlay == false) {
					ResultFileWriter.writeToFile("2362377-DrawShape-log.txt", Drawn_Shape, Largest_Shape, Square_counter, Triangle_counter, Avg_time);
					System.out.println("Exiting in 2 sec");
					utils.waitFor2s();
					System.exit(0);
				}
			} else {
				utils.ShowError("Unkown Shape in QR Code.");
				System.exit(0);
				
			}
		}

	}

	// DONE - NO CHANGES
	private static boolean AskUserContinue() {
		System.out.println("Press A: To Continue");
		System.out.println("Press X: To Exit");
		
		int inp = utils.getButtonInput("Buttons:", 'A', 'X', sb);
		if(inp == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	// DONE NOW NO CHANGES 
	private static int displayMenuAndGetUserInput() {
		utils.clearScreen();

		utils.ShowMainHeading();

		System.out.println("\n Note: \n If you want to make Square, Give \" S Length \" in QR CODE \n If you want to make triangle Give \" T side1 side2 side3 \"  in QR CODE");
		System.out.println("\nPress A: Scan QR Code");
		System.out.println("Press X: Make Last Drawn Shape ( Cannot Run when you havent scanned any QR code before )");
		int inp = utils.getButtonInput("Button: ", 'A','X',sb);
	

		if (inp == 2) {
			if (Last_Drawn.length() == 0 || Last_Drawn == null) {
				utils.ShowError("No Last Drawn Shape Found! Make sure you have scanned QR code before.");
				return displayMenuAndGetUserInput();
			} else {
				return inp;
			}
		}else{
			return 1;
		}
		
	}

	// CHANGE NAME AND DONE 
	private static void StartInitialGame() {
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
			StartInitialGame();
		} else if (StartInp == 2) {
			System.out.println("SEE YA");
			System.exit(0);
		} else if (StartInp == 1) {
			return;
		}
	}

	// CHECK IF ITS WORK OR IMPLEMENT YOUR OWN 
	public static String getQRCode() {
		System.out.println("Press A or X:  When Ready to Scan QR Code");
		int inp = utils.getButtonInput("Button:", 'A', 'X', sb);
		System.out.println("Scanning ...");
		BufferedImage img = sb.getQRImage();
		try {
			String decodedText = sb.decodeQRImage(img);
			if (!decodedText.isEmpty()) {
				System.out.println("Play SUCCESS SOUND");
				System.out.println(decodedText);
				return decodedText;
			} else {
				System.out.println("Play Failed SOUND");
				utils.ShowError("Could'nt Scan QR Code Please Try again");
				return getQRCode();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Play Failed SOUND");
			utils.ShowError("Could'nt Scan QR Code Internal Error");
			return getQRCode();
		}
	}
	// Fully Done - NO CHANGES NOW
	public static void MakeSquare(int lenght) {

		utils.clearScreen();
		utils.ShowMainHeading();
		
		System.out.println("\n Making Square");
		double area = lenght * lenght;

		
		long startTime = System.currentTimeMillis();
		square.Draw(lenght , sb);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        Avg_time.add((double) elapsedTime);
		// Adding Shape to arrays to write in file later
		Drawn_Shape.add("Square " + lenght);
		Square_counter += 1;
		Last_Drawn = "S " + lenght;
		if (Largest_Shape.isEmpty()) {
			Largest_Shape.add(0, area);
			Largest_Shape.add(1, "Square");
		}else {
			if ((double) Largest_Shape.get(0) <= area) {
				Largest_Shape.set(0, area);
				Largest_Shape.set(1, "Square");
			}	
		}
		utils.waitFor2s();
	}
	// Done Fully - NO CHANGES NOW
	private static void MakeTriangle(int side1, int side2, int side3) {

		utils.clearScreen();
		utils.ShowMainHeading();
		
		System.out.println("\n Calculating angles For TRIANGLE RAHHHHHHHH");
		
		double[] angles = triangle.CalculateAngles(side1 , side2 , side3);

		double angle1 = angles[0];
		double angle2 = angles[1];
		double angle3 = angles[2];
		double area = triangle.CalculateArea(side1, side2, side3);

		long startTime = System.currentTimeMillis();
		triangle.Draw(side1,side2,side3,angles , sb);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        Avg_time.add((double)elapsedTime);
		// Adding Shape to arrays to write in file later
		Drawn_Shape.add("Trinagle " + side1 + " " + side2 + " " + side3 + " " + "( Angles : " + angle1 + " " + angle2
				+ " " + angle3 + " )");
		Triangle_counter += 1;
		Last_Drawn = "T " + side1 + " " + side2 + " " + side3;
		if(Largest_Shape.isEmpty()) {
			Largest_Shape.add(0, area);
			Largest_Shape.add(1, "Triangle");
		}else {
			if ((double) Largest_Shape.get(0) <= area) {
				Largest_Shape.set(0, area);
				Largest_Shape.set(1, "Triangle");
			}	
		}
		utils.waitFor2s();
	}

}
