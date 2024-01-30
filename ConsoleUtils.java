import java.util.Scanner;
import swiftbot.Button;
import swiftbot.SwiftBotAPI;

public class ConsoleUtils {
	// METHODS IN THIS CLASS : ShowMainHeading , ShowError , waitFor2s , clearScreen
	// , ConvertInt , GetInput

	// COLORS FOR ERRORS
	private static final String RESET = "\033[0m";
	private static final String GREEN = "\033[0;32m";
	private static final String BLUE = "\033[0;34m";
	private static final String RED = "\033[0;31m";

	public int ConvertInt(String num) {
		try {
			int number = Integer.decode(num);
			return number;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public int getButtonInput(String Question, char Button1, char Button2 , SwiftBotAPI sb) {

		final int TIMEOUT_IN_MILLI_SEC = 5_000;
		sb.disableAllButtons();
		long EndTime = System.currentTimeMillis() + TIMEOUT_IN_MILLI_SEC;
		System.out.println(Question);
		Button B1 = getButton(Button1);
		Button B2 = getButton(Button2);
		int[] rint = {-1};


		if (B1 == null || B2 == null) {
			throw new IllegalArgumentException("NO BUTTON WITH LETTER : " + Button1 + " or " + Button2);
		}

		sb.enableButton(B1, () -> {
			System.out.println("Button " + Button1 +  " Pressed!");
			sb.disableAllButtons();
			rint[0] = 1;
		});
		sb.enableButton(B2, () -> {
			System.out.println("Button " + Button2 +  " Pressed!");
			sb.disableAllButtons();
			rint[0] = 2;
		});
		
		while (rint[0] == -1 && System.currentTimeMillis() < EndTime) {
	        ;// Wait for a button press or timeout
	    }
		
		
		if(rint[0] == -1) {
			
			ShowError("Please Press the Right Button.");
			sb.disableAllButtons();
			return getButtonInput(Question, Button1 , Button2 , sb);
		}

		return rint[0];
	}
	
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


	public void ShowMainHeading() {
		System.out.println(GREEN + "###############################" + RESET);
		System.out.println("	Draw Shape		");
		System.out.println(GREEN + "###############################" + RESET);
	}

	public void ShowError(String err) {
		System.out.println(BLUE + "Error: " + RED + err + RESET);
		waitFor2s();
	}

	public void waitFor2s() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Internal thread Error Please Try to run Program again");
		}
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
