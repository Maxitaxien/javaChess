package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

/**
 * A class for calculating moves in the
 * cardinal and diagonal directions
 */
public class DirectionalCalculator {
	/**
	 * Calculates moves in the cardinal (up, down, left, right) directions
	 * @param loc the location of the piece
	 * @param board the board to place on, where the size of the board is relevant
	 * @return a list of locations in the cardinal directions
	 */
	public List<Location> calculateCardinal(Location loc, ChessBoard board) {
	    List<Location> cardinalMoves = new ArrayList<>();
	    int currentRow = loc.row;
	    int currentCol = loc.col;
	    int numRows = board.numRows();
	    int numCols = board.numColumns();

	    // Up moves
	    for (int row = currentRow - 1; row >= 0; row--) {
	    	Location moveTo = new Location(row, currentCol);
	        cardinalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }

	    // Down moves
	    for (int row = currentRow + 1; row < numRows; row++) {
	    	Location moveTo = new Location(row, currentCol);
	        cardinalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }

	    // Left moves
	    for (int col = currentCol - 1; col >= 0; col--) {
	    	Location moveTo = new Location(currentRow, col);
	        cardinalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }

	    // Right moves
	    for (int col = currentCol + 1; col < numCols; col++) {
	    	Location moveTo = new Location(currentRow, col);
	        cardinalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }

	    return cardinalMoves;
	}

	
	
	/**
	 * Calculates moves in the diagonal (down right, up left, up right, down left) directions
	 * @param loc the location of the piece
	 * @param board the board to place on, where the size of the board is relevant
	 * @return a list of locations in the diagonal directions
	 */
	public List<Location> calculateDiagonal(Location loc, ChessBoard board) {
	    List<Location> diagonalMoves = new ArrayList<>();
	    int numRows = board.numRows();
	    int numColumns = board.numColumns();
	    int currentRow = loc.row;
	    int currentCol = loc.col;

	    // Down-right diagonal
	    for (int i = 1; currentRow + i < numRows && currentCol + i < numColumns; i++) {
	    	Location moveTo = new Location(currentRow + i, currentCol + i);
	        diagonalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }
	    // Up-left diagonal
	    for (int i = 1; currentRow - i >= 0 && currentCol - i >= 0; i++) {
	    	Location moveTo = new Location(currentRow - i, currentCol - i);
	        diagonalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }
	    // Up-right diagonal
	    for (int i = 1; currentRow + i < numRows && currentCol - i >= 0; i++) {
	    	Location moveTo = new Location(currentRow + i, currentCol - i);
	        diagonalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }
	    // Down-left diagonal
	    for (int i = 1; currentRow - i >= 0 && currentCol + i < numColumns; i++) {
	    	Location moveTo = new Location(currentRow - i, currentCol + i);
	        diagonalMoves.add(moveTo);
	        if (!(board.isEmpty(moveTo))) {
	        	break;
	        }
	    }

	    return diagonalMoves;
	}

	

}
