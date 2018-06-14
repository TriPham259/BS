
/**
 * Referee ist der Verbraucher-Thread im Erzeuger/Verbrauchersystem.
 * @author Huy Tran PC
 */
public class Referee extends Thread {
	// maximale Pause zwischen den Pufferzugriffen
	private static final double MAX_IDLE_TIME = 100;
	
	private Player player1;
	private Player player2;

	private int win1;
	private int win2;
	private int draw;
	
	private Hand hand1; 
	private Hand hand2; 

	public Referee(String name, Player player1, Player player2) {
		super(name);
		this.player1 = player1;
		this.player2 = player2;
		this.win1 = 0;
		this.win2 = 0;
		this.draw = 0;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				printAccessRequestMessage();
				
				// Entnimm Hand-Objekt dem Puffer, 
				// ruf dazu Puffer-Zugriffsmethode auf
                // --> Synchronisation über den Puffer!
				hand1 = player1.getBuffer().remove();
				hand2 = player2.getBuffer().remove();
				
				workOutTheResult();
				
				// halte für eine Zufallszeit an
				pause();
			}
		} catch (InterruptedException e) {
			System.out.println(getName() + " is successfully interrupted.");
		}
	}

	/**
	 * Bearbeite die Runde. 
	 */
	public synchronized void workOutTheResult() {
		System.out.printf("%s (%s) vs %s (%s)\n", player1.getName(), hand1, player2.getName(), hand2);

		if (hand1.fight(hand2) > 0) {
			win1++;
			System.out.printf("%s beat %s.\n", player1.getName(), player2.getName());
		} else if (hand1.fight(hand2) < 0) {
			win2++;
			System.out.printf("%s beat %s.\n", player2.getName(), player1.getName());
		} else {
			draw++;
			System.out.println("It's a draw.");
		}
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
	
	public int getWin1() {
		return win1;
	}

	public int getWin2() {
		return win2;
	}

	public int getDraw() {
		return draw;
	}

}
