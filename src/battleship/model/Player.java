package battleship.model;

import java.util.Random;

/**
 * this class contains information about a single player
 */
public class Player {
	
	private Game game;
	private String name;
	private Ship[] ships = new Ship[5];
	private int[][] attacked = new int[11][11];
	private Boolean isAi;
	private int shipsPlaced = 0;
	/**
	 * constructs a player
	 * @param name - the name of the player
	 * @param isAi - if the player is ai or not
	 * @param game - the game the player is connected to
	 */
	public Player(String name, Boolean isAi, Game game) {
		
		this.name = name;
		this.isAi = isAi;
		this.game = game;
		
		for ( int i = 0; i < ships.length; i++ ) {
			ships[i] = new Ship(i, this);
		}
		
		for ( int i = 1 ; i < 11; i++) {
			for (int j = 0 ; j < 11; j++) {
				attacked[i][j] = 0;
			}
		}
	}
	/**
	 * gets the name of the player
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets the name of the player
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * gets how many ships have been placed
	 * @return the number of ships placed
	 */
	public int getShipsPlaced() {
		return shipsPlaced;
	}
	/**
	 * gets a Ship owned by the player
	 * @param ship - which ship is being accessed
	 * @return the Ship
	 */
	public Ship getShip(int ship) {
		return ships[ship];
	}
	/**
	 * gets whether the player is ai or not
	 * @return true if ai, otherwise false
	 */
	public Boolean getAi() {
		return isAi;
	}
	/**
	 * sets if the player is ai or not
	 * @param true if ai, false if not
	 */
	public void setAi(Boolean ai) {
		this.isAi = ai;
	}
	/**
	 * records where in the grid has been attacked and if it was a hit or miss
	 * @param x - the x location of the attack
	 * @param y - the y location of the attack
	 * @param hit - true if hit, false if miss
	 */
	public void setAttacked(int x, int y, Boolean hit) {
		if ( hit == false ) {
			attacked[x][y] = 1;
		} else {
			attacked[x][y] = 2;
		}
	}
	/**
	 * randomly places the ship for ai players
	 */
	public void aiPlaceShip() {
			Boolean aiPlacedShip = false;
			Random random = new Random();
			
			while ( !aiPlacedShip ) {
				int randX = random.nextInt(10) + 1;
				int randY = random.nextInt(10) + 1;
				
				int randOrientation = random.nextInt(4);
				String direction = "";
				
				switch(randOrientation) {
				case 0:
					direction = "Up";
					break;
				case 1:
					direction = "Right";
					break;
				case 2:
					direction = "Down";
					break;
				case 3:
					direction = "Left";
					break;
				default:
					direction = "Up";
					
				}
				aiPlacedShip = placeShip(randX, randY, direction);
			}	
			ships[shipsPlaced].setView(game.getView());
			shipsPlaced++;
	}
	/**
	 * randomly attacks a square for ai players
	 * @return true if hit, false if miss
	 */
	public Boolean aiAttack() {
		
		Random random = new Random();
		int randX = 0;
		int randY = 0;
		
		do {
			randX = random.nextInt(10) + 1;
			randY = random.nextInt(10) + 1;
			
			if ( attacked[randX][randY] == 0 ) {
				Boolean hit = game.attack(randX, randY);
				return hit;
			} 
		} while (attacked[randX][randY] != 0);
		return false;
	}
	/**
	 * checks for valid placement
	 * @param x - the x location of the bow of the ship
	 * @param y - the y location of the bow of the ship
	 * @param orientation - the direction the ship is facing
	 * @return true if ship can be placed
	 */
	public Boolean placeShip(int x, int y, String orientation) {
		
		Boolean validPlacement = checkShipPlacement(x, y, orientation);
		
		if (validPlacement == false) {
			return false;
		}
		if (!isAi) {
			ships[shipsPlaced].setView(game.getView());
			shipsPlaced++;
		}
		if ( shipsPlaced == 5 ) {
			game.setPlaceShips(false);
		}
		
		return true;
	}
	/**
	 * checks if the ships are beyond the boundary of the grid or collide with eachother
	 * @param x - the x location of the head of the ship
	 * @param y - the y location of the head of the ship
	 * @param orientation - the direction the ship being checked is facing
	 * @return - true if valid placement
	 */
	public Boolean checkShipPlacement(int x, int y, String orientation) {
		
		ships[shipsPlaced].setOrientation(orientation);
		Boolean xLegit = ships[shipsPlaced].setX(x);
		Boolean yLegit = ships[shipsPlaced].setY(y);
		if ( !xLegit || !yLegit) {
			return false;
		}
		
		for ( int i = 0; i < shipsPlaced; i++ ) {
			

			for ( int j = 0; j < ships[shipsPlaced].getLength(); j++ ) {
				if ( ships[i].getOrientation().equals("Up")) {
					if ( orientation.equals("Up")) {
						if ( x == ships[i].getY()) {
							if ( ships[i].getX() <= y + j && ships[i].getX() + ships[i].getLength() > y + j) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Down")) {
						if ( x == ships[i].getY()) {
							if ( ships[i].getX() <= y - j && ships[i].getX() + ships[i].getLength() > y - j) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Right")) {
						if ( x - j == ships[i].getY()) {
							if ( y >= ships[i].getX() && y <= ships[i].getX() + ships[i].getLength() - 1) {
								return false;
							}
						}
					}
					if ( orientation.equals("Left")) {
						if ( x + j == ships[i].getY()) {
							if ( y >= ships[i].getX() && y <= ships[i].getX() + ships[i].getLength() - 1) {
								return false;
							}
						}
					}
				}
				if ( ships[i].getOrientation().equals("Down")) {
					if ( orientation.equals("Up")) {
						if ( x == ships[i].getY()) {
							if ( ships[i].getX() >= y + j && ships[i].getX() - ships[i].getLength() < y + j) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Down")) {
						if ( x == ships[i].getY()) {
							if ( ships[i].getX() >= y - j && ships[i].getX() - ships[i].getLength() < y - j) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Right")) {
						
						if ( x - j == ships[i].getY()) {
							if ( y <= ships[i].getX() && y >= ships[i].getX() - ships[i].getLength() + 1) {
								return false;
							}
						}
					}
					if ( orientation.equals("Left")) {
						if ( x + j == ships[i].getY()) {
							if ( y <= ships[i].getX() && y >= ships[i].getX() - ships[i].getLength() + 1) {
								return false;
							}
						}
					}
				}
				if ( ships[i].getOrientation().equals("Right")) {
					if ( orientation.equals("Up")) {
						if ( y + j == ships[i].getX()) {
							if ( x <= ships[i].getY() && x >= ships[i].getY() - ships[i].getLength() + 1) {
								return false;
							}
						}
					}
					if ( orientation.equals("Down")) {
						if ( y - j == ships[i].getX()) {
							if ( x <= ships[i].getY() && x >= ships[i].getY() - ships[i].getLength() + 1) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Right")) {
						
						if ( y == ships[i].getX()) {
							if ( ships[i].getY() >= x - j && ships[i].getY() - ships[i].getLength() < x - j) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Left")) {
						if ( y == ships[i].getX()) {
							if ( ships[i].getY() >= x + j && ships[i].getY() - ships[i].getLength() < x + j) {
								return false;
							}
						}
					}
				}
				if ( ships[i].getOrientation().equals("Left")) {
					if ( orientation.equals("Up")) {
						if ( y + j == ships[i].getX()) {
							if ( x >= ships[i].getY() && x <= ships[i].getY() + ships[i].getLength() - 1) {
								return false;
							}
						}
					}
					if ( orientation.equals("Down")) {
						if ( y - j == ships[i].getX()) {
							if ( x >= ships[i].getY() && x <= ships[i].getY() + ships[i].getLength() - 1) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Right")) {
						
						if ( y == ships[i].getX()) {
							if ( ships[i].getY() <= x - j && ships[i].getY() + ships[i].getLength() > x - j) {
								return false;
							}
						} 
					}
					if ( orientation.equals("Left")) {
						if ( y == ships[i].getX()) {
							if ( ships[i].getY() <= x + j && ships[i].getY() + ships[i].getLength() > x + j) {
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	/**
	 * checks if the player has any ships left
	 * @return true if all ships are sunk
	 */
	public Boolean checkIfAllShipsSunk() {
		int sunkShipCount = 0;
		for ( int i= 0 ; i < 5; i++ ) {
			if ( ships[i].getHealth() == 0 ) {
				sunkShipCount++;
			}
		}
		if ( sunkShipCount >= 5 ) {
			return true;
		}
		return false;
	}
	/**
	 * checks if a square has already been attacked
	 * @param x - the x location to check
	 * @param y - the y location to check
	 * @return true if the square has already been attacked
	 */
	public int hasAttacked(int x, int y) {
		return attacked[x][y];
	}
	/**
	 * resets the ships owned by the player
	 */
	public void restartGame() {
		shipsPlaced = 0;
		for ( int i = 1 ; i < 11; i++) {
			for (int j = 0 ; j < 11; j++) {
				attacked[i][j] = 0;
			}
		}
		ships = new Ship[5];
		for ( int i = 0; i < ships.length; i++ ) {
			ships[i] = new Ship(i, this);
		}
	}
}
