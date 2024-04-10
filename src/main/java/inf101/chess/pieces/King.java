package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.GameBoard;

public class King extends Piece {
	private boolean hasCastled = false;
	private boolean hasMoved = false;
	
	public King(char colour, Location loc) {
		super(colour, 'K', loc);
	}

	@Override
	public List<Location> getLegalMoves(GameBoard board) {
		return board.getNeighborhood(getLocation(), 1);
	}
}