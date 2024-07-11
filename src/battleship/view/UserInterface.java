
/* 
 * fire, explosion, and water art from https://www.pngwing.com/en/free-png-bnxfk
 * ship art by Lowder 2 https://opengameart.org/content/sea-warfare-set-ships-and-more
*/
package battleship.view;
import java.awt.BorderLayout;
import battleship.model.*;
import battleship.controller.*;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * View for the game
 * @author Sean Bradbury
 */
public class UserInterface extends JFrame {
	/**
	 * The main model
	 */
	private static Game game;
	/**
	 * the controller for the game
	 */
	private static Controller controller;
	/**
	 * the orientation of the ship for placement, defaulted to up
	 */
	private String orientation = "Up";
	/**
	 * The text area for chat messages
	 */
	private static JTextArea chatWindow;
	/**
	 * the opening screen
	 */
	private static JDialog splash;
	/**
	 * jlabel which displays the turn and timers
	 */
	private static JLabel gameInfo;
	/**
	 * displays friendly ship stats
	 */
	private static JTextArea friendlyShips;
	/**
	 * displays enemy ship stats
	 */
	private static JTextArea enemyShips;
	/**
	 * displays moves made in the game
	 */
	private static JTextArea moves;
	/**
	 * english locale
	 */
	static Locale englishLocale = new Locale("en");
	/**
	 * spanish locale
	 */
	static Locale spanishLocale = new Locale("sp");
	/**
	 * resource bundle defaulted to english
	 */
	static ResourceBundle bundle = ResourceBundle.getBundle("messages", englishLocale);
	/**
	 * an array of jlabels for containing ship graphics
	 */
	public static JLabel[] pictures = new JLabel[5];
	/**
	 * the swap boards button
	 */
	private static JButton swapBoards;
	/**
	 * the menu system
	 */
	private static JMenuBar menuBar;
	/**
	 * the game time
	 */
	private static Timer gameTimer = new Timer();
	/**
	 * the time of the player's turn
	 */
	private static Timer playerTimer = new Timer();
	/**
	 * text field used for name input
	 */
	private static JTextField name;
	/**
	 * the splash screen
	 */
	private static JDialog start;
	
	private static final long serialVersionUID = 1L;
	/**
	 * tells what is the currently active grid
	 */
	public static boolean isEnemyGrid;
	/**
	 * dark green colour used for the background
	 */
	public static final Color darkGreen = Color.decode("#284F00");
	/**
	 * cream colour used for text panels
	 */
	public static final Color cream = Color.decode("#FFF2CC");
	/**
	 * red colour used for grid labels and swap boards button
	 */
	public static final Color brightRed = Color.decode("#FF3131");
	/**
	 * blue used for friendly grid
	 */
	public static final Color paleBlue = Color.decode("#5ABCD8");
	/**
	 * blue used for friendly grid
	 */
	public static final Color lightBlue = Color.decode("#1CA3EC");
	/**
	 * blue used for friendly grid
	 */
	public static final Color seaBlue = Color.decode("#2389DA");
	/**
	 * blue used for friendly grid
	 */
	public static final Color blue = Color.decode("#0F5E9C");
	/**
	 * Courier New font, normal size
	 */
	public static final Font menuFont = new Font("Courier New", Font.PLAIN, 18);
	/**
	 * Courier New font, small
	 */
	public static final Font timeFont = new Font("Courier New", Font.PLAIN, 14);
	/**
	 * Courier New font, large
	 */
	public static final Font courier = new Font("Courier New", Font.BOLD, 28);
	/**
	 * Courier New font for top left panel
	 */
	public static final Font playerTurn = new Font("Courier New", Font.PLAIN, 24);
	/**
	 * c is used to set attributes to gridbag layout components
	 */
	public static GridBagConstraints c = new GridBagConstraints();
	/**
	 * the frame of the ui
	 */
	public static Container pane;
	/**
	 * the main grid of the game
	 */
	public static JPanel gameGrid;
	/**
	 * array of the friendly grid panels, the 0 row and column are the labels 
	 * and 1-10 are the game grid
	 */
	public static JPanel[][] friendlyGridArray = new JPanel[11][11];
	/**
	 * array of the enemy grid panels
	 */
	public static JPanel[][] enemyGridArray = new JPanel[11][11];
	
	
	/**
	 * label used with an image icon to create graphics
	 */
	

