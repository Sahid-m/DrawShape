
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
	
	public void Draw(int lenght){
		System.out.println("Drawing Square of lenght " + lenght);
		System.out.println("SHOWING GREEN UNDERLIGHTS");
	}
}
