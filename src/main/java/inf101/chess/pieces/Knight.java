package inf101.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class Knight extends Piece {

	public Knight(char colour, Location loc) {
		super(colour, 'N', loc);
	}

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
		List<Location> legalMoves = new ArrayList<>();
		int currentRow = getLocation().row;
		int currentCol = getLocation().col;
		
		for (int i = -2; i < 3; i += 4) {
			for (int j = -1; j < 2; j += 2) {
				if (!(i == 0 && j == 0)) {
					legalMoves.add(new Location(currentRow + i, currentCol + j));
					legalMoves.add(new Location(currentRow + j, currentCol + i));
				}
			}
		}
		return legalMoves;
	}

	@Override
	public void moved() {
		// TODO Auto-generated method stub
		
	}
	

}
