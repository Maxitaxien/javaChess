package inf101.chess.model;

import inf101.chess.pieces.Piece;
import inf101.grid.Grid;
import inf101.grid.GridLocationIterator;
import inf101.grid.Location;

public class TestBoard implements IChessBoard {
	
	private Grid<Piece> grid;
	
	public TestBoard(int rows, int cols) {
		grid = new Grid<>(rows, cols);
	}

	@Override
	public void setPiece(Location loc, Piece piece) {
		grid.set(loc, piece);
		
	}

	@Override
	public void initializeBoard() {
		// Not needed for TestBoard.
		
	}

	@Override
	public void clearBoard() {
		// Not needed for TestBoard
		
	}

	@Override
	public Piece get(Location loc) {
		return grid.get(loc);
	}

	@Override
	public char getPlayerChar(Location loc) {
		return grid.get(loc).getColour();
	}

	@Override
	public int numRows() {
		return grid.numRows();
	}

	@Override
	public int numColumns() {
		return grid.numColumns();
	}

	@Override
	public boolean squareEmpty(Location loc) {
		try {
			return grid.get(loc) == null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isOpponent(char ownChar, Location loc) {
		try {
			boolean empty = squareEmpty(loc);
			if (empty) {
				return false;
			}
			else {
				return getPlayerChar(loc) != ownChar;
			}
		} catch (Exception E) {
			return false;
		}
	}

	
	@Override
	public void movePiece(Location from, Location to) {
		Piece piece = grid.get(from);
		if (piece == null) {
			System.err.println("No piece at location: " + from + ".");
		} else if (squareEmpty(to) || piece.getColour() != getPlayerChar(to)) {
			grid.set(from, null);
			grid.set(to, piece);
			char pieceType = piece.getSymbol();
		} else {
			System.err.println("Can not place at " + to + ".");
		}
	}

	@Override
	public GridLocationIterator locations() {
		return grid.locations();
	}

	@Override
	public int countPieces(char PlayerChar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isOnBoard(Location loc) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public IChessBoard copy(boolean test) {
		// TODO Auto-generated method stub
		return null;
	}

}
