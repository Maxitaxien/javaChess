package inf101.sem2.game;

import java.util.List;

import inf101.grid.ChessMove;
import inf101.grid.Location;
import inf101.sem2.player.ChessPlayer;
import inf101.sem2.terminal.ChessPlayerList;

/**
 * This class models turn based games where each round the current player gets
 * to place one piece.
 * Games of this sort has win/tie/loose conditions and rules for where it is
 * possible to place pieces.
 * <p>
 * This type of games does not allow players to move pieces unless
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public abstract class ChessGame {

	/**
	 * Number of miliseconds between each move.
	 */
	public static final int SINGLE_MOVE_TIME = 400;

	/** Keeps track of where players have placed their pieces */
	protected ChessBoard board;

	/** Displays messages and current state of the game */
	protected ChessGraphics graphics;

	/** keeps track of whose turn it is */
	protected ChessPlayerList players;

	/* ************** Constructors ****************/
	public ChessGame(ChessBoard board, ChessGraphics graphics) {
		this.board = board;
		this.graphics = graphics;
		players = new ChessPlayerList();
	}

	public ChessGame(ChessBoard board, ChessGraphics graphics, Iterable<ChessPlayer> players) {
		this(board, graphics);
		for (ChessPlayer p : players) {
			addPlayer(p);
		}
	}

	/* ************** Methods *****************/

	/**
	 * This is the main game loop making sure each player gets to place one piece
	 * each turn.
	 */
	public void run() {
		// game loop
		while (!gameOver()) {
			System.out.println("1");
			try {
				displayPlayerTurn();
				// If player has no valid moves, skip to next player
				if (getPossibleMoves().isEmpty()) {
					players.nextPlayer();
					continue;
				}

				// Get move from player and execute if valid
				ChessMove move = getCurrentPlayer().getMove(copy());
				if (validMove(move)) {
					System.out.println("valid");
					makeMove(move);
					players.nextPlayer();
				} else {
					System.out.println("invalid");
					graphics.displayMessage("That is an invalid move");
				}
			} catch (IllegalArgumentException e) {
				graphics.displayMessage(e.getMessage());
			}
		}
		// print results when game is over
		graphics.displayMessage("Game is over!");
		graphics.display(board);

		for (ChessPlayer p : players) {
			if (isWinner(p)) {
				displayMessage("Player " + p + " has won!");
			}
		}

	}

	/**
	 * When players are asked to make a move we don't want them to change the
	 * state of the game so we send them a copy of the game.
	 */
	public abstract ChessGame copy();

	/**
	 * Create copy of game object, but with a non working graphics object.
	 * The graphics object will be of the type FakeGameGraphics.
	 * 
	 * @return copy of game with fake graphics
	 */
	public ChessGame copyGameWithoutGraphics() {
		ChessDummyGraphics fakeGraphics = new ChessDummyGraphics();
		ChessGame gameCopy = copy();
		gameCopy.graphics = fakeGraphics;
		return gameCopy;
	}

	/**
	 * This method fills in a game with the state of a given game.
	 * The method is there so the implementation of the copy method is simplified in
	 * the subclasses.
	 * 
	 * @param target
	 */
	protected void copyTo(ChessGame target) {
		target.board = board.copy();
		target.graphics = graphics;
		target.players = players.copy();
	}

	/**
	 * This method performs a move for the current player and advances to next
	 * player
	 *
	 * @param loc
	 */
	public abstract void makeMove(ChessMove move);


	/**
	 * Check if move is valid. There cannot be any pices laying in
	 * the locations given in the move, except if the move has over 1 location.
	 * Then the starting location needs a piece that will be moved.
	 * 
	 * @param move
	 * @return true if valid move. False if not.
	 */
	public abstract boolean validMove(ChessMove move);

	/**
	 * Adds a player to the game.
	 * 
	 * @param player
	 */
	protected void addPlayer(ChessPlayer player) {
		players.add(player);
	}

	/**
	 * Gets a copy of the GameBoard.
	 */
	public ChessBoard getGameBoard() {
		return board.copy();
	}

	/**
	 * The game has rules for where the players can place.
	 * This method checks if the current player can place on a given location.
	 * <p>
	 * This is both used to verify that the move current player returns is valid
	 *
	 * @param loc - where to place
	 * @return true if it is a valid move, false otherwise.
	 */
	public boolean canPlace(Location loc) {
		return board.isEmpty(loc);
	}

	/**
	 * Check if currentPlayer is on the given location <code>loc</code>.
	 * 
	 * @param loc
	 * @return true if the current player is on the location. False if not.
	 */
	public boolean hasCurrentPlayer(Location loc) {
		return getCurrentPlayer().getSymbol() == board.get(loc).getSymbol();
	}

	/**
	 * Checks if the given player has reached the winning condition of the game.
	 * 
	 * @param player
	 * @return
	 */
	public abstract boolean isWinner(ChessPlayer player);

	/**
	 * This method checks if the given player is a looser.
	 * In two player games there is normally one winner and one looser or it is a
	 * draw.
	 * But in games with more players this might be different.
	 * 
	 * @param player
	 * @return
	 */
	public boolean isLooser(ChessPlayer player) {
		for (ChessPlayer p : players()) {
			if (p != player && isWinner(p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Computes a score for the player p in the current game
	 * to be used when choosing the best move.
	 *
	 * @param game
	 * @param p
	 * @return
	 */
	public int score(ChessPlayer p) {
		if (isWinner(p)) {
			return 1;
		}
		if (isLooser(p)) {
			return -1;
		}
		return 0;
	}

	public abstract boolean gameOver();

	public void displayBoard() {
		graphics.display(board);
	}

	public void displayMessage(String message) {
		graphics.displayMessage(message);
	}

	public void displayPlayerTurn() {
		String playerTurnString = getCurrentPlayer() + "'s turn.";
		displayMessage(playerTurnString);
	}

	public Iterable<ChessPlayer> players() {
		return players.copy();
	}

	public void nextPlayer() {
		players.nextPlayer();
	}

	public ChessPlayer getCurrentPlayer() {
		return players.getCurrentPlayer();
	}

	public void setCurrentPlayer(ChessPlayer player) {
		players.setCurrentPlayer(player);
	}

	public abstract List<ChessMove> getPossibleMoves();

	public ChessGraphics getGraphics() {
		return graphics;
	}

	public abstract String getName();

	public void restart() {
		board.clear();
		players.restart();
		graphics.display(board);
		graphics.displayMessage("Welcome!");
	}

	public void sleep() {
		if (graphics instanceof DummyGraphics)
			return;
		try {
			Thread.sleep(SINGLE_MOVE_TIME);
		} catch (InterruptedException e) {
			System.err.println("Sleep interrupted");
		}
	}
}
