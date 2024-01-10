package inf101.sem2.game;

import java.util.Objects;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.player.Player;

public abstract class MultiMoveGame extends Game<Move> {

	public MultiMoveGame(GameBoard board, Graphics graphics) {
		super(board, graphics);
	}

	public MultiMoveGame(GameBoard board, Graphics graphics, Iterable<Player> players) {
		super(board, graphics, players);
	}

	/**
	 * Check if move is valid. There cannot be any pices laying in
	 * the locations given in the move, except the piece laying in the first
	 * position.
	 * Then the starting location needs a piece that will be moved.
	 * 
	 * @param move
	 * @return true if valid move. False if not.
	 */
	@Override
	public boolean validMove(Move move) {
		if (move == null)
			return false;

		// Check that piece in first location is the players piece
		Location from = move.getFrom();
		if (!Objects.equals(board.get(from), getCurrentPlayer()))
			return false;

		Location to = move.getTo();
		if (!board.canPlace(to))
			return false;
		
		return true;
	}

	/**
	 * This method performs a move for the current player and advances to next
	 * player
	 *
	 * @param loc
	 */
	public void makeMove(Move move) {
		if (!validMove(move))
			throw new IllegalArgumentException("Cannot make move:\n" + move);

		board.movePiece(move.getFrom(), move.getTo());
	}
}
