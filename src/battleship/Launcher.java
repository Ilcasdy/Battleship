

package battleship;
import battleship.controller.*;
import battleship.view.*;
import battleship.model.*;
/**
 * contains main method for Battleship
 * @author Sean Bradbury
 *
 */
public class Launcher {
	/**
	 * main method to run Battleship game
	 * @param args - not used
	 */
	public static void main(String[] args) {
		
		Game game;
		UserInterface view;
		Controller controller;
		
		game = new Game();
		view = new UserInterface(game);
		controller = new Controller(game, view);
		
		game.setView(view);
		game.setController(controller);
		view.setController(controller);
		
		view.execute();
	}
}
