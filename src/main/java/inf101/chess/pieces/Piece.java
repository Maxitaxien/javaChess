package inf101.chess.pieces;

import java.util.List;

import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

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
	 * Moves the piece and updates the hasMoved attribute if the piece has one.
	 */
	public abstract void moved();
	
	/**
	 * Let's us know if the piece has been moved earlier in the game.
	 * @return boolean indicating if the piece has previously moved
	 */
	public abstract boolean hasMoved();
	
	@Override
	public String toString() {
		return "Piece: " + getSymbol()+ getColour();
	}
}
