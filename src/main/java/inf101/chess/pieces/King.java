package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.logic.CastleRule;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class King extends Piece {
	private boolean hasCastled = false;
	
	public King(char colour, Location loc) {
		super(colour, 'K', loc);
	}
	

	/**
	 * Indicates if the king has castled.
	 * An already castled king cannot castle again
	 * @return boolean indicating if king has castled
	 */
	public boolean hasCastled() {
		return this.hasCastled;
	}
	
	/**
	 * Update the king's hasCastled to true when castling is done.
	 */
	public void castled() {
		hasCastled = true;
	}
	
	
	@Override
	public int getValue() {
		// Here we just return a sufficiently high number in relation to the other values,
		// to make the algorithm realize that it is  bad to lose a king!
		return 12;
	}
	
	@Override
	public King copy() {
		King newKing = new King(this.getColour(), this.getLocation());
		if (hasMoved()) {
			newKing.moved();
		}
		if (hasCastled()) {
			newKing.castled();
		}
		return newKing;
	}

	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		List<Location> possibleMoves = new ArrayList<>();
		int currentRow = this.getLocation().row;
		int currentCol = this.getLocation().col;
		
		Location newLoc;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(i == 0 && j == 0)) {
					int newRow = currentRow + i;
					int newCol = currentCol + j;
					newLoc = new Location(newRow, newCol);
					if (board.isOnBoard(newLoc) && ((board.squareEmpty(newLoc) || 
							board.isOpponent(getColour(), newLoc)))) {
						possibleMoves.add(newLoc);
					}
				}
			}
		}

		
		CastleRule rule = new CastleRule(board, this);
		List<Location> possibleCastles = rule.getLegalCastles();
		possibleMoves.addAll(possibleCastles);
		
		return possibleMoves;
	}
	

}
