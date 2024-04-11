package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class Rook extends Piece {
	private boolean hasMoved = false;
	
	public Rook(char colour, char symbol, Location loc) {
		super(colour, symbol, loc);
	}

	@Override
	public List<Location> getLegalMoves(ChessBoard board) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void moved() {
		hasMoved = true;
		
	}

}
