package inf101.sem2.game.games;

import java.util.ArrayList;
import java.util.List;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.game.Graphics;
import inf101.sem2.game.SingleMoveGame;
import inf101.sem2.player.Player;

public class Chess extends SingleMoveGame {
	
	/**
	 * Initializing chess much in the same way as Othello.
	 * Credit for this part of the code goes to the creator of Othello.java
	 */
	public Chess(Graphics graphics, Player p1, Player p2) {
		super(new GameBoard(8, 8), graphics);
		addPlayer(p1);
		addPlayer(p2);
		init();
	}
	
	public Chess(Graphics graphics, Iterable<Player> players) {
		super(new GameBoard(8, 8), graphics, players);
		init();
	}
	
	private void init() {
		// TODO: Initialize chess pieces
		// Currently dummy characters to represent colour
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < board.numRows(); col++) {
				board.set(new Location(row, col), getCurrentPlayer());
			}
		}
		
		players.nextPlayer();
		
		for (int row = 7; row > 5; row--) {
			for (int col = 0; col < board.numRows(); col++) {
				board.set(new Location(row, col), getCurrentPlayer());
			}
		}
		
		players.nextPlayer();
	}

	@Override
	public Game<Location> copy() {
		Game<Location> newGame = new Chess(this.graphics, players);
		copyTo(newGame);
		return newGame;
		}

	@Override
	public boolean isWinner(Player player) {
		// TODO 
		return false;
	}

	@Override
	public boolean gameOver() {
		// TODO Implement logic for checkmate
		return false;
	}
	
	/**
	 * Credit for this method once again goes to Othello.
	 * @param loc
	 * @return
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
