package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.GameBoard;

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

	// TODO: Implement
	@Override
	public List<Location> getLegalMoves(GameBoard board) {
		return null;
	}



}