	/**
	 * default constructor
	 */
	public UserInterface() {
	}
	/**
	 * constructor that connects the view to the model
	 * @param game - the model
	 */
	public UserInterface(Game game) {
		
		UserInterface.game = game;
	}
	/**
	 * sets the controller to be used
	 * @param controller - the controller to be used
	 */
	public void setController(Controller controller) {
		UserInterface.controller = controller;
	}
	/**
	 * gets the orientation for ship placement
	 * @return orientation of the ship
	 */
	public String getOrientation() {
		return orientation;
	}
	/**
	 * gets the enemy grid variable which determines which grid is showing
	 * @return isEnemyGrid - a boolean for which grid is showing
	 */
	public Boolean getIsEnemyGrid() {
		return isEnemyGrid;
	}
	/**
	 * gets the timer for the whole game
	 * @return gameTimer
	 */
	public Timer getGameTime() {
		return gameTimer;
	}
	/**
	 * gets the timer for the player turn
	 * @return playerTimer
	 */
	public Timer getPlayerTime() {
		return playerTimer;
	}
	/**
	 * gets the name typed into the name field
	 */
	public String getName() {
		return name.getText();
	}
	/**
	 * this function creates the view of the game
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createGame(Container pane) {
		
		isEnemyGrid = false;
		UserInterface.pane = pane;

		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pane.setLayout(new GridBagLayout());
		pane.setBackground(darkGreen);
		
		start();
		
		createSplash();
		
		createMenu(pane);
		createLogo(pane);
		createSwap(pane);
		createLeftPanel(pane);
		createGameGrid(pane);
		createRightPanel(pane);
		gameTimer.start();
		gameTimer.setMain(true);
		playerTimer.start();
	}
	/**
	 * creates the splash screen
	 */
	public static void start() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		start = new JDialog(frame, "Battleship", true);
		start.setLocation(300, 200);
        start.setSize(300, 200);
        ImageIcon logoIcon = new ImageIcon("icons/battleshiplogo.png");
        JLabel pic = new JLabel(logoIcon);
        start.add(pic);
        
