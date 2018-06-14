import java.util.concurrent.locks.ReentrantLock;

public class Kasse {
	private String name; 
	private ReentrantLock kasseLock; 
	private int lineLength; 
	private int purchaseNum; 
	
	public Kasse(String name) {
		this.name = name; 
		this.kasseLock = new ReentrantLock(true);
	}
	
	public String getName() {
		return name; 
	}
	
	public int getLineLength() {
		return lineLength;
	}
	
	public int getPurchaseNum() {
		return purchaseNum;
	}
	
	/**
	 * Lasse einen Student sich an der Kasse anstellen.
	 */
	public void lineUp() {
		lineLength++;
		System.out.printf("[|%s| = %d]+ %s lines up at %s.\n", name, lineLength, Thread.currentThread().getName(), name);
	} 
	
	/**
	 * Lasse einen Student an der Kasse bezahlen und verlassen.
	 * Weitere Studenten können erst bezahlen, nur wenn der Student die Kasse verlässt.
	 */
	public void payAndLeave() throws InterruptedException {
		kasseLock.lock();
		
		try {
			purchaseNum++;
			System.out.printf("[|%s| = %d]~ %s pays for %s.\n", name, lineLength, Thread.currentThread().getName(), name);
			Thread.sleep(MensaMain.PURCHASE_DURATION);
		} finally {
			lineLength--;
			System.out.printf("[|%s| = %d]- %s leaves %s.\n", name, lineLength, Thread.currentThread().getName(), name);
			kasseLock.unlock();
		}
		
	}

}
