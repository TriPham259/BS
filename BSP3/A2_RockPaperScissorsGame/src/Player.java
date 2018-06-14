import java.util.Random;

/**
 * Player ist der Erzeuger-Thread im Erzeuger/Verbrauchersystem.
 * 
 * @author Huy Tran PC
 */
public class Player extends Thread {
	// maximale Pause zwischen den Pufferzugriffen
	public final int MAX_IDLE_TIME = 100;
	private Hand hand;
	private BoundedBuffer<Hand> buffer;

	public Player(String name, BoundedBuffer<Hand> buffer) {
		super(name);
		this.buffer = buffer;
	}

	/**
     * Erzeuge Hand-Objekt und lege es in den Puffer. 
     * Halte nach jeder Ablage fuer eine Zufallszeit an.
	 */
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// erzeuge Hand-Objekt
				hand = chooseHand();
				
				printAccessRequestMessage();
			
				// rufe Puffer-Zugriffsmethode auf --> Synchronisation über den Puffer!
				buffer.enter(hand);
				
				// halte für eine Zufallszeit an
				pause();
			}
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + " is successfully interrupted.");
		}

	}

	/**
	 * Wähle zufällig eine Hand aus.
	 * @return Eine Hand aus Enum Hand.
	 */
	public Hand chooseHand() {
		Hand[] hands = Hand.values();
		return hands[new Random().nextInt(hands.length)];
	}
	
	/** 
	 * Gib einen Zugriffswunsch auf der Konsole aus. 
	 */
	public void printAccessRequestMessage() {
		System.err.println("                    " + this.getName() + " möchte auf den Puffer zugreifen!");
	}

	/**
	 * Blockiere den Verbraucher für eine Zufallszeit. 
	 */
	public void pause() throws InterruptedException { 
		Thread.sleep((int) (MAX_IDLE_TIME * Math.random()));
	}
	
	public BoundedBuffer<Hand> getBuffer() {
		return buffer;
	}
}
