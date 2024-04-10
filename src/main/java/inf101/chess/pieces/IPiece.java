package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.grid.Move;

/**
 * Interface for a chess piece.
 */
public interface IPiece {
	
	/**
	 * Get colour of piece (white or black)
	 * @return char 'W' or char 'B'
	 */
	public char getColour();
	
	/**
	 * Get Location on board in row, col notation.
	 * Example: Upper right corner of board: (0, 0)
	 * @return Location object representing board location
	 */
	public Location getLocation();
	
	/**
	 * Get character corresponds to the kind of piece.
	 * Here the letters P, B, N, R, Q, K are used
	 * (Corresponding to Pawn, Bishop, Knight, Rook, Queen, King
	 * @return char that represents piece
	 */
	public char getRepresentation();
	
	/**
	 * Calculates legal moves for the piece
	 * at it's current position. Should be updated
	 * for all pieces any time a move is made.
	 * @return a list of possible locations the piece can move to
	 */
	public List<Move> getLegalMoves();
}
