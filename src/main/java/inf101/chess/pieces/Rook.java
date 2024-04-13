package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class Rook extends Piece {
	private boolean hasMoved = false;
	
	public Rook(char colour, Location loc) {
		super(colour, 'R', loc);
	}

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		return calculator.calculateCardinal(this.getLocation(), board);
	}


	@Override
	public void moved() {
		hasMoved = true;
		
	}

}
