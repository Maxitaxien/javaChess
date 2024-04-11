package inf101.sem2.player.ai;

import inf101.sem2.game.ChessGame;
import inf101.sem2.player.AbstractChessPlayer;

public class DumbChessPlayer extends AbstractChessPlayer {
	
	static int counter = 1;

	public DumbChessPlayer(char symbol) {
		super(symbol, "DumbPlayer " + counter++);
	}

	@Override
	public <T> T getMove(ChessGame<T> game) {
		for (T move : game.getPossibleMoves()) {
			return move;
		}
		return null;
	}
}
