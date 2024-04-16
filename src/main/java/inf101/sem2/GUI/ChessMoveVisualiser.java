package inf101.sem2.GUI;

import java.awt.Color;
import java.util.List;

import inf101.chess.pieces.Piece;
import inf101.grid.Grid;
import inf101.grid.Location;
import inf101.sem2.game.ChessBoard;
import inf101.sem2.game.ChessGame;

/**
 * If activated, can be used to draw little circles indicating
 * the legal moves for the currently selected piece.
 */
public class ChessMoveVisualiser implements GridVisualiser{
	private ChessGame game;
	
	private ChessGUI gui;
	
	private List<Location> previousPossibleMoves;
	
	
	public ChessMoveVisualiser(ChessGame game, ChessGUI gui) {
		this.game = game;
		this.gui = gui;
	}
	
	@Override
	public void clearPossibleMoves() {
		previousPossibleMoves.clear();
	}

	@Override
	public void highlightSelectedPanel(Location location, Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPossibleMoves(Location pieceLocation) {
		ChessBoard board = game.getGameBoard();
		Piece piece = board.get(pieceLocation);
        if (piece != null) {
            List<Location> possibleMoves = piece.getPossibleMoves(board);
            for (Location loc : possibleMoves) {
                GamePanel panel = gui.;
                if (panel != null) {
                    panel.setPossibleMove(true);
                }
            }
            previousPossibleMoves = possibleMoves;
        	}
	}
}
