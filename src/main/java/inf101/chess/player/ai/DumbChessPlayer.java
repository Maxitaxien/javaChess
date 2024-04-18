package inf101.chess.player.ai;

import inf101.chess.model.ChessGame;
import inf101.chess.player.AbstractChessPlayer;
import inf101.grid.ChessMove;

public class DumbChessPlayer extends AbstractChessPlayer {
	
	static int counter = 1;

	public DumbChessPlayer(char symbol) {
		super(symbol, "DumbPlayer " + counter++);
	}

	@Override
	public ChessMove getMove(ChessGame game) {
		for (ChessMove move : game.getPossibleMoves()) {
			return move;
		}
		return null;
	}
}
