package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class Pawn extends Piece {
	private boolean hasMoved = false;
	// Used to check if the pawn has just moved two squares - then it can be taken en passant
	private boolean canEnPassant = false;
	
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
		
		Location newLoc;
		
		int direction = (getColour() == 'W') ? 1 : -1;
		
		// Normal move down the board
		newLoc = new Location(currentRow + direction, currentCol);
		if (board.isOnBoard(newLoc) && board.get(newLoc) == null) {
			possibleMoves.add(newLoc);
		}
		
		// Captures
		newLoc = new Location(currentRow + direction, currentCol + 1);
		if (board.isOpponent(getColour(), newLoc)) {
			possibleMoves.add(newLoc);
		}
		
		newLoc = new Location(currentRow + direction, currentCol - 1);
		if (board.isOpponent(getColour(), newLoc)) {
			possibleMoves.add(newLoc);
		}
		
		newLoc = new Location(currentRow + direction, currentCol);
		// Initial move
		if (!hasMoved() && board.isOnBoard(newLoc) && board.get(newLoc) == null) {
			possibleMoves.add(new Location(currentRow + direction*2, currentCol));
		}
		
		return possibleMoves;
	}
}
