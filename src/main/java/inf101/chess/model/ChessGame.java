package inf101.chess.model;


import inf101.chess.logic.GameStateDeterminer;
import inf101.chess.player.ChessPlayer;
import inf101.chess.player.ChessPlayerList;
import inf101.chess.player.ai.ChessDummyGraphics;
import inf101.chess.view.ChessGraphics;
import inf101.grid.ChessMove;
import inf101.grid.Location;

/**
 * Inspired by the Game class by Martin Vatshelle
 * Adapted and expandedto work with chess
 */
public abstract class ChessGame {

	/**
	 * Number of miliseconds between each move.
	 */
	public static final int SINGLE_MOVE_TIME = 400;

	/** Keeps track of where players have placed their pieces */
	protected IChessBoard board;

	/** Displays messages and current state of the game */
	protected ChessGraphics graphics;

	/** keeps track of whose turn it is */
	protected ChessPlayerList players;
	
	/** keeps track fo the current game state */
	protected GameState state;
	
	/* ************** Constructors ****************/
	public ChessGame(IChessBoard board, ChessGraphics graphics, GameState state) {
		this.board = board;
		this.graphics = graphics;
		this.state = state;
		players = new ChessPlayerList();
	}

	public ChessGame(IChessBoard board, ChessGraphics graphics, GameState state, Iterable<ChessPlayer> players) {
		this(board, graphics, state);
		for (ChessPlayer p : players) {
			addPlayer(p);
		}
	}

	
	/**
	 * Returns the current state of the game using a GameStateDeterminer.
	 * @param inputBoard the current board state
	 * @return a GameState (active, check, checkmate, stalemate)
	 */
	public GameState determineState(IChessBoard inputBoard) {
		GameStateDeterminer determiner = new GameStateDeterminer(inputBoard, getCurrentPlayerChar());
		return determiner.determineCheck();
	}
	
	/**
	 * Returns the GameState of the game,
	 * as calculated by determineState.
	 * @return current state of game
	 */
	public GameState getState() {
		return this.state;
	}
	

	/**
	 * When players are asked to make a move we don't want them to change the
	 * state of the game, so we send them a copy of the game.
	 */
	public abstract ChessGame copy();

	/**
	 * Create copy of game object, but with a non working graphics object.
	 * The graphics object will be of the type ChessDummyGraphics.
	 * 
	 * @return copy of the game with fake graphics
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
		target.state = state;
	}

	// TODO: Make a move be a capture or a normal move based on an attribute in the ChessMove
	// Handle the different cases differently.
	/**
	* This method performs a move for the current player and advances to next
	* player. 
	*
	* @param move the move to make
	*/
	public void makeMove(ChessMove move) {				
		board.get(move.getFrom()).moved();
		board.movePiece(move.getFrom(), move.getTo());
		if (move.isCastle()) {
			board.get(move.getRookFrom()).moved();
			board.movePiece(move.getRookFrom(), move.getRookTo());
		}
	}

	
    
    
	/**
	 * Adds a player to the game.
	 * 
	 * @param player the player to add
	 */
	protected void addPlayer(ChessPlayer player) {
		players.add(player);
	}

	/**
	 * Gets a copy of the GameBoard.
	 * @return a copy
	 */
	public IChessBoard getGameBoard() {
		return board.copy();
	}

	
	/**
	 * Inspired by the method used in Othello,
	 * checks if the piece at the square belongs to the opposing player
	 * @param loc
	 * @return boolean indicating if piece belongs to opposing player
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
	
	public char getCurrentPlayerChar() {
		return players.getCurrentPlayerChar();
	}

	public ChessPlayer getCurrentPlayer() {
		return players.getCurrentPlayer();
	}

	public void setCurrentPlayer(ChessPlayer player) {
		players.setCurrentPlayer(player);
	}

	public ChessGraphics getGraphics() {
		return graphics;
	}

	public abstract String getName();

	public void restart() {
		board.clearBoard();
		board.initializeBoard();
		players.restart();
		graphics.display(board);
		graphics.displayMessage("Welcome!");
	}

	public void sleep() {
		if (graphics instanceof ChessDummyGraphics)
			return;
		try {
			Thread.sleep(SINGLE_MOVE_TIME);
		} catch (InterruptedException e) {
			System.err.println("Sleep interrupted");
		}
	}
}
