package inf101.chess.player.ai;

import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.model.ChessGame;
import inf101.chess.player.AbstractChessPlayer;
import inf101.grid.ChessMove;

public class DumbChessPlayer extends AbstractChessPlayer {
	public DumbChessPlayer(char symbol) {
		super(symbol, "DumbPlayer");
	}

	@Override
	public ChessMove getMove(ChessGame game) {
		MoveCollectorAndVerifier moveGetter = new MoveCollectorAndVerifier(game.getGameBoard(),
															game.getCurrentPlayerChar());
		// Return the first move:
		for (ChessMove move : moveGetter.getMoves()) {
			return move;
		}
		return null;
	}
}
