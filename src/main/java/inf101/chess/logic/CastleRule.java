package inf101.chess.logic;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.pieces.King;
import inf101.chess.pieces.Piece;
import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

/**
 * Handles the logic of when a castling move should be added
 * to the King's legal moves. 
 * The validity of the move when in check is handled in Chess.java
 */
public class CastleRule {
	boolean shortCastle;
	King king;
	ChessBoard board;
	
	public CastleRule(ChessBoard board, King king) {
		// this.state = state;
		this.king = king;
		this.board = board;
	}
	
	/**
	 * Checks if the castling move is legal.
	 * @return a list of locations for legal castles
	 */
	public List<Location> getLegalCastles() {
		List<Location> legalCastles = new ArrayList<>();
		if (isOnCorrectRow()) {
			int row = king.getLocation().row;
			int kingCol = king.getLocation().col;
			Location kingSideRookLoc = new Location(row, kingCol - 3);
			Location queenSideRookLoc = new Location(row, kingCol + 4);
			
			Piece kingSideRook = board.get(kingSideRookLoc);
			Piece queenSideRook = board.get(queenSideRookLoc);
			
			if (kingSideRook != null) {
				if (!kingSideRook.hasMoved() && 
						squaresEmpty(row, kingCol - 1, kingCol - 2)) {
					legalCastles.add(new Location(row, kingCol - 2));
				}
			}
			
			if (queenSideRook != null) {
				if (!queenSideRook.hasMoved() &&
						squaresEmpty(row, kingCol + 1, kingCol + 3)) {
					legalCastles.add(new Location(row, kingCol + 2));
				}
			}
		}
		return legalCastles;
		
	}
	
	/**
	 * Helper method to check if the king is on the correct row for castling
	 * (the back row)
	 * @return boolean indicating if this is the case
	 */
	private boolean isOnCorrectRow() {
		if (king.getColour() == 'W') {
			return (king.getLocation().row == 0) ? true : false;
		}
		else {
			return (king.getLocation().row == board.numRows() - 1) ? true : false;
		}
	}
	
	/**
	 * Helper method to check if all specific squares are empty
	 */
	private boolean squaresEmpty(int row, int startCol, int endCol) {
		int step = startCol < endCol ? 1 : -1;
		for (int col = startCol; col != endCol + step; col += step) {
			if (!(board.isEmpty(new Location(row, col)))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @return string representation depending on move
	 */
	@Override
	public String toString() {
		if (this.shortCastle) {
			return "0-0";
		}
		else {
			return "0-0-0";
		}
	}

}
