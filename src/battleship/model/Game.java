package battleship.model;
import battleship.controller.*;
import battleship.view.UserInterface;
/**
 * the main model class for the battleship game
 */
public class Game {
	
	private UserInterface view;
	@SuppressWarnings("unused")
	private Controller controller;
	private Player player1;
	private Player player2;
	private Player turn;
	private Boolean placeShips;
	/**
	 * default constructor that defaults a user vs ai game
	 */
	public Game() {
		
		player1 = new Player("Player 1", false, this);
		player2 = new Player("Player 2", true, this);
		
		turn = player1;
		placeShips = true;
	}
	/**
	 * connects the model to the view
	 * @param view - the view of the game
	 */
	public void setView(UserInterface view) {
		this.view = view;
	}
	/**
	 * connects the model to the controller
	 * @param controller - the controller for the game
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	/**
	 * determines the phase of the game
	 * @param tOrF true if in the place ships phase, false otherwise
	 */
	public void setPlaceShips(Boolean tOrF) {
		placeShips = tOrF;
	}
	/**
	 * gets which player's turn it is
	 * @return - the player whose turn it is
	 */
	public Player getTurn() {
		return turn;
	}
	/**
	 * gets player 1 - the user
	 * @return player1
	 */
	public Player getPlayer1() {
		return player1;
	}
	/**
	 * gets player 2 - the enemy player
	 * @return the enemy player
	 */
	public Player getPlayer2() {
		return player2;
	}
	/**
	 * gets the phase of the game
	 * @return true if in the place ships phase, otherwise false
	 */
	public Boolean getPlaceShips() {
		return placeShips;
	}
	/**
	 * gets the view of the game
	 * @return view
	 */
	public UserInterface getView() {
		return view;
	}
	/**
	 * uses the turn variable to determine who is attacking and performs an attack
	 * @param x - the x location of the attack
	 * @param y - the y location of the attack
	 * @return true if hit, false if miss
	 */
	public Boolean attack(int x, int y) {
		
		Player attacker = null;
		Player defender = null;
		
		if ( turn == player1 ) {
			attacker = player1;
			defender = player2;
		}
		else {
			attacker = player2;
			defender = player1;
		}
		
		for ( int i = 0; i < 5; i++ ) {
			Boolean isHit = defender.getShip(i).attack(x, y);
			
			if ( isHit == true && attacker == player1 ) {
				view.hit(x, y);
				view.updateMoves(player1, x, y, true);
				player1.setAttacked(x, y, true);
				turn = defender;
				view.getPlayerTime().setTime(0);
				UserInterface.friendlyShipsText();
				UserInterface.enemyShipsText();
				return true;
			}
			else if ( isHit == true && attacker == player2 ) {
				view.updateMoves(player2, x, y, true);
				player2.setAttacked(x, y, true);
				turn = defender;
				view.getPlayerTime().setTime(0);
				UserInterface.friendlyShipsText();
				UserInterface.enemyShipsText();
				return true;
			}
		}
		if ( attacker == player1 ) {
			view.updateMoves(player1, x, y, false);
			player1.setAttacked(x, y, false);
			view.miss(x, y);
		} else {
			view.updateMoves(player2, x, y, false);
			player2.setAttacked(x, y, false);
		}
		
		turn = defender;
		view.getPlayerTime().setTime(0);
		UserInterface.friendlyShipsText();
		UserInterface.enemyShipsText();
		return false;
	}
	/**
	 * resets the game
	 */
	public void restartGame() {
		placeShips = true;
		turn = player1;
		player1.restartGame();
		player2.restartGame();
	}
}
