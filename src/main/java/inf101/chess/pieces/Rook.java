package inf101.chess.pieces;

import java.util.List;

import inf101.chess.logic.DirectionalCalculator;
import inf101.chess.model.ChessBoard;
import inf101.chess.model.IChessBoard;
import inf101.grid.Location;

public class Rook extends Piece {
	private boolean hasMoved = false;
	
	public Rook(char colour, Location loc) {
		super(colour, 'R', loc);
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}

	@Override
	public List<Location> getPossibleMoves(IChessBoard board) {
		DirectionalCalculator calculator = new DirectionalCalculator();
		return calculator.calculateCardinal(this.getLocation(), board, getColour());
	}


	@Override
	public void moved() {
		hasMoved = true;
		
	}

}
