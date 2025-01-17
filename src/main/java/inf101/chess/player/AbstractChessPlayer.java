package inf101.chess.player;

/**
 * An abstract class by all kinds of ChessPlayers.
 * Heavily inspired by AbstractPlayer, author:
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public abstract class AbstractChessPlayer implements ChessPlayer {

	protected char symbol;
	protected String name;

	public AbstractChessPlayer(char symbol) {
		this(symbol, "Player " + symbol);
	}

	public AbstractChessPlayer(char symbol, String name) {
		this.symbol = symbol;
		this.name = name;
	}

	@Override
	public char getSymbol() {
		return symbol;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractChessPlayer) {
			AbstractChessPlayer player = (AbstractChessPlayer) obj;
			return this.symbol == player.symbol;
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Checks if a given String is a valid player name
	 *
	 * @param name - the name to check
	 * @return the name if it is valid, otherwise an Exception will be thrown
	 */
	public static String validateName(String name) {
		if (!isValidName(name)) {
			throw new IllegalArgumentException("Name can not be blank");
		}
		return name;
	}

	/**
	 * Checks if a given string is a valid player name
	 *
	 * @param name - the name to check
	 * @return true if the name is valid, false otherwise.
	 */
	public static boolean isValidName(String name) {
		return name != null && !name.isBlank();
	}
}
