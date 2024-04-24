package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class Knight extends Piece {

	public Knight(char colour, Location loc) {
		super(colour, 'N', loc);
	}
	
	@Override
	public int getValue() {
		return 3;
	}
	
	@Override
	public Knight copy() {
		Knight newKnight = new Knight(this.getColour(), this.getLocation());
		if (hasMoved()) {
			newKnight.moved();
		}
		return newKnight;
	}

	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		List<Location> legalMoves = new ArrayList<>();
		int currentRow = getLocation().row;
		int currentCol = getLocation().col;
		
		// We will calculate two positions at once in each round of the loop:
		Location newLoc1;
		Location newLoc2;
		
		
		for (int i = -2; i < 3; i += 4) {
			for (int j = -1; j < 2; j += 2) {
				if (!(i == 0 && j == 0)) {
					
					newLoc1 = new Location(currentRow + i, currentCol + j);
					newLoc2 = new Location(currentRow + j, currentCol + i);
					
					if (board.isOpponent(getColour(), newLoc1) || board.squareEmpty(newLoc1)) {
						legalMoves.add(newLoc1);
					}
					if (board.isOpponent(getColour(), newLoc2) || board.squareEmpty(newLoc2)) {
						legalMoves.add(newLoc2);
					}
				}
			}
		}
		return legalMoves;
	}
}
