package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.ChessBoard;

public class Bishop extends Piece{
	public Bishop(char colour, Location loc) {
		super(colour, 'B', loc);
	}

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		return calculator.calculateDiagonal(getLocation(), board);
	}

	@Override
	public void moved() {
		// TODO Auto-generated method stub
		
	}

}
