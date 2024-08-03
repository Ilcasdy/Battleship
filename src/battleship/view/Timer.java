package battleship.view;
/**
 * Makes a timer thread for the battleship game
 */
public class Timer extends Thread{
	
	private int time = 0;
	private Boolean main = false;
	private Boolean start = false;
	/**
	 * default constructor
	 */
	public Timer() {
		
	}
	/**
	 * gets the time
	 * @return time - time in seconds
	 */
	public int getTime() {
		return time;
	}
	/**
	 * sets the time
	 * @param time in seconds
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * determines if this the main game timer
	 * @param main - true if is the game timer
	 */
	public void setMain(Boolean main) {
		this.main = main;
	}
	/**
	 * determines if it is the timer for the splash screen
	 * @param start - true if it is the splash screen timer
	 */
	public void setStart(Boolean start) {
		this.start = start;
	}
	/**
	 * "main" method for the timer thread
	 */
	public void run() {
		
		if ( start == true ) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			UserInterface.closeStart();
		}
		
		while(true) {
			try {
				Thread.sleep(1000);
				time++;
				if ( main == true ) {
					UserInterface.gameTimer();
				}
			} catch (InterruptedException e) {
				
			}
		}
	}

}
