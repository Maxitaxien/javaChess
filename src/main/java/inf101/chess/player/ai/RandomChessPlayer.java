package inf101.chess.player.ai;

import java.util.Collections;
import java.util.List;

import inf101.chess.logic.MoveCollectorAndVerifier;
import inf101.chess.model.ChessGame;
import inf101.chess.player.AbstractChessPlayer;
import inf101.grid.ChessMove;
/**
 * Inspired by RandomPlayer by Martin Vatshelle
 * This Player chooses a random move among all the possible moves.
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class RandomChessPlayer extends AbstractChessPlayer {

	public RandomChessPlayer(char piece, String name) {
		super(piece, name);
	}

	public RandomChessPlayer(char piece) {
		super(piece, "Random player");
	}

	@Override
	public ChessMove getMove(ChessGame game) {
		MoveCollectorAndVerifier moveGetter = new MoveCollectorAndVerifier(game.getGameBoard(),
													game.getCurrentPlayerChar());
		List<ChessMove> moves = moveGetter.getMoves();
		if (moves.isEmpty()) {
			throw new IllegalStateException("No possible moves to choose, game should have ended!");
		}
		Collections.shuffle(moves);
		return moves.get(0);
	}
}
