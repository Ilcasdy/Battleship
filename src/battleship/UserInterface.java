
/* 
 * fire, explosion, and water art from https://www.pngwing.com/en/free-png-bnxfk
 * ship art by Lowder 2 https://opengameart.org/content/sea-warfare-set-ships-and-more
*/
package battleship;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * View for the game
 * @author Sean Bradbury
 */
public class UserInterface extends JFrame {

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
	public static JLabel picture;

	/**
	 * default constructor
	 */
	public UserInterface() {
	}

	/**
	 * this function creates the view of the game
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createGame(Container pane) {

		isEnemyGrid = false;

		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pane.setLayout(new GridBagLayout());
		pane.setBackground(darkGreen);

		createMenu(pane);
		createLogo(pane);
		createSwap(pane);
		createLeftPanel(pane);
		createGameGrid(pane);
		createRightPanel(pane);

	}

	/**
	 * Creates the top left menu
	 * 
	 * @param pane - the frame of the ui
	 */
	public static void createMenu(Container pane) {

		// Create the menu button
		JButton menuButton = new JButton("Menu");
		menuButton.setBackground(cream);
		menuButton.setPreferredSize(new Dimension(80, 20));
		menuButton.setFont(menuFont);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		pane.add(menuButton, c);

		// Creates the drop down menus
		JPopupMenu dropDown = new JPopupMenu();
		dropDown.setPreferredSize(new Dimension(110, 180));
		dropDown.setBackground(cream);
		pane.add(dropDown, c);

		JPopupMenu networkMenu = new JPopupMenu();
		networkMenu.setPreferredSize(new Dimension(130, 80));
		networkMenu.setBackground(cream);
		dropDown.add(networkMenu, c);

		JPopupMenu languageMenu = new JPopupMenu();
		languageMenu.setPreferredSize(new Dimension(110, 60));
		languageMenu.setBackground(cream);
		dropDown.add(languageMenu, c);

		// labels for the menu options
		JLabel save = new JLabel(" Save");
		save.setFont(menuFont);
		dropDown.add(save, c);
		JLabel load = new JLabel(" Load");
		load.setFont(menuFont);
		dropDown.add(load);
		JLabel restart = new JLabel(" Restart");
		restart.setFont(menuFont);
		dropDown.add(restart);
		JLabel network = new JLabel(" Network");
		network.setFont(menuFont);
		dropDown.add(network);
		JLabel language = new JLabel(" Language");
		language.setFont(menuFont);
		dropDown.add(language);
		JLabel about = new JLabel(" About");
		about.setFont(menuFont);
		dropDown.add(about);
		JLabel help = new JLabel(" Help");
		help.setFont(menuFont);
		dropDown.add(help);
		JLabel close = new JLabel(" Close");
		close.setFont(menuFont);
		dropDown.add(close);

		// labels for the network menu
		JLabel host = new JLabel(" Host");
		host.setFont(menuFont);
		networkMenu.add(host);
		JLabel connect = new JLabel(" Connect");
		connect.setFont(menuFont);
		networkMenu.add(connect);
		JLabel disconnect = new JLabel(" Disconnect");
		disconnect.setFont(menuFont);
		networkMenu.add(disconnect);

		// labels for the language menu
		JLabel english = new JLabel(" English");
		english.setFont(menuFont);
		languageMenu.add(english);
		JLabel spanish = new JLabel(" Spanish");
		spanish.setFont(menuFont);
		languageMenu.add(spanish);

		/**
		 * opens and closes the drop down menu
		 */
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!dropDown.isVisible()) {
					dropDown.show(menuButton, 0, 20);
				} else {
					dropDown.setVisible(false);

				}
			}
		});
		/**
		 * Opens the network menu
		 */
		network.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				networkMenu.show(menuButton, 0, 20);
			}
		});
		/**
		 * opens the language menu
		 */
		language.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				languageMenu.show(menuButton, 0, 20);
			}
		});

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
		JButton swapBoards = new JButton("Swap Boards");
		ImageIcon circleButton = new ImageIcon("icons/circlebutton3.png");
		
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

		// when clicked, swaps the boards and repaints
		swapBoards.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isEnemyGrid = !isEnemyGrid;
				createGameGrid(pane);
				pane.revalidate();
				pane.repaint();
			}
		});
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
		JTextArea gameInfo = new JTextArea();
		gameInfo.setPreferredSize(new Dimension(0, 110));
		gameInfo.setBackground(cream);
		gameInfo.setFont(timeFont);
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(30, 10, 10, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		leftPanel.add(gameInfo, c);

		// temporary text
		gameInfo.append("\n    Player 1's Turn\n");
		gameInfo.append("          3:21\n\n");
		gameInfo.append("       Game timer\n         11:45");

		// shows the health of friendly ships
		JTextArea friendlyShips = new JTextArea();
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

		// temporary text
		friendlyShips.append(" Your ships:\n");
		friendlyShips.append(" Carrier 5/5\n");
		friendlyShips.append(" Battleship 4/4\n");
		friendlyShips.append(" Destroyer 3/3\n");
		friendlyShips.append(" Submarine 3/3\n");
		friendlyShips.append(" Frigate 2/2\n");

		// shows the health of enemy ships
		JTextArea enemyShips = new JTextArea();
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

		// temporary text
		enemyShips.append(" Enemy ships:\n");
		enemyShips.append(" Carrier 5/5\n");
		enemyShips.append(" Battleship 4/4\n");
		enemyShips.append(" Destroyer 3/3\n");
		enemyShips.append(" Submarine 3/3\n");
		enemyShips.append(" Frigate 2/2\n");

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
		c.weightx = 1.0;
		c.weighty = 1.0;
		pane.add(rightPanel, c);

		// this panel shows the history of the game
		JTextArea moves = new JTextArea();
		moves.setBackground(Color.decode("#FFF2CC"));
		moves.setMaximumSize(new Dimension(100, 150));
		moves.setPreferredSize(new Dimension(0, 75));
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(30, 15, 10, 15);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0.9;
		rightPanel.add(moves, c);

		// temporary text
		moves.append(" Place your ships\n");
		moves.append(" Player 1 missed at B4\n");
		moves.append(" Player 2 hit at E3\n");

		// displays chat history
		JTextArea chatWindow = new JTextArea();
		chatWindow.setBackground(Color.decode("#FFF2CC"));
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 15, 5, 15);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		rightPanel.add(chatWindow, c);

		// temporary text
		chatWindow.append(" Player 1: Hello!\n");
		chatWindow.append(" Player 2: gg\n");

		// area to type and send messages
		JTextField chatTypeArea = new JTextField("");
		chatTypeArea.setBackground(Color.decode("#FFF2CC"));
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
					label.setPreferredSize(new Dimension(40, 40));
					label.setText(Character.toString(i + 'A' - 1));
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
						friendlyGridArray[i][j] = grid;
						gameGrid.add(grid);

					}
				}
			}
		}
		// image icons used in the grid
		ImageIcon explosionIcon = new ImageIcon("icons/explosion.png");
		ImageIcon crosshairIcon = new ImageIcon("icons/crosshair.png");
		ImageIcon crosshair2Icon = new ImageIcon("icons/crosshair2.png");
		ImageIcon battleship1UpIcon = new ImageIcon("icons/battleship1Up.png");
		ImageIcon battleship2UpIcon = new ImageIcon("icons/battleship2Up.png");
		ImageIcon battleship3UpIcon = new ImageIcon("icons/battleship3Up.png");
		ImageIcon battleship4UpIcon = new ImageIcon("icons/battleship4Up.png");
		ImageIcon submarine1LeftIcon = new ImageIcon("icons/submarine1Left.png");
		ImageIcon submarine2LeftIcon = new ImageIcon("icons/submarine2Left.png");
		ImageIcon submarine3LeftIcon = new ImageIcon("icons/submarine3Left.png");
		ImageIcon carrier1UpIcon = new ImageIcon("icons/carrier1Up.png");
		ImageIcon carrier2UpIcon = new ImageIcon("icons/carrier2Up.png");
