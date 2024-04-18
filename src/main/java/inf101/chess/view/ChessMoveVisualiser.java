package inf101.chess.view;

import java.awt.Color;
import java.util.List;

import inf101.chess.model.ChessBoard;
import inf101.chess.pieces.Piece;
import inf101.grid.Grid;
import inf101.grid.Location;
/**
 * If activated, can be used to draw little circles indicating
 * the legal moves for the currently selected piece.
 */
public class ChessMoveVisualiser implements GridVisualiser{	
	
	@Override
	public void clearPossibleMoves() {
		;
	}

	
	@Override
	public void showPossibleMoves(GamePanel panel, ChessBoard board, char playerColour) {
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


	@Override
	public void highlightSelectedPanel(GamePanel panel, char playerColour) {
		// TODO Auto-generated method stub
		
	}
