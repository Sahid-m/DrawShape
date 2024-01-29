import swiftbot.SwiftBotAPI;

public class DrawSquare {
	static SwiftBotAPI sb = new SwiftBotAPI();
	
	
	public static void main(String[] args) {
		
		try {
			DrawSquare1(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void DrawSquare1(int lenght) throws InterruptedException {
		
		int velocity = 50;
		int swiftbotSpeed = 21;
		int timeside = (lenght * 1000) / swiftbotSpeed;
		
		
		for(int i = 0; i < 4; i++) {
			
			
			
			sb.move(velocity, velocity, timeside);
			Thread.sleep(500);
			
			sb.move(50, 0, 500);
			Thread.sleep(500);
		}
	}
}
