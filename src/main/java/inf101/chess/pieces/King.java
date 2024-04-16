package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.logic.CastleRule;
import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class King extends Piece {
	private boolean hasCastled = false;
	private boolean hasMoved = false;
	
	public King(char colour, Location loc) {
		super(colour, 'K', loc);
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
	
	public boolean hasCastled() {
		return this.hasCastled;
	}

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
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
					if (board.isOnBoard(newLoc) && (board.isEmpty(newLoc) || board.isOpponent(getColour(), newLoc))) {
						possibleMoves.add(newLoc);
					}
				}
			}
		}

		if (!hasCastled() && !hasMoved()) {
			CastleRule rule = new CastleRule(board, this);
			List<Location> possibleCastles = rule.getLegalCastles();
			possibleMoves.addAll(possibleCastles);
		}
		
		return possibleMoves;
	}
	
	public void castled() {
		hasCastled = true;
	}

	@Override
	public void moved() {
		hasMoved = true;
		
	}
}
