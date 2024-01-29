import swiftbot.SwiftBotAPI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Main {
	
	
	// Try to break Down Square and Triangle methods into Classes with all their methods
	// in Square class -> DrawSquare , ValidateSquare ,
	// Make a Class with general methods like ShowMainHeading , AskUserContinue, ShowError , clearScreen , waitFor2s , ConvertInt , writeInFile
	static final String RESET = "\033[0m";
	static final String GREEN = "\033[0;32m";
	static final String BLUE = "\033[0;34m";
	static final String RED = "\033[0;31m";

	static SwiftBotAPI sb = new SwiftBotAPI();
	static Scanner getinp = new Scanner(System.in);

	static ArrayList<String> Drawn_Shape = new ArrayList<>();
	static ArrayList<Object> Largest_Shape = new ArrayList<>();
	static int Square_counter = 0;
	static int Triangle_counter = 0;
	static ArrayList<String> Avg_time = new ArrayList<>();
	static String Last_Drawn = "T 20 23 25";
	static boolean UserPlay = true;

	public static void main(String[] args) {
		String Decoded_Text = "";
		StartInitialGame();

		while (UserPlay) {

			short inp = getInp();
			if (inp == 1) {
				Decoded_Text = getQRCode();
				System.out.println("Decoded Text : " + Decoded_Text);
			} else if (inp == 5) {
				Decoded_Text = Last_Drawn;
			}
			String[] input = Decoded_Text.split(" ");

			if (input[0].equalsIgnoreCase("S")) {
				boolean isSValid = Validate_Square(input);
				if (isSValid == false) {
					System.out.println("Exiting in 2 sec");
					waitFor2s();
					System.exit(0);
				}
				int lenght = Integer.decode(input[1]);
				DrawSquare(lenght);

				UserPlay = AskUserContinue();

				if (UserPlay == false) {
					WriteInFile();
					System.out.println("Exiting in 2 sec");
					waitFor2s();
					System.exit(0);
				}

			} else if (input[0].equalsIgnoreCase("T")) {
				boolean isTValid = Validate_Triangle(input);

				if (isTValid == false) {
					System.out.println("Exiting in 2 Sec");
					waitFor2s();
					System.exit(0);
				}

				int side1 = Integer.decode(input[1]);
				int side2 = Integer.decode(input[2]);
				int side3 = Integer.decode(input[3]);

				DrawTriangle(side1, side2, side3);
				UserPlay = AskUserContinue();

				if (UserPlay == false) {
					WriteInFile();
					System.out.println("Exiting in 2 sec");
					waitFor2s();
					System.exit(0);
				}
			} else {
				ShowError("Unkown Shape in QR Code.");
				System.exit(0);
				
			}
		}

	}

	private static void WriteInFile() {
		ShowMainHeading();
		String FileName = "Draw-2362377.txt";
		File myFile = new File(FileName);
		System.out.println("\n Writing in File");
		
		if(myFile.exists()) {
			try{
				if(myFile.delete()) {
					
				}else {
					ShowError("Cannot Delete File that alredy exists");
				}
			}
			catch(SecurityException e) {
				ShowError("Can Not Write File Permission Denied \n Error: " + e.getMessage());
			}
		}
		
		try {
			if (myFile.createNewFile()) {
		        System.out.println("File created: " + myFile.getName());
		        FileWriter File = new FileWriter(FileName);
		        
		        // ALL DRAWN SHAPES
		        File.write("Drawn Shapes : ");
		        for(int i = 0; i < Drawn_Shape.size(); i++) {
		        	File.write( i + ") " + Drawn_Shape.get(i) + " ");
		        }
		        
		        // LARGEST SHAPE
		        File.write(" \nLargest Shape : ");
		        String LargestShape = String.valueOf(Largest_Shape.get(1)) + " " + String.valueOf(Largest_Shape.get(0));
		        File.write(LargestShape);
		        
		        File.write(" \nMost Frequent Drawn Shape : ");
		        if(Square_counter > Triangle_counter) {
		        	File.write("Square " + Square_counter + " times");
		        }
		        else if (Triangle_counter > Square_counter) {
		        	File.write("Square " + Triangle_counter + " times");
		        }
		        else {
		        	File.write("Both Drawn Equal Times i.e. " + Square_counter + " times");
		        }
		        
		        File.close();
		        
		        System.out.println("Successfully Wrote to file \n Exiting .... \n Have a Nice Day");
		        waitFor2s();
		        
		        
		      } else {
		    	ShowError("Can Not Write File Permission Denied ");
		      }
		}catch (IOException e) {
			ShowError("Can Not Write File Permission Denied \n Error: " + e.getMessage());
		}
		

	}

	private static boolean AskUserContinue() {
		System.out.println("Press 1 to continue and 0 to exit");
		String scannedString = getinp.nextLine();
		short StartInp = 0;

		try {
			StartInp = Short.valueOf(scannedString);
		} catch (NumberFormatException e) {
			StartInp = -1;
		}

		if (StartInp == -1) {
			ShowError("Please Enter Appropriate Input!");
			AskUserContinue();
		} else if (StartInp == 1) {
			return true;
		} else if (StartInp == 0) {
			return false;
		}
		return AskUserContinue();
	}

	private static boolean Validate_Triangle(String[] input) {
		if (input.length != 4) {
			ShowError("Please Only Give input in format \"T side1 side2 side3\"  ");
			return false;
		}

		int side1 = ConvertInt(input[1]);
		int side2 = ConvertInt(input[2]);
		int side3 = ConvertInt(input[3]);

		// Validates If they are integers
		if (side1 == -1 || side2 == -1 || side3 == 3) {
			ShowError("Please Give Lenght of Sides as Integers Only Between 15 - 85 ");
			return false;
		}

		// Validates If they are between 15 - 85
		if (side1 < 15 || side1 > 85) {
			ShowError("Please Give lenght only between 15 - 85");
			return false;
		}
		if (side2 < 15 || side2 > 85) {
			ShowError("Please Give lenght only between 15 - 85");
			return false;
		}
		if (side3 < 15 || side3 > 85) {
			ShowError("Please Give lenght only between 15 - 85");
			return false;
		}

		boolean isTPossible = isTrianglePossible(side1, side2, side3);

		if (isTPossible == false) {
			ShowError("Triangle With Given Sides is not Possible to make");
			return false;
		}

		return true;
	}

	private static boolean Validate_Square(String[] input) {
		if (input.length != 2) {
			ShowError("Please Give input in Format \" S lenght_of_side \" ex. \"S 30\" ");
			return false;
		}
		int lenght = ConvertInt(input[1]);
		if (lenght == -1) {
			ShowError("Please Give Lenght of Side as Integer Only Between 15 - 85 ");
			return false;
		}
		if (lenght < 15 || lenght > 85) {
			ShowError("Please Give lenght only between 15 - 85");
			return false;
		}

		return true;
	}

	private static short getInp() {
		clearScreen();

		ShowMainHeading();

		System.out.println(
				"\n Note: \n If you want to make Square, Give \" S Length \" in QR CODE \n If you want to make triangle Give \" T side1 side2 side3 \"  in QR CODE");
		System.out.println("\nPress 1: Scan QR Code");
		System.out.println("Press 5: Make Last Drawn Shape ( Cannot Run when you havent scanned any QR code before )");
		System.out.println("Input: ");
		String scannedString = getinp.nextLine();
		short inp = 0;

		try {
			inp = Short.valueOf(scannedString);
		} catch (NumberFormatException e) {
			inp = -1;
		}

		if (inp == -1) {
			ShowError("Please Enter Appropriate Input!");
			inp = 0;
			return getInp();
		} else if (inp == 5) {
			if (Last_Drawn.length() == 0 || Last_Drawn == null) {
				ShowError("No Last Drawn Shape Found! Make sure you have scanned QR code before.");
				return getInp();
			} else {
				return inp;
			}
		} else if (inp == 1) {
			return 1;
		} else {
			ShowError("Please Enter Appropriate Input!");
			return getInp();
		}
	}

	private static void StartInitialGame() {
		clearScreen();

		ShowMainHeading();

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
			ShowError("Please Enter Appropriate Input!");
			StartInitialGame();
		} else if (StartInp == 2) {
			System.out.println("SEE YA");
			System.exit(0);
		} else if (StartInp == 1) {
			return;
		}
	}

	public static String getQRCode() {
		System.out.println("Press Enter When Ready to Scan QR Code");
		getinp.next();

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
				ShowError("Could'nt Scan QR Code Please Try again");
				return getQRCode();
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Play Failed SOUND");
			ShowError("Could'nt Scan QR Code Internal Error");
			return getQRCode();
		}
	}

	public static int ConvertInt(String num) {
		try {
			int number = Integer.decode(num);
			return number;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static boolean isTrianglePossible(int side1, int side2, int side3) {

		if ((side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1)) {
			return true;
		} else {
			return false;
		}
	}

	public static void DrawSquare(int lenght) {

		double area = lenght * lenght;

		System.out.println("Drawing Square of lenght " + lenght);
		System.out.println("SHOWING GREEN UNDERLIGHTS");

		// Adding Shape to arrays to write in file later
		Drawn_Shape.add("Square " + lenght);
		Square_counter += 1;
		Last_Drawn = "S " + lenght;
		if (Largest_Shape.isEmpty()) {
			Largest_Shape.add(0, lenght);
			Largest_Shape.add(1, "Square");
		}else {
			if ((Double) Largest_Shape.get(0) <= area) {
				Largest_Shape.set(0, area);
				Largest_Shape.set(1, "Square");
			}	
		}

		

		waitFor2s();
	}

	private static void DrawTriangle(int side1, int side2, int side3) {

		System.out.println("Calculating angles For TRIANGLE RAHHHHHHHH");

		double angle1 = 1.2;
		double angle2 = 1.2;
		double angle3 = 1.2;
		double area = CalculateTriangleArea(side1, side2, side3);

		System.out.println("Drawing Triangle of lenghts " + side1 + " " + side2 + " " + side3 + " .");
		System.out.println("SHOWING GREEN UNDERLIGHTS");

		// Adding Shape to arrays to write in file later
		Drawn_Shape.add("Trinagle " + side1 + " " + side2 + " " + side3 + " " + "( Angles : " + angle1 + " " + angle2
				+ " " + angle3 + " )");
		Triangle_counter += 1;
		Last_Drawn = "T " + side1 + " " + side2 + " " + side3;
		if (Largest_Shape.isEmpty()) {
			Largest_Shape.add(0, area);
			Largest_Shape.add(1, "Triangle");
		}else {
			if ((Double) Largest_Shape.get(0) <= area) {
				Largest_Shape.set(0, area);
				Largest_Shape.set(1, "Triangle");
			}	
		}

		

		waitFor2s();
	}

	public static double CalculateTriangleArea(int side1, int side2, int side3) {

		double s = (side1 + side2 + side3) / 2;

		double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

		return area;
	}

	public static void ShowMainHeading() {
		System.out.println(GREEN + "###############################" + RESET);
		System.out.println("	Draw Shape		");
		System.out.println(GREEN + "###############################" + RESET);
	}

	public static void ShowError(String err) {
		System.out.println(BLUE + "Error: " + RED + err + RESET);
		waitFor2s();
	}

	public static void waitFor2s() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Internal thread Error Please Try to run Program again");
		}
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
