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
		public int getValue() {
			return 3;
		}
	
	@Override
	public Bishop copy() {
		Bishop newBishop = new Bishop(this.getColour(), this.getLocation());
		if (hasMoved()) {
			newBishop.moved();
		}
		return newBishop;
	}
	
	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		return calculator.calculateDiagonal(getLocation(), board, getColour());
	}


	

}
