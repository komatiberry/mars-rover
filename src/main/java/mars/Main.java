package mars;

import java.util.Objects;

import mars.rover.RoverManager;

public class Main {
	public static void main(String[] args) {
		if (args.length <= 0 || Objects.isNull(args[0])) {
			System.out.println("No Mars Rover instruction file.");
		} else {
			RoverManager roverManager = new RoverManager();
			roverManager.processInstructionFile(args[0]);
		}
	}	
}