        Timer startTimer = new Timer();
        startTimer.setStart(true);
        startTimer.start();
        start.setVisible(true);
        
	}
	/**
	 * closes the splash screen
	 */
	public static void closeStart() {
		start.dispose();
	}
	/**
	 * creates the opening screen with name input
	 */
	public static void createSplash() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		splash = new JDialog(frame, "Battleship", true);
		splash.setLocation(300, 200);
        splash.setSize(500, 300);
        JPanel splashPanel = new JPanel(new BorderLayout());
        
        JButton exitSplash = new JButton("Start Game!");
        exitSplash.setActionCommand("Exit Splash");
		exitSplash.addActionListener(controller);
		
        JLabel title = new JLabel("Battleship");
        JPanel middle = new JPanel(new BorderLayout());
        JTextArea body = new JTextArea();
        body.append("By Sean Bradbury\n");
        body.append("Ship art by Lowder 2\n\n");
        body.append("Thank you for playing my game!\n\n");
        body.append("Enter your name below");
        
        name = new JTextField();
        
        splash.add(splashPanel);
        splashPanel.add(exitSplash, BorderLayout.SOUTH);
        splashPanel.add(title, BorderLayout.NORTH);
        splashPanel.add(middle, BorderLayout.CENTER);
        middle.add(body, BorderLayout.CENTER);
        middle.add(name, BorderLayout.SOUTH);
        splash.setVisible(true);
	}
	/**
	 * creates the about dialog
	 */
	public void about() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		JDialog about = new JDialog(frame, "About", true);
		about.setLocation(300, 200);
        about.setSize(300, 200);
        JTextArea aboutText = new JTextArea();
        aboutText.setEditable(false);
        about.add(aboutText);
        aboutText.append("Made by Sean Bradbury");
        about.setVisible(true);
	}
	/**
	 * creates the help dialog
	 */
	public void help() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		JDialog help = new JDialog(frame, "Help", true);
		help.setLocation(300, 200);
        help.setSize(300, 200);
        JTextArea helpText = new JTextArea();
        helpText.setEditable(false);
        help.add(helpText);
        helpText.append("Left click to place ships\n");
        helpText.append("Right click to rotate ships\n");
        helpText.append("Left click on enemy board to attack\n");
        help.setVisible(true);
	}

	/**
	 * Creates the top left menu
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createMenu(Container pane) {
		
		if ( menuBar != null ) {
			pane.remove(menuBar);
		}
		
		menuBar = new JMenuBar();
		menuBar.setBackground(cream);
		menuBar.setPreferredSize(new Dimension(80, 20));
		;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		pane.add(menuBar, c);
		

		UIManager.put("Menu.background", cream);
		// Create the menu button
		JMenu menuButton = new JMenu(bundle.getString("menu"));
		menuButton.setFont(menuFont);
		menuButton.setBackground(cream);
		menuBar.add(menuButton);

		// labels for the menu options
		JMenuItem save = new JMenuItem(bundle.getString("save"));
		save.setFont(menuFont);
		save.setActionCommand("Save");
		save.addActionListener(controller);
		menuButton.add(save);
		JMenuItem load = new JMenuItem(bundle.getString("load"));
		load.setFont(menuFont);
		load.setActionCommand("Load");
		load.addActionListener(controller);
		menuButton.add(load);
		JMenuItem restart = new JMenuItem(bundle.getString("restart"));
		restart.setFont(menuFont);
		restart.setActionCommand("Restart");
		restart.addActionListener(controller);
		menuButton.add(restart);
		JMenu networkMenu = new JMenu(bundle.getString("network"));
		networkMenu.setFont(menuFont);
		menuButton.add(networkMenu);
		JMenu languageMenu = new JMenu(bundle.getString("language"));
		languageMenu.setFont(menuFont);
		menuButton.add(languageMenu);
		JMenuItem about = new JMenuItem(bundle.getString("about"));
		about.setFont(menuFont);
		about.setActionCommand("About");
		about.addActionListener(controller);
		menuButton.add(about);
		JMenuItem help = new JMenuItem(bundle.getString("help"));
		help.setFont(menuFont);
		help.setActionCommand("Help");
		help.addActionListener(controller);
		menuButton.add(help);
		JMenuItem close = new JMenuItem(bundle.getString("close"));
		close.setFont(menuFont);
		close.setActionCommand("Close");
		close.addActionListener(controller);
		menuButton.add(close);
		JMenu debug = new JMenu(bundle.getString("debug"));
		debug.setFont(menuFont);
		menuButton.add(debug);

		JMenuItem host = new JMenuItem(bundle.getString("host"));
		host.setFont(menuFont);
		host.setActionCommand("Host");
		host.addActionListener(controller);
		networkMenu.add(host);
		JMenuItem connect = new JMenuItem(bundle.getString("connect"));
		connect.setFont(menuFont);
		connect.setActionCommand("Connect");
		connect.addActionListener(controller);
		networkMenu.add(connect);
		JMenuItem disconnect = new JMenuItem(bundle.getString("disconnect"));
		disconnect.setFont(menuFont);
		disconnect.setActionCommand("Disconnect");
		disconnect.addActionListener(controller);
		networkMenu.add(disconnect);

		JMenuItem english = new JMenuItem(bundle.getString("english"));
		english.setFont(menuFont);
		english.setActionCommand("English");
		english.addActionListener(controller);
		languageMenu.add(english);
		JMenuItem spanish = new JMenuItem(bundle.getString("spanish"));
		spanish.setFont(menuFont);
		spanish.setActionCommand("Spanish");
		spanish.addActionListener(controller);
		languageMenu.add(spanish);
		
		JMenuItem quickGame = new JMenuItem(bundle.getString("quickgame"));
		quickGame.setFont(menuFont);
		quickGame.setActionCommand("Quick Game");
		quickGame.addActionListener(controller);
		debug.add(quickGame);



	}

	/**
	 * displays the logo
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createLogo(Container pane) {

		ImageIcon logoIcon = new ImageIcon("icons/battleshiplogo.png");
		JPanel logo = new JPanel();
		logo.setBackground(darkGreen);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 40, 0, 0);
		c.gridx = 8;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		pane.add(logo, c);

		JLabel battleshipLogo = new JLabel(logoIcon);
		battleshipLogo.setBackground(darkGreen);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		logo.add(battleshipLogo, c);
	}

	/**
	 * creates the swap boards button
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createSwap(Container pane) {
		
		swapBoards = new JButton(bundle.getString("swapboards"));
		swapBoards.setActionCommand("Swap Boards");
		swapBoards.addActionListener(controller);
		
		swapBoards.setBackground(brightRed);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(60, 10, 10, 0);
		c.ipadx = 0;
		c.gridx = 13;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		pane.add(swapBoards, c);
	}

	/**
	 * creates the left panel
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createLeftPanel(Container pane) {

		// outer layer to contain the other panels
		JPanel leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBackground(darkGreen);
		c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		pane.add(leftPanel, c);

		// this panel will show whose turn it is with the timer
		gameInfo = new JLabel();
		gameInfo.setHorizontalAlignment(SwingConstants.CENTER);
		gameInfo.setPreferredSize(new Dimension(0, 110));
		gameInfo.setBackground(cream);
		gameInfo.setFont(timeFont);
		gameInfo.setOpaque(true);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(30, 10, 10, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		leftPanel.add(gameInfo, c);

		// shows the health of friendly ships
		friendlyShips = new JTextArea();
		friendlyShips.setBackground(cream);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 10, 0);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		leftPanel.add(friendlyShips, c);

		// shows the health of enemy ships
		enemyShips = new JTextArea();
		enemyShips.setBackground(cream);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 20, 0);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		leftPanel.add(enemyShips, c);
		
		friendlyShipsText();
		enemyShipsText();

		// empty panel for spacing
		JPanel empty = new JPanel();
		c.fill = GridBagConstraints.BOTH;
		empty.setBackground(darkGreen);
		c.insets = new Insets(25, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = .5;
		leftPanel.add(empty, c);

	}

	/**
	 * creates the panel on the right
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createRightPanel(Container pane) {

		// creates the outer panel
		JPanel rightPanel = new JPanel(new GridBagLayout());
		rightPanel.setBackground(darkGreen);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 13;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 0.015;
		c.weighty = 1.0;
		pane.add(rightPanel, c);

		// this panel shows the history of the game
		
		moves = new JTextArea();
		moves.setLineWrap(true);
		moves.setWrapStyleWord(true);
		moves.setBackground(cream);
		moves.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(moves);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setMaximumSize(new Dimension(100, 150));
		scrollPane.setPreferredSize(new Dimension(0, 75));
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(30, 15, 10, 15);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0.9;
		rightPanel.add(scrollPane, c);

		// temporary text
		moves.append(" Place your ships!\n Right click to rotate\n");

		// displays chat history
		chatWindow = new JTextArea();
		chatWindow.setBackground(Color.decode("#FFF2CC"));
		chatWindow.setLineWrap(true);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 15, 5, 15);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		rightPanel.add(chatWindow, c);

		// area to type and send messages
		JTextField chatTypeArea = new JTextField("");
		chatTypeArea.setBackground(Color.decode("#FFF2CC"));
		chatTypeArea.addActionListener(controller);
		chatTypeArea.setActionCommand("Send Message");
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 15, 10, 15);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.05;
		rightPanel.add(chatTypeArea, c);
	}

	/**
	 * creates the main game grid
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createGameGrid(Container pane) {
		
		String letter = "";
		if (gameGrid != null) {
			pane.remove(gameGrid);
		}
		// outer panel to contain the grid
		gameGrid = new JPanel(new GridLayout(11, 11, 0, 0));
		gameGrid.setPreferredSize(new Dimension(440, 440));
		gameGrid.setBackground(darkGreen);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 20, 0);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 11;
		c.gridheight = 11;
		c.weightx = 0;
		c.weighty = 0;
		pane.add(gameGrid, c);
		// loop to fill the grid
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				
				// creates an empty panel for the corner
				if (i == 0 && j == 0) {
					JPanel corner = new JPanel();
					corner.setPreferredSize(new Dimension(40, 40));
					corner.setBackground(darkGreen);
					gameGrid.add(corner);
					// creates the numbers 1-10 in the top row
				} else if (i == 0) {
					JLabel label = new JLabel();
					label.setPreferredSize(new Dimension(40, 40));
					label.setText(Integer.toString(j));
					label.setFont(courier);
					label.setForeground(brightRed);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					gameGrid.add(label);
					// creates A -J in the first column
				} else if (j == 0) {
					JLabel label = new JLabel();
					letter = Character.toString(i + 'A' - 1);
					label.setPreferredSize(new Dimension(40, 40));
					label.setText(letter);
					label.setFont(courier);
					label.setForeground(brightRed);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					gameGrid.add(label);

				} else {
					// creates the enemy grid
					if (isEnemyGrid) {
						JPanel grid = new JPanel(new BorderLayout());
						grid.setPreferredSize(new Dimension(40, 40));
						grid.setBackground(Color.decode("#000000"));
						grid.setBorder(BorderFactory.createLineBorder(Color.decode("#7EFF00"), 1));
						grid.setName(Integer.toString(i) + ":" + Integer.toString(j));
						grid.addMouseListener(controller);
						enemyGridArray[i][j] = grid;
						gameGrid.add(grid);
						// creates the friendly grid
					} else {
						Color color;
						int randomNumber;
						Random random = new Random();
						randomNumber = random.nextInt(4);
						// randomizes the colours of the grid
						switch (randomNumber) {
						case 0:
							color = paleBlue;
							break;
						case 1:
							color = lightBlue;
							break;
						case 2:
							color = seaBlue;
							break;
						case 3:
							color = blue;
							break;
						default:
							color = blue;
						}

						JPanel grid = new JPanel(new BorderLayout());
						grid.setPreferredSize(new Dimension(40, 40));
						grid.setBackground(color);
						grid.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 1));
						grid.setName(Integer.toString(i) + ":" + Integer.toString(j));
						grid.addMouseMotionListener(controller);
						grid.addMouseListener(controller);
						
						
						friendlyGridArray[i][j] = grid;
						JLayeredPane layeredPane = new JLayeredPane();
						layeredPane.setPreferredSize(new Dimension(40, 40));
			            layeredPane.setBounds(0, 0, 40, 40);
			            friendlyGridArray[i][j].add(layeredPane);
						gameGrid.add(grid);

					}
				}
			}
		}
	}
	/**
	 * creates an imageicon from a filename
	 * @param fileName - the full path name of the file
	 * @return icon - an imageicon
	 */
	public ImageIcon createImageIcon(String fileName) {
		
		ImageIcon icon = new ImageIcon(fileName);
		return icon;
		
	}
	/**
	 * switches the grid in the view
	 */
	public void switchGrid() {
		isEnemyGrid = !isEnemyGrid;
		createGameGrid(pane);
		
		if ( isEnemyGrid ) {
			
			for ( int i = 0; i < 11; i ++ ) {
				for ( int j = 0; j < 11; j++ ) {
					int attacked = game.getPlayer1().hasAttacked(i ,j);
					if ( attacked == 1 ) {
						miss(i, j);
					}
					if ( attacked == 2 ) {
						hit(i, j);
					}
				}
			}			
		}
		else {
			for ( int i = 0; i < game.getPlayer1().getShipsPlaced(); i++ ) {
				Ship ship = game.getPlayer1().getShip(i);
				generateShip(ship.getX(), ship.getY(), i, ship.getOrientation());
			}
			for ( int i = 0; i < 11; i ++ ) {
				for ( int j = 0; j < 11; j++ ) {
					int attacked = game.getPlayer2().hasAttacked(i, j);
					if ( attacked == 1 ) {
						missPlayer(i, j);
					}
					if ( attacked == 2 ) {
						hitPlayer(i, j);
					}
				}
			}
		}
		
		pane.revalidate();
		pane.repaint();
	}
	/**
	 * rotates the ship during placement
	 */
	public void rotate() {
		
		removeHover();
		if (orientation.equals("Up")) {
			orientation = "Right";
		}
		else if (orientation.equals("Right")) {
			orientation = "Down";
		}
		else if (orientation.equals("Down")) {
			orientation = "Left";
		}
		else {
			orientation = "Up";
		}
	}
	/**
	 * creates an image of a ship where the mouse is hovering
	 * @param x - the x location of the ship
	 * @param y - the y location of the ship
	 */
	public void hoverShip(int x, int y) {
		
		removeHover();
		
		for ( int i = 0; i < game.getPlayer1().getShipsPlaced(); i++ ) {
			Ship ship = game.getPlayer1().getShip(i);
			generateShip(ship.getX(), ship.getY(), i, ship.getOrientation());
		}
		
		int shipType = game.getPlayer1().getShipsPlaced();
		
		generateShip(x, y, shipType, orientation);
		
	}
	/**
	 * paints the ships on the friendly grid after they are placed
	 * @param x - the x location of the ship
	 * @param y - the y location of the ship
	 * @param type - the type of ship as an integer
	 * @param orientation - the direction the ship is facing
	 */
	public void generateShip(int x, int y, int type, String orientation) {
		
		
		int length = 0;
		String ship = "";
		
		if ( type == 0 ) {
			ship = "battleship";
			length = 4;
		}
		else if ( type == 1) {
			ship = "carrier";
			length = 5;
		}
		else if ( type == 2 ) {
			ship = "destroyer";
			length = 3;
		}
		else if ( type == 3 ) {
			ship = "submarine";
			length = 3;
		}
		else if ( type == 4 ) {
			ship = "patrol";
			length = 2;
		}
			
		switch(orientation) {
		
		case "Up":
			if ( x + length > 11 ) {
				return;
			}
			for ( int i = 0; i < length; i ++ ) {
				
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
				JLayeredPane bottom = (JLayeredPane) friendlyGridArray[x + i][y].getComponent(0);
                bottom.add(pictures[i], JLayeredPane.DEFAULT_LAYER);
				friendlyGridArray[x + i][y].revalidate();
	            friendlyGridArray[x + i][y].repaint();
			}
			break;
		case "Right":
			if ( y - length < 0 ) {
				return;
			}
			for ( int i = 0; i < length; i ++ ) {
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
				JLayeredPane bottom = (JLayeredPane) friendlyGridArray[x][y - i].getComponent(0);
                bottom.add(pictures[i], JLayeredPane.DEFAULT_LAYER);
				friendlyGridArray[x][y - i].revalidate();
	            friendlyGridArray[x][y - i].repaint();
			}
			break;
		case "Down":
			if ( x - length < 0 ) {
				return;
			}
			for ( int i = 0; i < length; i ++ ) {
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
				JLayeredPane bottom = (JLayeredPane) friendlyGridArray[x - i][y].getComponent(0);
                bottom.add(pictures[i], JLayeredPane.DEFAULT_LAYER);
				friendlyGridArray[x - i][y].revalidate();
	            friendlyGridArray[x - i][y].repaint();
			}
			break;
		case "Left":
			if ( y + length > 11 ) {
				return;
			}
			for ( int i = 0; i < length; i ++ ) {
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
				JLayeredPane bottom = (JLayeredPane) friendlyGridArray[x][y + i].getComponent(0);
                bottom.add(pictures[i], JLayeredPane.DEFAULT_LAYER);
				friendlyGridArray[x][y + i].revalidate();
	            friendlyGridArray[x][y + i].repaint();
			}
			break;
		default:
		}
	}
	/**
	 * used to remove the hover ship image as the mouse moves around
	 */
	public void removeHover() {
		
		for ( int i = 0; i < 5; i ++ ) {
			 if (pictures[i] != null && pictures[i].getParent() != null) {
		            Container parent = pictures[i].getParent();
		            parent.remove(pictures[i]);
		            parent.revalidate();
		            parent.repaint();
		            pictures[i] = null;
			 }   
        }
    }
	/**
	 * calls generate ship to place the ship on the board
	 */
	public void paintShip() {
		
		Ship ship = game.getPlayer1().getShip(game.getPlayer1().getShipsPlaced() - 1);
		generateShip(ship.getX(), ship.getY(), game.getPlayer1().getShipsPlaced() - 1, ship.getOrientation());
	}
	/**
	 * used at the end of the game to show the location of the enemy ships
	 * @param ship - the enemy ship to paint
	 */
	public void paintEnemyShip(Ship ship) {
		
		generateEnemyShip(ship.getX(), ship.getY(), ship.getType(), ship.getOrientation());
	}
	/**
	 * generates the enemy ship graphics and places them on the grid
	 * @param x - the x location of the ship
	 * @param y - the y location of the ship
	 * @param type - the type of ship as an integer
	 * @param orientation - the direction the ship is facing
	 */
	public void generateEnemyShip(int x, int y, int type, String orientation) {
		
		
		int length = 0;
		String ship = "";
		
		if ( type == 0 ) {
			ship = "battleship";
			length = 4;
		}
		else if ( type == 1) {
			ship = "carrier";
			length = 5;
		}
		else if ( type == 2 ) {
			ship = "destroyer";
			length = 3;
		}
		else if ( type == 3 ) {
			ship = "submarine";
			length = 3;
		}
		else if ( type == 4 ) {
			ship = "patrol";
			length = 2;
		}
			
		switch(orientation) {
		
		case "Up":
			for ( int i = 0; i < length; i ++ ) {
				
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
                enemyGridArray[x + i][y].add(pictures[i]);
				enemyGridArray[x + i][y].revalidate();
	            enemyGridArray[x + i][y].repaint();
			}
			break;
		case "Right":
			for ( int i = 0; i < length; i ++ ) {
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
                enemyGridArray[x][y - i].add(pictures[i]);
				enemyGridArray[x][y - i].revalidate();
	            enemyGridArray[x][y - i].repaint();
			}
			break;
		case "Down":
			for ( int i = 0; i < length; i ++ ) {
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
                enemyGridArray[x - i][y].add(pictures[i]);
				enemyGridArray[x - i][y].revalidate();
	            enemyGridArray[x - i][y].repaint();
			}
			break;
		case "Left":
			for ( int i = 0; i < length; i ++ ) {
				pictures[i] = new JLabel(createImageIcon("icons/" + ship + (i + 1) + orientation + ".png"));
				pictures[i].setBounds(0, 0, 40, 40);
                enemyGridArray[x][y + i].add(pictures[i]);
				enemyGridArray[x][y + i].revalidate();
	            enemyGridArray[x][y + i].repaint();
			}
			break;
		default:
		}
	}
	/**
	 * paints an explosion graphic when the user hits an enemy ship
	 * @param x - the x location of the hit
	 * @param y - the y location of the hit
	 */
	public void hit(int x, int y) {	
		JLabel pic = new JLabel(createImageIcon("icons/explosion.png"));
		enemyGridArray[x][y].add(pic);
		enemyGridArray[x][y].revalidate();
		enemyGridArray[x][y].repaint();
	}
	/**
	 * paints a crosshair graphic when the user misses
	 * @param x - the x location of the miss
	 * @param y - the y location of the miss
	 */
	public void miss(int x, int y) {
		JLabel pic = new JLabel(createImageIcon("icons/crosshair.png"));
		enemyGridArray[x][y].add(pic);
		enemyGridArray[x][y].revalidate();
		enemyGridArray[x][y].repaint();
	}
	/**
	 * paints a fire graphic on top of the friendly ship with the enemy hits
	 * @param x - the x location of the hit
	 * @param y - the y location of the hit
	 */
	public void hitPlayer(int x, int y) {
		JLabel pic = new JLabel(createImageIcon("icons/fire.png"));
		JLayeredPane layeredPane = (JLayeredPane) friendlyGridArray[x][y].getComponent(0);
		pic.setBounds(0, 0, 40, 40);
	    layeredPane.add(pic, JLayeredPane.PALETTE_LAYER);
		layeredPane.revalidate();
		layeredPane.repaint();
	}
	/**
	 * paints a crosshair when the enemy misses
	 * @param x - the x location of the miss
	 * @param y - the y location of the miss
	 */
	public void missPlayer(int x, int y) {
		JLabel pic = new JLabel(createImageIcon("icons/crosshair2.png"));
		friendlyGridArray[x][y].add(pic);
	}
	/**
	 * close the opening screen
	 */
	public void closeSplash() {
		splash.dispose();
	}
	/**
	 * when the user wins the game this method creates a JDialog with a message
	 */
	public void winGame() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		JDialog youWin = new JDialog(frame, "Congratulations!", true);
		youWin.setLocationRelativeTo(frame);
		youWin.setSize(300, 200);
		
		JLabel winMessage = new JLabel("Congrats you won!");
		youWin.add(winMessage);
		
		youWin.setVisible(true);
	}
	/**
	 * when the user loses the game this method creates a JDialog with a message
	 */
	public void loseGame() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		JDialog youLose = new JDialog(frame, "Sorry!", true);
		youLose.setLocationRelativeTo(frame);
		youLose.setSize(300, 200);
		
		JLabel loseMessage = new JLabel("You LOSE");
		youLose.add(loseMessage);
		
		youLose.setVisible(true);
	}
	/**
	 * sets the text of the friendly ship status
	 */
	public static void friendlyShipsText() {
		friendlyShips.setText("");
		friendlyShips.append(" " + bundle.getString("your") + " " + bundle.getString("ships") + ":\n");
		friendlyShips.append(" " + bundle.getString("carrier") + " " + game.getPlayer1().getShip(1).getHealth() + "/5\n");
		friendlyShips.append(" " + bundle.getString("battleship") + " " + game.getPlayer1().getShip(0).getHealth() + "/4\n");
		friendlyShips.append(" " + bundle.getString("destroyer") + " " + game.getPlayer1().getShip(2).getHealth() + "/3\n");
		friendlyShips.append(" " + bundle.getString("submarine") + " " + game.getPlayer1().getShip(3).getHealth() + "/3\n");
		friendlyShips.append(" " + bundle.getString("frigate") + " " + game.getPlayer1().getShip(4).getHealth() + "/2\n");
	}
	/**
	 * sets the text of the enemy ship status
	 */
	public static void enemyShipsText() {
		
		String SUNK = bundle.getString("destroyed");
		String NOTSUNK = bundle.getString("operational");
		String text = bundle.getString("operational");
		enemyShips.setText("");
		
		enemyShips.append(" " + bundle.getString("enemy") + " " + bundle.getString("ships") + ":\n");
		if ( game.getPlayer2().getShip(1).getHealth() == 0 ) {
			text = SUNK;
		}
		else {
			text = NOTSUNK;
		}
		enemyShips.append(" " + bundle.getString("carrier") + " " + text + "\n");
		if ( game.getPlayer2().getShip(0).getHealth() == 0 ) {
			text = SUNK;
		}
		else {
			text = NOTSUNK;
		}
		enemyShips.append(" " + bundle.getString("battleship") + " " + text + "\n");
		if ( game.getPlayer2().getShip(2).getHealth() == 0 ) {
			text = SUNK;
		}
		else {
			text = NOTSUNK;
		}
		enemyShips.append(" " + bundle.getString("destroyer") + " " + text + "\n");
		if ( game.getPlayer2().getShip(3).getHealth() == 0 ) {
			text = SUNK;
		}
		else {
			text = NOTSUNK;
		}
		enemyShips.append(" " + bundle.getString("submarine") + " " + text + "\n");
		if ( game.getPlayer2().getShip(4).getHealth() == 0 ) {
			text = SUNK;
		}
		else {
			text = NOTSUNK;
		}
		enemyShips.append(" " + bundle.getString("frigate") + " " + text + "\n");
	}
	/**
	 * updates the move text area whenever an action is taken
	 * @param player - the player performing the action
	 * @param x - the x location of the action
	 * @param y - the y location of the action
	 * @param hit - true if hit, false if miss
	 */
	public void updateMoves(Player player, int x, int y, Boolean hit) {
		String text;
		String hitOrMiss;
		if ( hit == true ) {
			hitOrMiss = bundle.getString("hitat");
		}
		else {
			hitOrMiss = bundle.getString("missedat");
		}
		text = player.getName() + " " + hitOrMiss + " " + Character.toString(x + 'A' - 1) + Integer.toString(y) + "\n";
		moves.append(text);
		moves.setCaretPosition(moves.getDocument().getLength());
	}
	/**
	 * when a player sinks a ship this method appends a message to moves
	 * @param player - the player sinking the ship
	 * @param ship - the ship that is sunk
	 */
	public void updateSunk(Player player, int ship) {
		
		if ( player == game.getPlayer1() ) {
			player = game.getPlayer2();
		} else {
			player = game.getPlayer1();
		}
		
		String shipName = "";
		switch(ship) {
		case 0:
			shipName = bundle.getString("battleship");
			break;
		case 1:
			shipName = bundle.getString("carrier");
			break;
		case 2:
			shipName = bundle.getString("destroyer");
			break;
		case 3:
			shipName = bundle.getString("submarine");
			break;
		case 4:
			shipName = bundle.getString("frigate");
			break;
		default: 
				
		}
		moves.append(player.getName() + " " + bundle.getString("hassunka") + " " + shipName + "\n");
	}
	/**
	 * adds a message to the chat window
	 * @param message - the message to add from the text field
	 */
	public void chat(String message) {
		chatWindow.append(game.getPlayer1().getName() + ": " + message + "\n");
	}
	/**
	 * changes the language of the game
	 * @param bundle - the resource bundle with the language locale
	 */
	public void changeLanguage(ResourceBundle bundle) {
		UserInterface.bundle = bundle;
		friendlyShipsText();
		enemyShipsText();
		swapBoards.setText(bundle.getString("swapboards"));
		createMenu(pane);
		gameInfo.setText("\n   " + game.getTurn().getName() + "'s " + bundle.getString("turn"));
		
	}
	/**
	 * closes the main window
	 */
	public void closeGame() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(pane);
		frame.dispose();
	}
	/**
	 * gets the time from the timers and displays them in the game info box
	 */
	public static void gameTimer() {
		
		int time = gameTimer.getTime();
		String formattedTime = formatTime(time);
		int playerTime = playerTimer.getTime();
		String playerFormattedTime = formatTime(playerTime);
		
		gameInfo.setText("<html>" + game.getTurn().getName() + "'s " + bundle.getString("turn") + "<br><br>" 
		+ bundle.getString("game") + " " + bundle.getString("timer") + "<br>" 
		+ formattedTime + "<br>" 
		+ bundle.getString("player") + " " + bundle.getString("timer") + "<br>" 
		+ playerFormattedTime + "</html>");

	}
	/**
	 * formats seconds into minutes:seconds
	 * @param time - the time in seconds
	 * @return - the formatted string
	 */
	public static String formatTime(int time) {
		
		int seconds = time % 60;
		int minutes = time / 60;
		String formatTime = String.format("%02d:%02d", minutes, seconds);
		
		return formatTime;
	}

	/**
	 * creates the GUI, the execute method calls it
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Battleship");
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setLocation(100, 100);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createGame(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * invoked from main, launches the UI
	 */
	public void execute() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
