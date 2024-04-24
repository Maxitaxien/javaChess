package inf101.chess.model;

import inf101.chess.pieces.Piece;
import inf101.grid.GridLocationIterator;
import inf101.grid.Location;

/**
 * An interface for chess boards.
 * The board should always be 8x8 unless when testing.
 */
public interface IChessBoard {
	
	/**
	 * Used to set a piece at a position.
	 * @param loc the position to set
	 * @param piece the piece to set at position
	 */
	public void setPiece(Location loc, Piece piece);
	
	/**
	 * Initializes a standard chess board.
	 * That is, it sets up 8 pawns for each side,
	 * 2 bishops, rooks, and knights
	 * and a single queen and king.
	 */
	public void initializeBoard();
	
	/**
	 * Clears the board so that it can be re-initialized
	 * for a new game.
	 */
	public void clearBoard();
	
	/**
	 * Copies to a new chess board.
	 * @return the new chess board
	 */
	public IChessBoard copy();
	
	/**
	 * Returns the piece at the position.
	 * @param loc the position to get
	 * @return the Piece
	 */
	public Piece get(Location loc);
	
	/**
	 * Returns the player character which
	 * the piece at the position belongs to.
	 * @param loc the position to get
	 * @return player character 'W' or 'B'
	 */
	public char getPlayerChar(Location loc);
	
	/**
	 * Returns the number of rows on
	 * the chess board. Should always be 8.
	 * @return integer representing number of rows
	 */
	public int numRows();
	
	/**
	 * Returns the number of columns on
	 * the chess board. Should always be 8.
	 * @return integer representing number of columns
	 */
	public int numColumns();
	
	/**
	 * Method to determine if something exists on a given square
	 * @param loc the location to check
	 * @return true if the given location is empty
	 */
	public boolean squareEmpty(Location loc);
	
	/**
	 * Checks if an opposing piece exists on a square.
	 * @param ownChar the current player's char
	 * @param loc the location to check
	 * @return boolean indicating if the piece at the location belongs to opponent
	 */
	public boolean isOpponent(char ownChar, Location loc);
	
	/**
	 * Move piece found at location from to location to.
	 * @param from
	 * @param to
	 */
	public void movePiece(Location from, Location to);
	
	/**
	 * Returns an iterator over all squares on
	 * the board.
	 */
	public GridLocationIterator locations();
	
	/**
	 * Counts the value of pieces on the board for a given player.
	 * @param playerChar the colour of the player to count for 
	 */
	public int countValue(char playerChar);
	
	/**
	 * Checks if given location is on the board.
	 * @param loc the location to check
	 * @return boolean indicating true or false
	 */
	public boolean isOnBoard(Location loc);
}
