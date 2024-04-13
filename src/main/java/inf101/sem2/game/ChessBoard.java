package inf101.sem2.game;

import java.util.List;

import inf101.chess.pieces.Piece;
import inf101.grid.Grid;
import inf101.grid.GridDirection;
import inf101.grid.GridLocationIterator;
import inf101.grid.Location;

/**
 * A class for a ChessBoard to represent pieces.
 * Adapted from GameBoard to work with chess.
 * The code is therefore heavily inspired by GameBoard.
 */
public class ChessBoard {

	private Grid<Piece> grid;

	public ChessBoard(int rows, int cols) {
		grid = new Grid<>(rows, cols);
	}

	public void setPiece(Location loc, Piece piece) {
		if (isEmpty(loc)) {
			grid.set(loc, piece);
		} else {
			System.err.println("Can not place at " + loc + ".");
		}
	}
	

	/**
	 * Place new piece at location even if there is already another piece there.
	 * This essentially represents a piece capture.
	 * 
	 * @param loc
	 * @param elem
	 */
	public void swap(Location loc, Piece piece) {
		grid.set(loc, piece);
	}

	public Piece get(Location loc) {
		return grid.get(loc);
	}
	
	public char getPlayerChar(Location loc) {
		Piece piece = get(loc);
		if (piece == null) {
			return ' ';
		} else {
			return piece.getColour();
		}
	}

	public int numColumns() {
		return grid.numColumns();
	}

	public int numRows() {
		return grid.numRows();
	}

	/**
	 * Method to determine if something exists on a given square
	 * @param loc the location to check
	 * @return true if the given location is empty
	 */
	public boolean isEmpty(Location loc) {
		try {
			return grid.get(loc) == null;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Move piece at location from to location to
	 * TODO: TESTING
	 * 
	 * 
	 * @param from
	 * @param to
	 */
	public void movePiece(Location from, Location to) {
		Piece piece = grid.get(from);
		if (piece == null) {
			System.err.println("No piece at location: " + from + ".");
		} else if (isEmpty(to) || piece.getColour() != getPlayerChar(to)) {
			grid.set(from, null);
			grid.set(to, piece);
			piece.movePiece(to);
			char pieceType = piece.getSymbol();
			if (pieceType == 'P' || pieceType == 'R' || pieceType == 'K') {
				piece.moved();
			}
		} else {
			System.err.println("Can not place at " + to + ".");
		}
	}
	

	@Override
	public String toString() {
		String s = "";

		for (int row = 0; row < grid.numRows(); row++) {
			// print row
			for (int col = 0; col < grid.numColumns(); col++) {
				Piece p = grid.get(new Location(row, col));
				if (p == null) {
					s += ' ';
				} else {
					s += p.getSymbol();
				}
				if (col < grid.numColumns() - 1) {
					s += '|';
				}
			}
			// print newline
			s += "\n";

			// print horizontal separator
			if (row < grid.numRows() - 1) {
				for (int col = 0; col < grid.numColumns(); col++) {
					s += '-';
					if (col < grid.numColumns() - 1) {
						s += '+';
					}
				}
				s += "\n";
			}

		}

		return s;
	}

	/**
	 * @return The maximum number of pieces in a row that the given player has.
	 */
	public int countNumInRow(char playerChar) {
		int max = 0;
		for (Location loc : locations()) {
			for (GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
				max = Math.max(max, countNumInRow(playerChar, loc, dir));
			}
		}
		return max;
	}

	private int countNumInRow(char playerChar, Location start, GridDirection dir) {
		int count = 0;
		while (grid.isOnGrid(start) && getPlayerChar(start) == playerChar) {
			count++;
			start = start.getNeighbor(dir);
		}
		return count;
	}

	/**
	 * This method checks whether all locations of the board is occupied
	 * or there are any empty places on the board.
	 *
	 * @return If the board is full
	 */
	public boolean isFull() {
		for (Location loc : locations()) {
			if (isEmpty(loc)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Makes a shallow copy of the board
	 */
	public ChessBoard copy() {
		ChessBoard board = new ChessBoard(grid.numRows(), grid.numColumns());
		grid.fillCopy(board.grid);
		return board;
	}

	public GridLocationIterator locations() {
		return grid.locations();
	}

	public void clear() {
		grid.clear();
	}

	public void fill(Piece piece) {
		grid.fill(piece);
	}

	public List<Location> getNeighborhood(Location loc) {
		return grid.getNeighbourhood(loc);
	}

	public List<Location> getNeighborhood(Location loc, int dist) {
		return grid.getNeighbourhood(loc, dist);
	}

	public int countPieces(char playerChar) {
		int total = 0;
		for (Location loc : locations()) {
			if (playerChar == getPlayerChar(loc)) {
				total++;
			}
		}
		return total;
	}

	public boolean isOnBoard(Location loc) {
		return this.grid.isOnGrid(loc);
	}
}
