
public enum Hand {
	ROCK, PAPER, SCISSORS; 
	
	/**
	 * Get the result of a fight between 2 hands.
	 * @param otherHand The other Hand.
	 * @return -1 if this hand loses, 0 if this is a draw and 1 if this hand wins.
	 */
	public int fight(Hand otherHand) {
		int[][] rules = {{0, -1, 1}, {1, 0, -1}, {-1, 1, 0}};
		return rules[this.ordinal()][otherHand.ordinal()];
	}
	
}
