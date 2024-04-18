package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.chess.model.ChessBoard;
import inf101.chess.model.ChessGame;
import inf101.chess.model.ChessGraphics;
import inf101.chess.model.GameState;
import inf101.chess.player.ChessPlayer;

public class Chess extends ChessGame {
	
	public Chess(ChessGraphics graphics, GameState state, ChessPlayer p1, ChessPlayer p2) {
		super(new ChessBoard(8, 8), graphics, state);
		addPlayer(p1);
		addPlayer(p2);
		initialize();
	}
	
	public Chess(ChessGraphics graphics, GameState state, Iterable<ChessPlayer> players) {
		super(new ChessBoard(8, 8), graphics, state, players);
		initialize();
	}
	
	/**
	 * Sets up piecces on the board through a call to
	 * the board.
	 */
	private void initialize() {
		board.initializeBoard();
	}
	
	@Override
	public void makeMove(ChessMove move, GameState state) {
		super.makeMove(move, state);
		displayBoard();
	}
	
	public List<ChessMove> getPossibleMoves() {
		if (this.state == GameState.ACTIVE) {
			System.out.println("Not check.");
		}
		else {
			System.out.println("Check!");
		}
		return getMoves();
	}



	@Override
	public Chess copy() {
		Chess newGame = new Chess(graphics, state, players);
		copyTo(newGame);
		return newGame;
		}

	@Override
	public boolean isWinner(ChessPlayer player) {
		// TODO 
		return false;
	}

	@Override
	public boolean gameOver() {
		// TODO Implement logic for checkmate
		// If gamestate = check and no legal moves
		return false;
	}
	
	/**
	 * Credit for this method once again goes to Othello.
	 * @param loc
	 * @return boolean indicating if piece belongs to current player
	 */
	public boolean isOpponent(Location loc) {
		if (!board.isOnBoard(loc))
			return false;
		if (board.get(loc) == getCurrentPlayer())
			return false;
		if (board.get(loc) == null)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return "Chess";
	}


}
