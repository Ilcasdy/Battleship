package battleship.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import battleship.model.*;
import battleship.network.Client;
import battleship.network.Host;
import battleship.network.Network;
import battleship.view.*;
/**
 * Controller class for the battleship game
 * @since 20
 */
public class Controller implements ActionListener, MouseListener, MouseMotionListener {

	private UserInterface view;
	private Game game;
	private Locale englishLocale = new Locale.Builder().setLanguage("en").build();
	private Locale spanishLocale = new Locale.Builder().setLanguage("sp").build();
	private ResourceBundle bundle = ResourceBundle.getBundle("messages", englishLocale);
	private Network network;
	private int port;
	private String ip;
	private Host host;
	/**
	 * Constructor the connects the controller to the view and model
	 * @param game - the main model object
	 * @param view - the view object
	 */
	public Controller(Game game, UserInterface view) {
		this.game = game;
		this.view = view;
		network = new Network(this);
	}
	/**
	 * controls all actions performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		switch (command) {
		case "Close Host":
			view.getHostDialog().dispose();
			break;
		case "Close Connect":
			view.getConnectDialog().dispose();
			break;
		case "Start Connection":
			Client client = new Client(this);
			ip = view.getClientIP();
			port = view.getClientPort();
			if ( port < 10000 || port > 65535 ) {
				setStatus("enteraport");
				break;
			}
			network.setSocket(client.connect(ip, port));
			network.start();
			game.setMultiplayer(true);
			game.getPlayer2().setAi(false);
			game.setTurn(game.getPlayer2());
			break;
		case "Start Host":
			host = new Host(this);
			port = view.getHostPort();
			if ( port < 10000 || port > 65535 ) {
				setStatus("enteraport");
				break;
			}
			host.setPort(port);
			host.start();
			setStatus("waitingforconnection");
			
			break;
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
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("save.txt", "UTF-8");
			} catch (FileNotFoundException ex) {
				UserInterface.updateMoves("filenotfound");
			} catch (UnsupportedEncodingException ex) {
				
			}
			writer.println(game.getPlaceShips());
			
			writer.println(game.getPlayer1().getName());
			writer.println(game.getPlayer1().getShipsPlaced());
			for ( int i = 0; i < game.getPlayer1().getShipsPlaced(); i++ ) {
				writer.println(game.getPlayer1().getShip(i).getX());
				writer.println(game.getPlayer1().getShip(i).getY());
				writer.println(game.getPlayer1().getShip(i).getOrientation());
				writer.println(game.getPlayer1().getShip(i).getHealth());
			}
			for ( int i = 0; i < 11; i++ ) {
				for ( int j = 0; j < 11; j++ ) {
					if ( i == 10 & j == 10 ) {
						writer.print(game.getPlayer1().hasAttacked(i, j));
					} else {
						writer.print(game.getPlayer1().hasAttacked(i, j) + ",");
					}
					
				}
				writer.print("\n");	
			}
			writer.println(game.getPlayer2().getName());
			writer.println(game.getPlayer2().getShipsPlaced());
			for ( int i = 0; i < game.getPlayer2().getShipsPlaced(); i++ ) {
				writer.println(game.getPlayer2().getShip(i).getX());
				writer.println(game.getPlayer2().getShip(i).getY());
				writer.println(game.getPlayer2().getShip(i).getOrientation());
				writer.println(game.getPlayer2().getShip(i).getHealth());
			}
			for ( int i = 0; i < 11; i++ ) {
				for ( int j = 0; j < 11; j++ ) {
					writer.print(game.getPlayer2().hasAttacked(i, j) + ",");
				}
				writer.print("\n");	
			}
			writer.println(game.getPlayer2().getAi());
			UserInterface.updateMoves("gamesaved");
			writer.close();
			break;
			
		case "Load":
			game.restartGame();
			view.switchGrid();
			view.switchGrid();
			view.getGameTime().setTime(0);
			view.getPlayerTime().setTime(0);
			UserInterface.openingText();
			
			Boolean placed;
			File file = new File("save.txt");
			Scanner s = null;
			try {
				s = new Scanner(file);
			} catch (FileNotFoundException e1) {
				UserInterface.updateMoves("filenotfound");
			}
			placed = s.nextBoolean();
			s.nextLine();
			
			game.setPlaceShips(placed);
			game.getPlayer1().setName(s.nextLine());
			
			int ships = s.nextInt();
			game.getPlayer1().setShipsPlaced(ships);
			s.nextLine();
			
			for ( int i = 0; i < ships; i++ ) {
				game.getPlayer1().getShip(i).setY(s.nextInt());
				s.nextLine();
				game.getPlayer1().getShip(i).setX(s.nextInt());
				s.nextLine();
				game.getPlayer1().getShip(i).setOrientation(s.nextLine());
				game.getPlayer1().getShip(i).setHealth(s.nextInt());
				s.nextLine();
				game.getPlayer1().getShip(i).setView(game.getView());
			}
			Scanner line;
			for ( int i = 0; i < 11; i++ ) {
				line = new Scanner(s.nextLine());
				line.useDelimiter(",");
				for (int j = 0; j < 11; j++ ) {
					int status = line.nextInt();
					if ( status == 1) {
						game.getPlayer1().setAttacked(i, j, false);
					} else if ( status == 2 ) {
						game.getPlayer1().setAttacked(i,  j,  true);
					} 
				}
			}
			game.getPlayer2().setName(s.nextLine());
			
			ships = s.nextInt();
			game.getPlayer2().setShipsPlaced(ships);
			s.nextLine();
			
			for ( int i = 0; i < ships; i++ ) {
				game.getPlayer2().getShip(i).setY(s.nextInt());
				s.nextLine();
				game.getPlayer2().getShip(i).setX(s.nextInt());
				s.nextLine();
				game.getPlayer2().getShip(i).setOrientation(s.nextLine());
				game.getPlayer2().getShip(i).setHealth(s.nextInt());
				s.nextLine();
				game.getPlayer2().getShip(i).setView(game.getView());
				System.out.println(game.getPlayer2().getShip(i).getX() + "\n"
						+ game.getPlayer2().getShip(i).getY() + "\n"
						+ game.getPlayer2().getShip(i).getOrientation() + "\n"
						+ game.getPlayer2().getShip(i).getHealth() );
			}
			for ( int i = 0; i < 11; i++ ) {
				line = new Scanner(s.nextLine());
				line.useDelimiter(",");
				for (int j = 0; j < 11; j++ ) {
					int status = line.nextInt();
					if ( status == 1) {
						game.getPlayer2().setAttacked(i, j, false);
					} else if ( status == 2 ) {
						game.getPlayer2().setAttacked(i,  j,  true);
					} 
				}
			}
			game.getPlayer2().setAi(s.nextBoolean());
			s.close();
			view.switchGrid();
			view.switchGrid();
			UserInterface.enemyShipsText();
			UserInterface.updateMoves("gameloaded");
			
			break;
		case "Restart":
			
			if ( game.getMultiplayer() ) {
				network.sendRestart();
			} else {
				game.restartGame();
				view.switchGrid();
				view.switchGrid();
				view.getGameTime().setTime(0);
				view.getPlayerTime().setTime(0);
				UserInterface.openingText();
			}
			break;
		case "Resign":
			view.loseGame();
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
			view.hostPopUp();
			break;
		case "Connect":
			view.clientPopUp();
			break;
		case "Disconnect":
			network.disconnect();
			UserInterface.updateMoves("disconnecting");
			game.setMultiplayer(false);
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
			view.chat(message, false);
			if ( game.getMultiplayer() ) {
				network.writeChatMessage(message);
			}
			break;
		case "Quick Game":
			
			game.restartGame();
			game.getPlayer1().setAi(true);
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
		
		if (game.getGameOver()) {
			return;
		}
		
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
				
				
				if ( game.getMultiplayer() == true ) {
					network.sendAttack(x, y);
				} else {
					game.attack(x, y);
				}
				
				Boolean gameOver = game.getPlayer2().checkIfAllShipsSunk();
				
				if ( gameOver == true ) {
					for ( int i = 0; i < 5; i++ ) {
						view.paintEnemyShip(game.getPlayer2().getShip(i));
					}
					view.winGame();
				}
				if ( game.getPlayer2().getAi() == true ) {
					game.getPlayer2().aiAttack();
				}
				
				Boolean player2Win = game.getPlayer1().checkIfAllShipsSunk();
				if ( player2Win == true ) {
					for ( int i = 0; i < 5; i++ ) {
						view.paintEnemyShip(game.getPlayer2().getShip(i));
					}
					view.loseGame();
				}
			}

			else if (game.getPlayer1().getShipsPlaced() < 5 && view.getIsEnemyGrid() == false && game.getMultiplayer() == false ){

				String orientation = view.getOrientation();
				Boolean success = game.getPlayer1().placeShip(y, x, orientation);
				if ( success == true ) {
					game.getPlayer2().aiPlaceShip();
					view.paintShip();
				}
			} else if (game.getPlayer1().getShipsPlaced() < 5 && game.getMultiplayer() == true && view.getIsEnemyGrid() == false ) {
				String orientation = view.getOrientation();
				game.getPlayer1().placeShip(y, x, orientation);
				if ( game.getPlayer1().getShipsPlaced() == 5 && game.getPlaceShips() == true) {
					sendReady();
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
	/**
	 * getter for name
	 * @return name of current player
	 */
	public String sendName() {
		return game.getPlayer1().getName();
	}
	/**
	 * takes a string from the other player and converts it to an action
	 * @param message - the string sent from the other player
	 */
	public void handleNetworkMessage(String message) {
		
		
		String[] messageArray = message.split(":", 2);
		switch(messageArray[0]) {
		case "chat":
			view.chat(messageArray[1], true);
			break;
		case "name":
			game.getPlayer2().setName(messageArray[1]);
			break;
		case "game":
			if ( messageArray[1].equals("ready") && game.getPlayer1().getShipsPlaced() == 5 && game.getPlaceShips() == true ) {
				game.setPlaceShips(false);
				sendReady();
			} else if ( messageArray[1].equals("over")) {
				view.winGame();
			}
			break;
		case "attack":
			String[] coordinates = messageArray[1].split(":");
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			Boolean hit = game.attack(x, y);
			if ( hit ) {
				view.hitPlayer(x, y);
			} else {
				view.missPlayer(x, y);
			}
			network.sendHit(hit, x, y);
			Boolean gameOver = game.getPlayer1().checkIfAllShipsSunk();
			if ( gameOver ) {
				network.sendGameOver();
				view.loseGame();
			}
			break;
		case "disconnect":
			network.disconnect();
			UserInterface.updateMoves("disconnected");
			game.setMultiplayer(false);
			break;
		case "hit":
			String[] location = messageArray[1].split(":");
			x = Integer.parseInt(location[1]);
			y = Integer.parseInt(location[2]);
			if ( location[0].equals("true") ) {
				view.hit(x, y);
				view.updateMoves(game.getPlayer1(), x, y, true);
				game.getPlayer1().setAttacked(x, y, true);
				game.setTurn(game.getPlayer2());
				view.getPlayerTime().setTime(0);
				UserInterface.friendlyShipsText();
				UserInterface.enemyShipsText();
			} else {
				view.updateMoves(game.getPlayer1(), x, y, false);
				game.getPlayer1().setAttacked(x, y, false);
				game.setTurn(game.getPlayer2());
				view.miss(x, y);
				view.getPlayerTime().setTime(0);
				UserInterface.friendlyShipsText();
				UserInterface.enemyShipsText();
			}
			break;
		case "request":
			if ( messageArray[1].equals("restart") ) {
				UserInterface.updateMoves(game.getPlayer2().getName() + " wants to restart the game");
				
			}
			break;
		case "sunk":
			for ( int i = 0; i < 5; i++ ) {
				if ( messageArray[1].equals(Integer.toString(i)) ) {
					if ( game.getPlayer2().getShip(i).getHealth() == 0 ) {
						continue;
					}
						game.getPlayer2().getShip(i).setHealth(0);
						view.updateSunk(game.getPlayer2(), i);
					
				}
			}
			break;
		default:
		}
	}
	/**
	 * tells the other player that the ships are placed
	 */
	public void sendReady() {
		network.writeReady();
		
	}
	/**
	 * updates the user interface 
	 * @param message - game state change
	 */
	public void postMessage(String message) {
		UserInterface.updateMoves(message);
	}
	/**
	 * updates the network status in the connection dialog
	 * @param message - network update
	 */
	public void setStatus(String message) {
		view.updateNetworkStatus(bundle.getString(message));
	}
	/**
	 * tells the other player they have sunk a ship
	 * @param i - the type of ship sunk
	 */
	public void sendSunk(int i) {
		network.sendSunk(i);
		
	}
	/**
	 * starts a host session
	 */
	public void startConnection() {
		network.setSocket(host.getSocket());
		network.start();
		setStatus("connectionsuccessful");
		game.setMultiplayer(true);
		game.getPlayer2().setAi(false);
		game.setTurn(game.getPlayer1());
	}

}
