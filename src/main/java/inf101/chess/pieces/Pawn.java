package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class Pawn extends Piece {
	private boolean hasMoved = false;
	
	public Pawn(char colour, Location loc) {
		super(colour, 'P', loc);
	}	
	
	/**
	 * Indicates if the pawn has moved. Relevant
	 * as the pawn can only move two squares forwards
	 * on it's first move.
	 * 
	 * @return true if piece has moved else false
	 */
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void moved() {
		hasMoved = true;
	}

	// TODO: Implement
	@Override
	public List<Location> getLegalMoves(ChessBoard board) {
		List<Location> legalMoves = new ArrayList<>();
		int currentRow = this.getLocation().row;
		int currentCol = this.getLocation().col;
		
		if (getColour() == 'W') {
			legalMoves.add(new Location(currentRow - 1, currentCol));
		}
		else {
			legalMoves.add(new Location(currentRow + 1, currentCol));
		}
		return legalMoves;
	}
}
