package battleship.model;

import battleship.view.UserInterface;
/**
 * contains information about a single ship
 */
public class Ship {
	
	
	/**
	 * 0 is battleship
	 * 1 is carrier
	 * 2 is destroyer
	 * 3 is submarine
	 * 4 is frigate
	 */
	private int type;
	private int length;
	private int[] hitLocations;
	private int health;
	private Boolean sunk = false;
	private String orientation = "Up";
	private int startXLocation;
	private int startYLocation;
	private UserInterface view;
	private Player player;
	/**
	 * creates a ship
	 * @param type - the type of ship created
	 * @param player - the owner of the ship
	 */
	public Ship(int type, Player player) {
		
		this.type = type;
		this.player = player;
		
		switch(type) {
		
		case 0:
			length = 4;
			break;
		case 1:
			length = 5;
			break;
		case 2:
			length = 3;
			break;
		case 3:
			length = 3;
			break;
		case 4:
			length= 2;
			break;
		default:
			length = 0;
		}
		
		health = length;
		
		hitLocations = new int[length];
		for ( int i = 0 ; i < length; i++ ) {
			hitLocations[i] = 0;
		}
		
	}
	/**
	 * gets the health of the ship
	 * @return the number of squares not yet hit
	 */
	public int getHealth() {
		return health;
	}
	/** 
	 * gets the type of the ship
	 * @return the integer type of the ship
	 */
	public int getType() {
		return type;
	}
	/**
	 * gets the x location of the bow of the ship
	 * @return the x location
	 */
	public int getX() {
		return startXLocation;
	}
	/**
	 * gets the y loation of the bow of the ship
	 * @return the y location
	 */
	public int getY() {
		return startYLocation;
	}
	/**
	 * gets the length of the ship
	 * @return the number of square long the ship is
	 */
	public int getLength() {
		return length;
	}
	/**
	 * gets the direction the ship is facing
	 * @return a string with the direction
	 */
	public String getOrientation() {
		return orientation;
	}
	/**
	 * sets the view the ship is attached to
	 * @param view - the view of the game
	 */
	public void setView(UserInterface view) {
		this.view = view;
	}
	/**
	 * sets the y location of the ship (x and y are flipped
	 * due to using grid coordinates instead of rows/columns)
	 * @param x
	 * @return true if successful
	 */
	public Boolean setX(int x) {
		
		if ( orientation.equals("Right") && x - length < 0 ) {
			return false;
		}
		if ( orientation.equals("Left") && x + length > 11 ) {
			return false;
		}
		startYLocation = x;
		return true;
	}
	/**
	 * sets the x location of the ship (x and y are flipped
	 * due to using grid coordinates instead of rows/columns)
	 * @param y
	 * @return true if successful
	 */
	public Boolean setY(int y) {
		if ( orientation.equals("Up") && y + length > 11 ) {
			return false;
		}
		if ( orientation.equals("Down") && y - length < 0 ) {
			return false;
		}
		startXLocation = y;
		return true;
	}
	/**
	 * sets the orientation of the ship
	 * @param orientation - the direction the ship is facing
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	/**
	 * check if the ship exists at the location, updates values if hit
	 * @param x - the x location to check
	 * @param y - the y location to check
	 * @return true if hit, false if miss
	 */
	public Boolean attack(int x, int y) {
		Boolean isHit = false;
		for ( int i = 0 ; i < length; i++ ) {
			if ( orientation.equals("Up") && startXLocation == (x - i) && startYLocation == y ) {
				hitLocations[i] = 1;
				health--;
				isHit = true;
			}
			if ( orientation.equals("Right") && startXLocation == x && startYLocation == (y + i) ) {
				hitLocations[i] = 1;
				health--;
				isHit = true;
			}
			if ( orientation.equals("Down") && startXLocation == (x + i) && startYLocation == y ) {
				hitLocations[i] = 1;
				health--;
				isHit = true;
			}
			if ( orientation.equals("Left") && startXLocation == x && startYLocation == (y - i) ) {
				hitLocations[i] = 1;
				health--;
				isHit = true;
			}
		}
		
		if ( health <= 0 ) {;
			if ( sunk == false ) {
				view.updateSunk(player, type);
			}
			sunk = true;
		}
		return isHit;
	}

}
