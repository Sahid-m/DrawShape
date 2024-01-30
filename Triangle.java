
public class Triangle {
	
	ConsoleUtils utils = new ConsoleUtils();
	
	public boolean Validate(String[] input){
		if (input.length != 4) {
			utils.ShowError("Please Only Give input in format \"T side1 side2 side3\"  ");
			return false;
		}

		int side1 = utils.ConvertInt(input[1]);
		int side2 = utils.ConvertInt(input[2]);
		int side3 = utils.ConvertInt(input[3]);

		// Validates If they are integers
		if (side1 == -1 || side2 == -1 || side3 == 3) {
			utils.ShowError("Please Give Lenght of Sides as Integers Only Between 15 - 85 ");
			return false;
		}

		// Validates If they are between 15 - 85
		if (side1 < 15 || side1 > 85) {
			utils.ShowError("Please Give lenght only between 15 - 85");
			return false;
		}
		if (side2 < 15 || side2 > 85) {
			utils.ShowError("Please Give lenght only between 15 - 85");
			return false;
		}
		if (side3 < 15 || side3 > 85) {
			utils.ShowError("Please Give lenght only between 15 - 85");
			return false;
		}

		boolean isTPossible = IsTrianglePossible(side1, side2, side3);

		if (isTPossible == false) {
			utils.ShowError("Triangle With Given Sides is not Possible to make");
			return false;
		}

		return true;
	}
	
	private boolean IsTrianglePossible(int side1, int side2, int side3) {

		if ((side1 + side2 > side3) && (side1 + side3 > side2) && (side2 + side3 > side1)) {
			return true;
		} else {
			return false;
		}
	}
	
	public double CalculateArea(int side1, int side2, int side3) {

		double s = (side1 + side2 + side3) / 2;

		double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));

		return area;
	}
	
	public double[] CalculateAngles(int side1 , int side2 , int side3) {
		
		
		
		double[] angles = {1.2,1.4,1.4};
		
		return angles;
		
	}
	
	public void Draw(int side1 , int side2 , int side3 , double[] angles) {
		System.out.println("Drawing Triangle of lenghts " + side1 + " " + side2 + " " + side3 + " .");
		System.out.println("Drawing With Angles:  " + angles[0] + " " + angles[1] + " " + angles[2] + " .");
		System.out.println("SHOWING GREEN UNDERLIGHTS");
	}
}
