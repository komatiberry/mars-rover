package mars.rover;

public class Rover {
	private int x;
	private int y;
	private String compassPoint;
	private int maxX;
	private int maxY;
	
	//the following could be moved out to an Enum or a "constants" type class if needed for an expanded application
	// for now : "not sharing is caring".
	private final String NORTH = "N";
	private final String EAST = "E";
	private final String WEST = "W";
	private final String SOUTH = "S";
	private final String MOVE_LEFT = "L";
	private final String MOVE_RIGHT = "R";
	private final String MOVE_FORWARD = "M";
	private final String INVALID = "INVALID";

	public Rover(String initialXPosition, String initialYPosition, String compassPointAbbreviation, String maxX, String maxY) {		
		if (!"NEWS".contains(compassPointAbbreviation)) {
			throw new IllegalArgumentException("Invalid Rover co-ordinates, initial compass point invalid : " + compassPointAbbreviation);
		}
		
		try {
			this.maxX = Integer.parseInt(maxX);
			this.maxY = Integer.parseInt(maxY);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Invalid Grid co-ordinates."); 
		}
		
		try {
			this.compassPoint = compassPointAbbreviation;
			this.x = Integer.parseInt(initialXPosition);
			this.y = Integer.parseInt(initialYPosition);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Invalid Rover co-ordinates."); 
		}
		
		if (x > this.maxX) {
			throw new IllegalArgumentException("Invalid Rover co-ordinates, initial X position out of bounds."); 
		}
		if (y > this.maxY) {
			throw new IllegalArgumentException("Invalid Rover co-ordinates, initial Y position out of bounds."); 
		}
	}
	
	public String processActionSequence(String actions) throws RoverException {
		for (String action : actions.split("")) {
			if (action.equalsIgnoreCase(MOVE_FORWARD)) {
				moveForward();
			} else if (action.equalsIgnoreCase(MOVE_RIGHT) || action.equalsIgnoreCase(MOVE_LEFT)) {
				compassPoint = changeDirection(action);
			} else {
				throw new RoverException("Invalid instruction : " + action);
			}
		}
		return toString();
	}
	
	private void moveForward() throws RoverException {
		switch(compassPoint) {
			case NORTH:
				if ((y + 1) > maxY) {
					throw new RoverException("Out of bounds Y move instruction");
				}
				y++;
				break;
			case EAST:
				if ((x + 1) > maxX) {
					throw new RoverException("Out of bounds X move instruction");
				}
				x++;
				break;
			case SOUTH:
				if ((y - 1) < 0) {
					throw new RoverException("Out of bounds Y move instruction");
				}
				y--;
				break;
			case WEST:
				if ((x - 1) < 0) {
					throw new RoverException("Out of bounds X move instruction");
				}
				x--;
				break;
				
			case INVALID:
				throw new RoverException("Invalid compass point");
			default:
				throw new RoverException("Invalid compass point");
		}
	}
	
	private String changeDirection(String direction) throws RoverException {
		switch (direction) {
			case MOVE_LEFT:
				switch (compassPoint) {
					case NORTH:
						return WEST;
					case EAST:
						return NORTH;
					case WEST:
						return SOUTH;
					case SOUTH:
						return EAST;
					default:
						throw new RoverException("Invalid compass point");
				}
			case MOVE_RIGHT:
				switch (compassPoint) {
					case NORTH:
						return  EAST;
					case EAST:
						return  SOUTH;
					case WEST:
						return  NORTH;
					case SOUTH:
						return  WEST;
					default:
						throw new RoverException("Invalid compass point");
			}
			default:
				throw new RoverException("Invalid direction instruction");
		}
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + compassPoint;
	}
}
