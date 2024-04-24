package inf101.chess.pieces;

import java.util.List;

import inf101.chess.logic.DirectionalCalculator;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class Rook extends Piece {
	public Rook(char colour, Location loc) {
		super(colour, 'R', loc);
	}
	
	@Override
	public int getValue() {
		return 5;
	}
	
	@Override
	public Rook copy() {
		Rook newRook = new Rook(this.getColour(), this.getLocation());
		if (hasMoved()) {
			newRook.moved();
		}
		return newRook;
	}
	
	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		return calculator.calculateCardinal(this.getLocation(), board, getColour());
	}

}
