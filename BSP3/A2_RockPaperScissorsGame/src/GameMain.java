
/**
 * Main-Thread zum Durchführen des Spiels. 
 * @author Huy Tran PC
 */
public class GameMain extends Thread {
	// Spieldauer
	private static final int GAME_DURATION = 5000;

	private Player player1;
	private Player player2;
	private Referee referee;

	public static void main(String[] args) {
		new GameMain().start();
	}

	public void run() {
		System.out.println("Game started!");
		
		BoundedBuffer<Hand> bufferMonitor = new BoundedBufferSyncMonitor<>(1);
		BoundedBuffer<Hand> bufferCondQueues = new BoundedBufferSyncCondQueues<>(1);

		player1 = new Player("Huy", bufferMonitor);
		player2 = new Player("Tri", bufferCondQueues);
		referee = new Referee("Collina", player1, player2);

		startThreads();
		runGame();
		stopThreads();
		waitThreads();
		printResult();
	}

	/**
	 * Starte die Threads.
	 */
	private void startThreads() {
		referee.start();
		player1.start();
		player2.start();
	}

	/**
	 * Lasse das Spiel für eine bestimmte Zeit laufen.
	 */
	private void runGame() {
		try {
			Thread.sleep(GAME_DURATION);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Stoppe die Threads.
	 */
	private void stopThreads() {
		referee.interrupt();
		player1.interrupt();
		player2.interrupt();
	}

	/**
	 * Warte bis die Threads stoppen.
	 */
	private void waitThreads() {
		try {
			referee.join();
			player1.join();
			player2.join();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Drucke das Gesamtergebnis aus.
	 */
	private void printResult() {
		System.out.println("Game ended!\n");
		System.out.println("Result:");
		System.out.printf("%s won %d times.\n", player1.getName(), referee.getWin1());
		System.out.printf("%s won %d times.\n", player2.getName(), referee.getWin2());
		System.out.printf("Draw: %d\n", referee.getDraw());
	}
}
