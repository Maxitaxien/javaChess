package inf101.chess.pieces;

import java.util.List;

import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public abstract class Piece {
	private char colour;
	private char symbol;
	private Location loc;
	private boolean hasMoved;
	
	public Piece(char colour, char symbol, Location loc) {
		this.colour = colour;
		this.symbol = symbol;
		this.loc = loc;
		this.hasMoved = false;
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
	 * Example: Upper left corner of board: (0, 0)
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
	 * Updates a piece's hasMoved attribute.
	 * Not relevant for all pieces, but as we are using Piece as an abstract 
	 * class, we might as well implement this for every kind of piece.
	 * Important for pawns, kings and rooks.
	 */
	public void moved() {
		this.hasMoved = true;
	}
	
	/**
	 * Let's us know if the piece has already moved.
	 * @return a boolean indicating if the piece has been moved in the game.
	 */
	public boolean hasMoved() {
		return this.hasMoved;
	}
	
	/**
	 * Update the location of the piece on the board.
	 */
	public void movePiece(Location to) {
		this.loc = to;
	}
	
	/**
	 * Calculates possible moves for the piece.
	 * The legality of the options are checked in different parts of the code
	 * Some pieces need the board size to calculate possible moves.
	 * @return a list of possible locations the piece can move to
	 */
	public abstract List<Location> getPossibleMoves(IChessBoard board);

	/**
	 * Creates a copy of a piece,
	 * to be used when testing positions
	 * @return a copy of the current Piece
	 */
	public abstract Piece copy();
	
	
	/**
	 * Get the value for the piece, which can be used for AI players.
	 * These are 1 for pawn, 3 for knight and bishop, 5 for rook,
	 * 9 for the queen and infinity for the king.
	 * @return integer giving the piece value for the kind of piece
	 */
	public abstract int getValue();
	
	@Override
	public String toString() {
		return "" + getSymbol() + getColour();
	}
}
