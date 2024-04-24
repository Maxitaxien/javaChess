package inf101.chess.pieces;

import java.util.List;

import inf101.chess.logic.DirectionalCalculator;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class Queen extends Piece {

	public Queen(char colour, Location loc) {
		super(colour, 'Q', loc);
	}
	
	@Override
		public int getValue() {
			return 9;
		}
	
	
	@Override
	public Queen copy() {
		Queen newQueen = new Queen(this.getColour(), this.getLocation());
		if (hasMoved()) {
			newQueen.moved();
		}
		return newQueen;
	}
	
	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		List<Location> cardinalMoves = calculator.calculateCardinal(getLocation(), board, getColour());
		List<Location> diagonalMoves = calculator.calculateDiagonal(getLocation(), board, getColour());

		cardinalMoves.addAll(diagonalMoves);
		
		return cardinalMoves;
	}

	

}
