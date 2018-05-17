import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Das Programm simuliert ein Autorennen.
 * 
 * @author Huy Tran PC
 *
 */

public class SimRace {
	// setze Anzahl der AUtos und Runden
	public static final int NUM_OF_CARS = 5;
	public static final int NUM_OF_LAPS = 10;

	// schalte Unfallsmodus um
	public static final boolean ACCIDENT_MODE = false;
	public static final int ACCIDENT_WITHIN = 200;
	private boolean crashed;

	private List<Car> cars = new ArrayList<>();

	public static void main(String[] args) {
		new SimRace().start();
	}

	/**
	 * Starte das Rennen.
	 */
	private void start() {
		if (ACCIDENT_MODE) {
			new Accident(Thread.currentThread()).start();;
		}

		startCars();
		waitCars();
		printResult();
	}

	/**
	 * Starte die Autos gleichzeitig. 
	 */
	private void startCars() {
		for (int i = 0; i < NUM_OF_CARS; i++) {
			Car car = new Car("Wagen " + (i + 1)); 
			cars.add(car);
			car.start();
		}
	}

	/**
	 * Warte bis alle Autos das Ziel erreicht. 
	 */
	private void waitCars() {
		try {
			for (Car car : cars) {
				car.join();
			}
		} catch (InterruptedException e) {
			// stoppe alle Autos beim Unfall 
			crashed = true;

			for (Car car : cars) {
				car.interrupt();
			}
		}
	}

	/**
	 * Drucke das Gesamtergebnistabelle aus. 
	 */
	private void printResult() {
		if (crashed) {
			System.out.println("Hello Darkness, my old friend.");
		} else {
			System.out.println("**** Endstand ****");
			
			// sortiere Autos nach Gesamtfahrzeit
			cars.sort(Comparator.comparing(Car::getTotalTime));

			for (int i = 0; i < cars.size(); i++) {
				System.out.printf("%d. Platz: %s Zeit: %d\n", i + 1, cars.get(i).getCarName(), cars.get(i).getTotalTime());
			}
		}
	}

}
