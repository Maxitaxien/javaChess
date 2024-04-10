package inf101.chess.pieces;

import java.util.List;

import inf101.grid.Location;
import inf101.grid.Move;

public class Bishop implements IPiece{
	private char colour;
	private Location loc;
	
	public Bishop(char colour, Location loc) {
		this.colour = colour;
		this.loc = loc;
	}

	@Override
	public char getColour() {
		return this.colour;
	}

	@Override
	public Location getLocation() {
		return this.loc;
	}

	@Override
	public char getRepresentation() {
		return 'B';
	}

	@Override
	public List<Move> getLegalMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}
