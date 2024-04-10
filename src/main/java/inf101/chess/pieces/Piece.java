package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.GameBoard;

public abstract class Piece {
	private char colour;
	private char symbol;
	private Location loc;
	
	public Piece(char colour, char symbol, Location loc) {
		this.colour = colour;
		this.symbol = symbol;
		this.loc = loc;
	}
	
	/**
	 * Get colour of piece (white or black)
	 * @return char 'W' or char 'B'
	 */
	public char getColour() {
		return colour;
	}
	
	/**
	 * Get Location on board in row, col notation.
	 * Example: Upper right corner of board: (0, 0)
	 * @return Location object representing board location
	 */
	public Location getLocation() {
		return this.loc;
	}
	
	/**
	 * Get character corresponds to the kind of piece.
	 * Here the letters P, B, N, R, Q, K are used
	 * (Corresponding to Pawn, Bishop, Knight, Rook, Queen, King)
	 * @return char that represents piece
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Calculates legal moves for the piece
	 * at it's current position. Should be updated
	 * for all pieces any time a move is made.
	 * @return a list of possible locations the piece can move to
	 */
	public abstract List<Location> getLegalMoves(GameBoard board);
}
