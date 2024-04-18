package inf101.chess.pieces;

import java.util.List;

import inf101.chess.logic.DirectionalCalculator;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class Bishop extends Piece{
	public Bishop(char colour, Location loc) {
		super(colour, 'B', loc);
	}

	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		return calculator.calculateDiagonal(getLocation(), board, getColour());
	}

	@Override
	public void moved() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasMoved() {
		// TODO Auto-generated method stub
		return false;
	}

}