//		ImageIcon carrier3UpIcon = new ImageIcon("icons/carrier3Up.png");
//		ImageIcon carrier4UpIcon = new ImageIcon("icons/carrier4Up.png");
		ImageIcon carrier5UpIcon = new ImageIcon("icons/carrier5Up.png");
//		ImageIcon carrier1UpFireIcon = new ImageIcon("icons/carrier1UpFire.png");
//		ImageIcon carrier2UpFireIcon = new ImageIcon("icons/carrier2UpFire.png");
		ImageIcon carrier3UpFireIcon = new ImageIcon("icons/carrier3UpFire.png");
		ImageIcon carrier4UpFireIcon = new ImageIcon("icons/carrier4UpFire.png");
//		ImageIcon carrier5UpFireIcon = new ImageIcon("icons/carrier5UpFire.png");
		ImageIcon patrol1LeftIcon = new ImageIcon("icons/patrol1Left.png");
		ImageIcon patrol2LeftIcon = new ImageIcon("icons/patrol2Left.png");

		// temporary code to show art
		if (isEnemyGrid) {
			picture = new JLabel(explosionIcon);
			enemyGridArray[8][2].add(picture);
			picture = new JLabel(explosionIcon);
			enemyGridArray[7][2].add(picture);
			picture = new JLabel(crosshairIcon);
			enemyGridArray[6][9].add(picture);
			picture = new JLabel(crosshairIcon);
			enemyGridArray[1][7].add(picture);
		} else {
			picture = new JLabel(crosshair2Icon);
			friendlyGridArray[6][9].add(picture);
			picture = new JLabel(crosshair2Icon);
			friendlyGridArray[1][7].add(picture);

			picture = new JLabel(battleship1UpIcon);
			friendlyGridArray[2][4].add(picture);
			picture = new JLabel(battleship2UpIcon);
			friendlyGridArray[3][4].add(picture);
			picture = new JLabel(battleship3UpIcon);
			friendlyGridArray[4][4].add(picture);
			picture = new JLabel(battleship4UpIcon);
			friendlyGridArray[5][4].add(picture);

			picture = new JLabel(submarine1LeftIcon);
			friendlyGridArray[7][8].add(picture);
			picture = new JLabel(submarine2LeftIcon);
			friendlyGridArray[7][9].add(picture);
			picture = new JLabel(submarine3LeftIcon);
			friendlyGridArray[7][10].add(picture);

			picture = new JLabel(carrier1UpIcon);
			friendlyGridArray[5][2].add(picture);
			picture = new JLabel(carrier2UpIcon);
			friendlyGridArray[6][2].add(picture);
			picture = new JLabel(carrier3UpFireIcon);
			friendlyGridArray[7][2].add(picture);
			picture = new JLabel(carrier4UpFireIcon);
			friendlyGridArray[8][2].add(picture);
			picture = new JLabel(carrier5UpIcon);
			friendlyGridArray[9][2].add(picture);

			picture = new JLabel(patrol1LeftIcon);
			friendlyGridArray[5][6].add(picture);
			picture = new JLabel(patrol2LeftIcon);
			friendlyGridArray[5][7].add(picture);

		}
	}

	/**
	 * creates the GUI, the execute method calls it
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Battleship");
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createGame(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * invoked from main, launches the UI
	 */
	public static void execute() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
