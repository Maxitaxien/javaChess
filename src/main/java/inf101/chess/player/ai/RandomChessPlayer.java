package inf101.chess.player.ai;

import java.util.Collections;
import java.util.List;

import inf101.chess.model.ChessGame;
import inf101.chess.player.AbstractChessPlayer;
import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.grid.Move;

/**
 * This Player chooses a random move among all the possible moves.
 * This player can play any game which implements the possibleMoves() method.
 * <p>
 * If a game where no possible moves exist is given, the player will throw an
 * Exception
 *
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
		List<ChessMove> moves = game.getPossibleMoves();
		if (moves.isEmpty()) {
			throw new IllegalStateException("No possible moves to choose, game should have ended!");
		}
		Collections.shuffle(moves);
		return moves.get(0);
	}
}
