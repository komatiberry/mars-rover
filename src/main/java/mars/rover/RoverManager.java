package mars.rover;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RoverManager {

	public void processInstructionFile(String filename) {
		//e.g.:	"src/test/resources/TestInput.txt";
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename));
			processInstructions(lines);
		} catch (IOException ioe) {
			System.out.println("Invalid Rover Instructions File : " + ioe);
		}
	}
	
	private void processInstructions(List<String> instructionLines) {
		int lineIndex = 0;
		Rover currentRover = null;
		String maxY = "0";
		String maxX = "0";
		
		//the instruction ingestion below is a bit 'hacky' but simple and effective 
		for (String line : instructionLines) {		
			//per spec: 1st line is the upper-right coordinates of the plateau
			if (lineIndex == 0) {
				String[] grid = line.split(" ");
				maxX = grid[0];
				maxY = grid[1];
			} else {
				//per spec: 
				// every even numbered line (after zero) is a series of instructions telling the rover how to explore the plateau
				if (lineIndex % 2 == 0) {
					try {
						String result = currentRover.processActionSequence(line);
						System.out.println(result);
					} catch (RoverException re) {
						System.out.println("Invalid Rover Instructions : " + re);
					}		
				} else {
					//per spec: every odd numbered line is the rover's initial position
					String[] coOrdinates = line.split(" ");
					try {
						currentRover = new Rover(coOrdinates[0], coOrdinates[1], coOrdinates[2], maxX, maxY);
					} catch (IllegalArgumentException iae) {
						System.out.println("Invalid Rover Creation Instructions : " + iae);
					}
				}				
			}
			lineIndex++;
		}
	}
}