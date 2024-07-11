package battleship.view;

public class Timer extends Thread{
	
	int time = 0;
	Boolean main = false;
	Boolean start = false;
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public void setMain(Boolean main) {
		this.main = main;
	}
	public void setStart(Boolean start) {
		this.start = start;
	}
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
