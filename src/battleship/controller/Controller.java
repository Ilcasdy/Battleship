package battleship.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import battleship.model.*;
import battleship.view.*;
/**
 * Controller class for the battleship game
 */
public class Controller implements ActionListener, MouseListener, MouseMotionListener {

	private UserInterface view;
	private Game game;
	private Locale englishLocale = new Locale("en");
	private Locale spanishLocale = new Locale("sp");
	private ResourceBundle bundle;
	/**
	 * Constructor the connects the controller to the view and model
	 * @param game - the main model object
	 * @param view - the view object
	 */
	public Controller(Game game, UserInterface view) {
		this.game = game;
		this.view = view;
	}
	/**
	 * controls all actions performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		switch (command) {

		case "Exit Splash":
			String name = view.getName();
			if ( !name.equals("") ) {
				game.getPlayer1().setName(name);
			}
			view.closeSplash();
			break;
		case "Swap Boards":
			view.switchGrid();
			break;
		case "Save":
			break;
		case "Load":
			break;
		case "Restart":
			game.restartGame();
			view.switchGrid();
			view.switchGrid();
			view.getGameTime().setTime(0);
			view.getPlayerTime().setTime(0);
			break;
		case "About":
			view.about();
			break;
		case "Help":
			view.help();
			break;
		case "Close":
			view.closeGame();
			break;
		case "Host":
			break;
		case "Connect":
			break;
		case "Disconnect":
			break;
		case "English":
			bundle = ResourceBundle.getBundle("messages", englishLocale);
			view.changeLanguage(bundle);
			break;
		case "Spanish":
			bundle = ResourceBundle.getBundle("messages", spanishLocale);
			view.changeLanguage(bundle);
			break;
		case "Send Message":
			JTextField source = (JTextField) e.getSource();
			String message = source.getText();
			source.setText("");
			view.chat(message);
			break;
		case "Quick Game":
			game.getPlayer1().setAi(true);
			game.restartGame();
			view.switchGrid();
			view.switchGrid();
			view.getGameTime().setTime(0);
			for ( int i = 0; i < 5; i++ ) {
				game.getPlayer1().aiPlaceShip();
				game.getPlayer2().aiPlaceShip();
			}
			game.setPlaceShips(false);
			while ( game.getPlayer1().checkIfAllShipsSunk() == false && game.getPlayer2().checkIfAllShipsSunk() == false) {
				game.getTurn().aiAttack();
			}
			if ( game.getPlayer1().checkIfAllShipsSunk() == true ) {
				view.switchGrid();
				view.switchGrid();
				for ( int i = 0; i < 5; i++ ) {
					view.paintEnemyShip(game.getPlayer2().getShip(i));
				}
				view.loseGame();
			}
			else {
				view.switchGrid();
				view.switchGrid();
				for ( int i = 0; i < 5; i++ ) {
					view.paintEnemyShip(game.getPlayer2().getShip(i));
				}
				view.winGame();
			}
			
			
			break;
		default:

		}
	}
	/**
	 * controls full mouse clicks
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

		
	/**
	 * controls mouse presses
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e)) {

			int x;
			int y;

			JPanel target = (JPanel) e.getSource();

			String square = target.getName();
			String[] split = square.split(":");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);

			if (game.getPlaceShips() == false && view.getIsEnemyGrid() == true && game.getTurn() == game.getPlayer1()) {
				int attacked = game.getTurn().hasAttacked(x, y);
				if (attacked != 0 ) {
					return;
				}
				
				game.attack(x, y);
				Boolean gameOver = game.getPlayer2().checkIfAllShipsSunk();
				
				if ( gameOver == true ) {
					for ( int i = 0; i < 5; i++ ) {
						view.paintEnemyShip(game.getPlayer2().getShip(i));
					}
					view.winGame();
				}
				
				game.getPlayer2().aiAttack();
				Boolean player2Win = game.getPlayer1().checkIfAllShipsSunk();
				if ( player2Win == true ) {
					for ( int i = 0; i < 5; i++ ) {
						view.paintEnemyShip(game.getPlayer2().getShip(i));
					}
					view.loseGame();
				}
			}

			else if (game.getPlaceShips() == true && view.getIsEnemyGrid() == false){

				String orientation = view.getOrientation();
				Boolean success = game.getPlayer1().placeShip(y, x, orientation);
				if ( success == true ) {
					game.getPlayer2().aiPlaceShip();
					view.paintShip();
				}
			}
		}

		if (SwingUtilities.isRightMouseButton(e)) {
			view.rotate();
			JPanel source = (JPanel) e.getSource();

			String square = source.getName();
			String[] split = square.split(":");
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);

			view.hoverShip(x, y);
		}

	}
	/**
	 * controls mouse releases
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (game.getPlaceShips() == true) {

			JPanel source;

			if (view.getIsEnemyGrid()) {
				return;
			}

			source = (JPanel) e.getSource();

			String square = source.getName();
			String[] split = square.split(":");
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);

			view.hoverShip(x, y);

		}

	}
}
