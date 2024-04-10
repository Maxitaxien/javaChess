package inf101.grid;

import java.util.Iterator;
import java.util.List;

import inf101.chess.pieces.Piece;

public class ChessMove extends Move {
	private Piece piece;
	private boolean capture;
	
	public ChessMove(List<Location> locations, Piece piece) {
		super(locations);
		this.piece = piece;
	}
	
	/**
	 * @return the piece being moved
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Indicates if the move captures another piece.
	 * @return boolean
	 */
	public boolean isCapture() {
		return capture;
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
