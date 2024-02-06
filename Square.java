import swiftbot.SwiftBotAPI;

public class Square {

	private ConsoleUtils utils = new ConsoleUtils();
	
	public boolean Validate(String[] input) {
		if (input.length != 2) {
			utils.ShowError("Please Give input in Format \" S lenght_of_side \" ex. \"S 30\" ");
			return false;
		}
		int lenght = utils.ConvertInt(input[1]);
		if (lenght == -1) {
			utils.ShowError("Please Give Lenght of Side as Integer Only Between 15 - 85 ");
			return false;
		}
		if (lenght < 15 || lenght > 85) {
			utils.ShowError("Please Give lenght only between 15 - 85");
			return false;
		}
		return true;
	}
	
	public void Draw(int lenght , SwiftBotAPI sb){
		final double calcSpeed = 20.88;
    	final int speed = 55;
    	final double time = (lenght / calcSpeed) * 1000 ;
    	final int turningTime = 800;
    	
    	
        for(int i = 0; i < 4; i++ ) {
        	
        	sb.move(speed, speed, (int)time);
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	sb.move(100, 0, turningTime);
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // Record the end time

        

	}
}
