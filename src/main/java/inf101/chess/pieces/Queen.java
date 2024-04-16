package inf101.chess.pieces;

import java.util.List;

import inf101.chess.logic.DirectionalCalculator;
import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;

public class Queen extends Piece {

	public Queen(char colour, Location loc) {
		super(colour, 'Q', loc);
	}

	@Override
	public List<Location> getPossibleMoves(ChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		List<Location> cardinalMoves = calculator.calculateCardinal(getLocation(), board, getColour());
		List<Location> diagonalMoves = calculator.calculateDiagonal(getLocation(), board, getColour());

		cardinalMoves.addAll(diagonalMoves);
		
		return cardinalMoves;
	}

	@Override
	public void moved() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean hasMoved() {
		return false;
	}

}
