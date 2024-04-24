package inf101.grid;


import java.util.HashMap;
import java.util.Map;

import inf101.chess.pieces.Piece;

/**
 * Extends the move class to work with chess
 */
public class ChessMove extends Move {
	private Piece piece;
	private boolean capture; // Indicates if the move is a capture
	private boolean castle; // Indicates if the move is a castle
	private boolean promotion; // Indicates if the move is a promotion
	
	private final Map<Integer, Character> colMap = Map.of(
			0, 'a', 1, 'b', 2, 'c', 3, 'd',
			4, 'e', 5, 'f', 6, 'g', 7, 'h');
	
	private final Map<Integer, Character> rowMap = Map.of(
			0, '8', 1, '7', 2, '6', 3, '5',
			4, '4', 5, '3', 6, '2', 7, '1');
	
		
	
	// These are only set in the case that the move is a castle
	private Location rookFrom;
	private Location rookTo;
	
	public ChessMove(Location from, Location to, Piece piece) {
		super(from, to);
		this.piece = piece;
		this.capture = false;
		this.capture = false;
		this.promotion = false;
		this.castle = false;
	}
	
	/**
	 * Used for castling so that the move can move two pieces at once
	 */
	public ChessMove(Location kingFrom, Location kingTo, Piece king,
					Location rookFrom, Location rookTo) {
		super(kingFrom, kingTo);
		this.piece = king;
		
		this.rookFrom = rookFrom;
		this.rookTo = rookTo;
		this.castle = true;
	}
	
	
	/**
	 * Get the piece being moved in the ChessMove
	 * @return the piece being moved
	 */
	public Piece getPiece() {
		return piece;
	}
	

	/**
	 * Sets the move to be a capture.
	 */
	public void capture() {
		this.capture = true;
	}
	
	
	/**
	 * Sets the move to be a promotion.
	 */
	public void promotion() {
		this.promotion = true;
	}
	
	/**
	 * Indicates if the move captures another piece.
	 * @return boolean indicating if the move is a capture
	 */
	public boolean isCapture() {
		return capture;
	}
	
	
	/**
	 * Indicates if the move is a castle.
	 * @return boolean indicating if the move is a castle
	 */
	public boolean isCastle() {
		return castle;
	}
	
	/**
	 * Indicates if the move is a promotion.
	 * @return boolean indicating if the move is a promotion.
	 */
	public boolean isPromotion() {
		return promotion;
	}

	
	/**
	 * Used in castling. Gets the location of the rook to move
	 * @return a location to move from
	 */
	public Location getRookFrom() {
		return rookFrom;
	}
	
	/**
	 * Used in casting. Gets the location to move the rook to
	 * @return a location to move to
	 */
	public Location getRookTo() {
		return rookTo;
	}
	

	/**
	 * Accounts for checks and captures and adds them to
	 * string representation of move
	 */
	@Override
    public String toString() {
		if (isCastle()) {
			// Kingside
			if (getRookFrom().col == 7) {
				return "0-0";
			}
			else {
				return "0-0-0";
			}
		}
		else {
			String capture = (isCapture()) ? "x" : "";
			char pieceSymbol = getPiece().getSymbol();
			char newRow = rowMap.get(getTo().row);
			char newCol = colMap.get(getTo().col);
			if (pieceSymbol != 'P') {
				return pieceSymbol + capture + newCol + newRow;
			}
			// Special cases for pawns
			else {
				char pawnColumn = colMap.get(getFrom().col);
				String firstChar = (capture.equals("x")) ? String.valueOf(pawnColumn) : "";
				String promotionAddition = (isPromotion()) ? "=Q" : "";
				return firstChar + capture + newCol + newRow + promotionAddition;
			}
		}
	}
	
	
}
