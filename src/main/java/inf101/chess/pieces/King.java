package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class King extends Piece {
	private boolean hasCastled = false;
	private boolean hasMoved = false;
	
	public King(char colour, Location loc) {
		super(colour, 'K', loc);
	}

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
		List<Location> possibleMoves = new ArrayList<>();
		int currentRow = this.getLocation().row;
		int currentCol = this.getLocation().col;
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (!(i == 0 && j == 0)) {
					int newRow = currentRow + i;
					int newCol = currentCol + j;
					possibleMoves.add(new Location(newRow, newCol));
				}
			}
		}
		
		// TODO: Implement castling
		if (!hasCastled && !hasMoved) {
			;
		}
		
		return possibleMoves;
	}

	@Override
	public void moved() {
		hasMoved = true;
		
	}
}
