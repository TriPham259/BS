
public class Student extends Thread {
	private MensaMain mensa; 
	
	public Student(String name, MensaMain mensa) {
		super(name);
		this.mensa = mensa; 
	}
	
	/** 
	 * Lasse den Studenten die Mensa benutzen, essen, 
	 * sich ausruhen, wiederkommen, bis Mensa geschlossen ist. 
	 */
	public void run() {
		try {
			while (!Thread.interrupted()) {
				mensa.use();	
				mensa.eat();
				mensa.rest();
			}
		} catch (InterruptedException e) {
			System.out.println("            " + getName() + " won't come back, as Mensa is closed.");
		}
	}	
}
