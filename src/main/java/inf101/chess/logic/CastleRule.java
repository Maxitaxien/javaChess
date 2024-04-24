package inf101.chess.logic;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.model.IChessBoard;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Piece;
import inf101.grid.Location;

/**
 * Handles the logic of when a castling move should be added
 * to the King's legal moves. 
 * The validity of the move when in check is handled in the
 * MoveCollectorAndVerifier
 */
public class CastleRule {
	King king;
	IChessBoard board;
	
	public CastleRule(IChessBoard board, King king) {
		this.king = king;
		this.board = board;
	}
	
	/**
	 * Checks if the castling move is legal.
	 * @return a list of locations for legal castles (either size 0, 1 or 2)
	 */
	public List<Location> getLegalCastles() {
		List<Location> legalCastles = new ArrayList<>();
		if (isOnCorrectRow() && !king.hasMoved() && !king.hasCastled()) {
			int row = king.getLocation().row;
			int kingCol = king.getLocation().col;
			Location kingSideRookLoc = new Location(row, kingCol + 3);
			Location queenSideRookLoc = new Location(row, kingCol - 4);
			
			Piece kingSideRook = board.get(kingSideRookLoc);
			Piece queenSideRook = board.get(queenSideRookLoc);
			

			if (kingSideRook != null) {
				if (!kingSideRook.hasMoved() && 
						squaresEmpty(row, kingCol + 1, kingCol + 2) &&
						squaresSafe(row, kingCol, kingCol + 2)) {
					legalCastles.add(new Location(row, kingCol + 2));
				}
			}
			
			if (queenSideRook != null) {
				if (!queenSideRook.hasMoved() &&
						squaresEmpty(row, kingCol - 1, kingCol - 3) &&
						squaresSafe(row, kingCol, kingCol - 3)) {
					legalCastles.add(new Location(row, kingCol - 2));
				}
			}
		}
		return legalCastles;
		
	}
	
	/**
	 * Helper method to check if the king is on the correct row for castling
	 * (the starting row for each colour)
	 * @return boolean indicating if this is the case
	 */
	private boolean isOnCorrectRow() {
		if (king.getColour() == 'W') {
			return (king.getLocation().row == board.numRows() - 1) ? true : false;
		}
		else {
			return (king.getLocation().row == 0) ? true : false;
		}
	}
	
	/**
	 * Helper method to check if all specific squares 
	 * to castle through are empty
	 * Looping logic: Credit to ChatGPT
	 * @param row the row where the checking is to be done
	 * @param startCol the first column to check
	 * @param endCol the final column to check
	 * @return boolean indicating if all the squares were empty or not
	 */
	private boolean squaresEmpty(int row, int startCol, int endCol) {
		int step = startCol < endCol ? 1 : -1;
		for (int col = startCol; col != endCol + step; col += step) {
			if (!(board.squareEmpty(new Location(row, col)))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper method to check if all squares to castle through
	 * are safe (not attacked by enemy pieces)
	 * @param row the row where the checking is to be done
	 * @param startCol the first column to check
	 * @param endCol the final column to check
	 * @return boolean indicating if all the squares were safe or not
	 */
	private boolean squaresSafe(int row, int startCol, int endCol) {
		int step = startCol < endCol ? 1 : -1;
		// Check if the castling locations are attacked by any opposing pieces
		List<Location> opposingMoves = new ArrayList<>();
		
		for (Location loc: board.locations()) {
			Piece piece = board.get(loc);
			if (piece != null && piece.getSymbol() != 'K') {
				if (piece.getColour() != king.getColour()) {
					List<Location> pieceMoves = piece.getPossibleMoves(board);
					opposingMoves.addAll(pieceMoves);
				}
			}
		}
		
		for (int col = startCol; col != endCol + step; col += step) {
			if (opposingMoves.contains(new Location(row, col))){ 
				return false;
			}
		}
		return true;
	}

}
