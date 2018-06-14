import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Mensa erhält die main-Methode zum Ausfüren des Programms.
 * @author Huy Tran PC
 *
 */
public class MensaMain extends Thread{
	// Basiseinstellung
	public static final int NUM_OF_KASSEN = 4; 
	public static final int NUM_OF_STUDENTS = 100; 
	public static final int MENSA_DURATION = 5000;
	public static final int PURCHASE_DURATION = 1000;
	
	private List<Kasse> kassen = new ArrayList<>();
	private List<Student> students = new ArrayList<>(); 
	private ReentrantLock kasseLock = new ReentrantLock(true);

	/**
	 * Starte die Mensa-Thread. 
	 */
	public static void main(String[] args) {
		new MensaMain().start();
	}
	
	public void run() {
		openKassen();
		startStudents();
		runMensa();
		stopStudents();
		waitStudents();
		printPurchaseResult();
	}
	
	/**
	 * Lasse jeden Studenten sich an der Kasse mit der kürzeste 
	 * Warteschlange anstellen, bezahlen und verlassen.  
	 */
	public void use() throws InterruptedException {
		kasseLock.lock();
		
		// finde die kürzeste Warteschlange
		Kasse kasseMin = null; 
		for (Kasse kasse : kassen) {
			if (kasseMin == null || kasseMin.getLineLength() > kasse.getLineLength()) {
				kasseMin = kasse;
			}
		}

		kasseMin.lineUp();
		kasseLock.unlock();
		
		kasseMin.payAndLeave();
	}
	
	/** 
	 * Lasse den Studenten für eine Zufallszeit essen. 
	 */
	public void eat() throws InterruptedException {
		Thread.sleep((int)(Math.random() * MENSA_DURATION));
	}
	
	/**
	 * Lasse den Studenten eine Pause für eine Zufallszeit machen, bevor er wiederkommt. 
	 */
	public void rest() throws InterruptedException {
		Thread.sleep((int)(Math.random() * MENSA_DURATION));
	}
	
	/**
	 * Öffnete Kassen.  
	 */
	private void openKassen() {
		for (int i = 0; i < NUM_OF_KASSEN; i++) {
			kassen.add(new Kasse("Kasse " + (i + 1)));
		}
	}
	
	/**
	 * Starte die Studente-Threads.
	 */
	private void startStudents() {
		for (int i = 0; i < NUM_OF_STUDENTS; i++) {
			Student student = new Student("Student " + (i + 1), this);
			students.add(student);
			student.start();
		}
	}
	
	/**
	 * Lasse die Mensa zu einer bestimmten Zeit laufen. 
	 */
	private void runMensa() {
		try {
			Thread.sleep(MENSA_DURATION);
		} catch (InterruptedException e) {
		}
		System.out.println("XXXXXXXX Mensa is now closed! XXXXXXXX");
	}
	
	/**
	 * Stoppe die Student-Threads.
	 */
	private void stopStudents() {
		for (Student student : students) {
			student.interrupt();
		}
	}
	
	/**
	 * Warte bis alle Student-Threads stoppen. 
	 */
	private void waitStudents() {
		for (Student student : students) {
			try {
				student.join();
			} catch (InterruptedException e) {
			}
		}
	}
	
	/** 
	 * Drucke die Anzahl der Bezahlvorgänge jeder Kasse aus. 
	 */
	private void printPurchaseResult() {
		System.out.println("\nNumber of purchases at each Kasse:");
		for (Kasse kasse : kassen) {
			System.out.println(kasse.getName() + ": " + kasse.getPurchaseNum());
		}
	}	
}














