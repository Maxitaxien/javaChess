package inf101.chess.logic;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

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
	public List<Location> calculateCardinal(Location loc, IChessBoard board, char pieceColour) {
	    List<Location> cardinalMoves = new ArrayList<>();
	    int currentRow = loc.row;
	    int currentCol = loc.col;
	    int numRows = board.numRows();
	    int numCols = board.numColumns();

	    // Up moves
	    for (int row = currentRow - 1; row >= 0; row--) {
	        Location moveTo = new Location(row, currentCol);
	        if (!addMove(moveTo, board, pieceColour, cardinalMoves)) {
	        	break;
	        }
	    }

	    // Down moves
	    for (int row = currentRow + 1; row < numRows; row++) {
	    	Location moveTo = new Location(row, currentCol);
	        if (!addMove(moveTo, board, pieceColour, cardinalMoves)) {
	        	break;
	        }
	    }

	    // Left moves
	    for (int col = currentCol - 1; col >= 0; col--) {
	    	Location moveTo = new Location(currentRow, col);
	        if (!addMove(moveTo, board, pieceColour, cardinalMoves)) {
	        	break;
	        }
	    }

	    // Right moves
	    for (int col = currentCol + 1; col < numCols; col++) {
	    	Location moveTo = new Location(currentRow, col);
	        if (!addMove(moveTo, board, pieceColour, cardinalMoves)) {
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
	public List<Location> calculateDiagonal(Location loc, IChessBoard board, char pieceColour) {
	    List<Location> diagonalMoves = new ArrayList<>();
	    int numRows = board.numRows();
	    int numColumns = board.numColumns();
	    int currentRow = loc.row;
	    int currentCol = loc.col;

	    // Up-right diagonal
	    for (int i = 1; currentRow + i < numRows && currentCol + i < numColumns; i++) {
	    	Location moveTo = new Location(currentRow + i, currentCol + i);
	    	if (!addMove(moveTo, board, pieceColour, diagonalMoves)) {
	        	break;
	        }
	    }
	    // Up-left diagonal
	    for (int i = 1; currentRow - i >= 0 && currentCol - i >= 0; i++) {
	    	Location moveTo = new Location(currentRow - i, currentCol - i);
	    	if (!addMove(moveTo, board, pieceColour, diagonalMoves)) {
	        	break;
	        }
	    }
	    // Down-right diagonal
	    for (int i = 1; currentRow + i < numRows && currentCol - i >= 0; i++) {
	    	Location moveTo = new Location(currentRow + i, currentCol - i);
	    	if (!addMove(moveTo, board, pieceColour, diagonalMoves)) {
	        	break;
	        }
	    }
	    // Down-left diagonal
	    for (int i = 1; currentRow - i >= 0 && currentCol + i < numColumns; i++) {
	    	Location moveTo = new Location(currentRow - i, currentCol + i);
	    	if (!addMove(moveTo, board, pieceColour, diagonalMoves)) {
	        	break;
	        }
	    }

	    return diagonalMoves;
	}
	
	/**
	 * Adds the move, returns false
	 * when the loop should be stopped.
	 */
	private boolean addMove(Location move, IChessBoard board, char pieceColour, List<Location> moveList) {
        if (!(board.squareEmpty(move))) {
        	if (board.isOpponent(pieceColour, move)) {
        		moveList.add(move);
        		return false;
        	}
        	else {
        		return false;
        	}
        }
        moveList.add(move);
        return true;
	}


	

}
