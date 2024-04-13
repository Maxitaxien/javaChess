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

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
		List<Location> possibleMoves = new ArrayList<>();
		int currentRow = this.getLocation().row;
		int currentCol = this.getLocation().col;
		
		int direction = (getColour() == 'W') ? 1 : -1;
		
		// Normal move down the board
		possibleMoves.add(new Location(currentRow + direction, currentCol));
		
		// Captures (handle these differently in other parts of the code?)
		possibleMoves.add(new Location(currentRow + direction, currentCol + 1));
		possibleMoves.add(new Location(currentRow + direction, currentCol - 1));
		
		if (!hasMoved()) {
			possibleMoves.add(new Location(currentRow + direction*2, currentCol));
		}
		
		return possibleMoves;
	}
}
