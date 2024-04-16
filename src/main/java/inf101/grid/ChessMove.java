package inf101.grid;

import java.util.Iterator;

import inf101.chess.pieces.Piece;

public class ChessMove extends Move {
	private Piece piece;
	private boolean capture; // Indicates if the move is a capture
	private boolean castle; // Indicates if the move is a castle
	
	// These are only set in the case that the move is a castle
	private Location rookFrom;
	private Location rookTo;
	
	public ChessMove(Location from, Location to, Piece piece) {
		super(from, to);
		this.piece = piece;
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
	 * Indicates if the move is a castle.
	 * @return
	 */
	public boolean isCastle() {
		return castle;
	}
	
	/**
	 * Sets the move to be a castle.
	 */
	public void castled() {
		this.castle = true;
	}
	
	/**
	 * Indicates if the move captures another piece.
	 * @return boolean
	 */
	public boolean isCapture() {
		return capture;
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
	
	// Slightly edited from the Move toString() method
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Location> iterator = iterator();
        while (iterator.hasNext()) {
            Location loc = iterator.next();
            builder.append(piece.getSymbol());
            builder.append(loc);
            if (iterator.hasNext())
                builder.append(" --> ");
        }
        return builder.toString();
    }
	
	
}
