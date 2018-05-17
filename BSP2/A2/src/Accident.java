import java.util.Random;

/**
 * Die Klasse simuliert einen Unfall. 
 * 
 * @author Huy Tran PC
 *
 */
public class Accident extends Thread{
	private Thread car; 
	
	public Accident(Thread car) {
		this.car = car; 
	}

	@Override
	public void run() {	
		try {
			Thread.sleep(new Random().nextInt(SimRace.ACCIDENT_WITHIN));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		car.interrupt();
	}
	
	
}
