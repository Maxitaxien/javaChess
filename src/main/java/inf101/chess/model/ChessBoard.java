package inf101.chess.model;

import inf101.chess.pieces.Bishop;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Knight;
import inf101.chess.pieces.Pawn;
import inf101.chess.pieces.Piece;
import inf101.chess.pieces.Queen;
import inf101.chess.pieces.Rook;
import inf101.grid.Grid;
import inf101.grid.GridLocationIterator;
import inf101.grid.Location;

/**
 * A class for a ChessBoard which will hold pieces.
 */
public class ChessBoard implements IChessBoard{

	private Grid<Piece> grid;

	public ChessBoard(int numRows, int numColumns) {
		// A chessboard should usually always be 8x8,
		// but initializing smaller boards can be useful for testing methods.
		grid = new Grid<>(numRows, numColumns);
	}

	@Override
	public void setPiece(Location loc, Piece piece) {
		grid.set(loc, piece);
	}
	
	@Override
	public void initializeBoard() {
		int rows = numRows();
		int cols = numColumns();
		
		// White pawns
		for (int row = 1; row < 2; row++) {
			for (int col = 0; col < cols; col++) {
				Location loc = new Location(row, col);
				setPiece(loc, new Pawn('B', loc));
			}
		}
		
		// White rooks
		setPiece(new Location(0, 0), new Rook('B', new Location(0, 0)));
		setPiece(new Location(0, cols - 1), new Rook('B', new Location(0, cols - 1)));
		
		// White knights
		setPiece(new Location(0, 1), new Knight('B', new Location(0, 1)));
		setPiece(new Location(0, cols - 2), new Knight('B', new Location(0, cols - 2)));
		
		// White bishops
		setPiece(new Location(0, 2), new Bishop('B', new Location(0, 2)));
		setPiece(new Location(0, cols - 3), new Bishop('B', new Location(0, cols - 3)));
		
		// White queen and king
		setPiece(new Location(0, 3), new Queen('B', new Location(0, 3)));
		setPiece(new Location(0, cols - 4), new King('B', new Location(0, cols - 4)));
		
		// Black pawns
		for (int row = rows - 2; row > rows - 3; row--) {
			for (int col = 0; col < cols; col++) {
				Location loc = new Location(row, col);
				setPiece(loc, new Pawn('W', loc));
			}
		}
		
		// Black rooks
		setPiece(new Location(rows - 1, 0), new Rook('W', new Location(rows - 1, 0)));
		setPiece(new Location(rows - 1, cols - 1), new Rook('W', new Location(rows - 1, cols - 1)));
				
		// Black knights
		setPiece(new Location(rows - 1, 1), new Knight('W', new Location(rows -1, 1)));
		setPiece(new Location(rows - 1, cols - 2), new Knight('W', new Location(rows - 1, cols - 2)));
				
		// Black bishops
		setPiece(new Location(rows -1, 2), new Bishop('W', new Location(rows - 1, 2)));
		setPiece(new Location(rows - 1, cols - 3), new Bishop('W', new Location(rows - 1, cols - 3)));
				
		// White queen and king
		setPiece(new Location(rows - 1, 3), new Queen('W', new Location(rows - 1, 3)));
		setPiece(new Location(rows - 1, cols - 4), new King('W', new Location(rows - 1, cols - 4)));	
	}
	
	@Override
	public void clearBoard() {
		this.grid.clear();
	}
	
	@Override
	public Piece get(Location loc) {
		return grid.get(loc);
	}
	
	@Override
	public char getPlayerChar(Location loc) {
		Piece piece = get(loc);
		if (piece == null) {
			return ' ';
		} else {
			return piece.getColour();
		}
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
			piece.movePiece(to);
		} else {
			System.err.println("Can not place at " + to + ".");
		}
	}
	
	


	@Override
	public IChessBoard copy() {
	    IChessBoard newBoard = new ChessBoard(numRows(), numColumns());
	    for (Location loc : this.locations()) {
	        Piece originalPiece = get(loc);
	        if (originalPiece != null) {
	            Piece newPiece = originalPiece.copy();
	            newBoard.setPiece(loc, newPiece);
	        }
	    }
	    return newBoard;
	}

	@Override
	public GridLocationIterator locations() {
		return grid.locations();
	}
	
	@Override
	public int countValue(char playerChar) {
		int total = 0;
		for (Location loc : locations()) {
			if (playerChar == getPlayerChar(loc)) {
				total += get(loc).getValue();
			}
		}
		return total;
	}

	@Override
	public boolean isOnBoard(Location loc) {
		return this.grid.isOnGrid(loc);
	}

}
