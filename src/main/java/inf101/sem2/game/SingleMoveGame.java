package inf101.sem2.game;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.Location;
import inf101.sem2.player.Player;

public abstract class SingleMoveGame extends Game<Location> {

	public SingleMoveGame(GameBoard board, Graphics graphics) {
		super(board, graphics);
	}

	public SingleMoveGame(GameBoard gameBoard, Graphics graphics, Iterable<Player> players) {
		super(gameBoard, graphics, players);
	}

	@Override
	public void makeMove(Location move) {
		board.set(move, getCurrentPlayer());
		displayBoard();
	}

	@Override
	public List<Location> getPossibleMoves() {
		ArrayList<Location> moves = new ArrayList<>();
		for (Location loc : board.locations()) {
			if (canPlace(loc)) {
				moves.add(loc);
			}
		}
		return moves;
	}

	/**
	 * Check if move is valid. There cannot be any pices laying in
	 * the locations given in the move, except if the move has over 1 location.
	 * Then the starting location needs a piece that will be moved.
	 * 
	 * @param move
	 * @return true if valid move. False if not.
	 */
	public boolean validMove(Location move) {
		if (move == null)
			return false;

		return canPlace(move);
	}
}
