import java.util.Random;

/**
 * Die Klasse simuliert ein Rennauto.
 * 
 * @author Huy Tran PC
 *
 */
public class Car extends Thread{
	private String name; 
	private int totalTime; 
	
	public Car(String name) {
		this.name = name; 
	}
	
	@Override
	public void run() {
		for (int i = 0; i < SimRace.NUM_OF_LAPS; i++) {
			int lapTime = new Random().nextInt(100);
			
			try {
				Thread.sleep(lapTime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			totalTime += lapTime;
		}
		
	}

	public String getCarName() {
		return name;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
}
