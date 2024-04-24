package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class Pawn extends Piece {	
	public Pawn(char colour, Location loc) {
		super(colour, 'P', loc);
	}	
	

	@Override
	public int getValue() {
		return 1;
	}
	
	@Override
	public Pawn copy() {
		Pawn newPawn = new Pawn(this.getColour(), this.getLocation());
		if (hasMoved()) {
			newPawn.moved();
		}
		return newPawn;
	}

	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		List<Location> possibleMoves = new ArrayList<>();
		int currentRow = this.getLocation().row;
		int currentCol = this.getLocation().col;
		
		Location newLoc;
		
		int direction = (getColour() == 'W') ? - 1 : 1;
		
		// Normal move down the board
		newLoc = new Location(currentRow + direction, currentCol);
		if (board.isOnBoard(newLoc) && board.squareEmpty(newLoc)) {
			possibleMoves.add(newLoc);
		}
		
		// Captures
		newLoc = new Location(currentRow + direction, currentCol + 1);
		if (board.isOpponent(getColour(), newLoc)) {
			possibleMoves.add(newLoc);
		}
		
		newLoc = new Location(currentRow + direction, currentCol - 1);
		if (board.isOpponent(getColour(), newLoc)) {
			possibleMoves.add(newLoc);
		}
		
		newLoc = new Location(currentRow + direction*2, currentCol);
		Location squareBetween = new Location(currentRow + direction, currentCol);
		// Initial move
		if (!hasMoved() && board.isOnBoard(newLoc) && 
				board.squareEmpty(newLoc) && board.squareEmpty(squareBetween)) {
			possibleMoves.add(new Location(currentRow + direction*2, currentCol));
		}
		
		return possibleMoves;
	}
}
