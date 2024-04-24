package inf101.chess.model;


import inf101.chess.logic.GameStateDeterminer;
import inf101.chess.player.ChessPlayer;
import inf101.chess.player.ChessPlayerList;
import inf101.chess.view.ChessDummyGraphics;
import inf101.chess.view.ChessGraphics;
import inf101.grid.ChessMove;
import inf101.grid.Location;

/**
 * Inspired by the Game class by Martin Vatshelle
 * Adapted and expanded to work with chess
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
	 * @return current state of game (ACTIVE, CHECK, CHECKMATE, STALEMATE)
	 */
	public GameState getState() {
		return this.state;
	}
	
	/**
	 * Manually set the game state,
	 * useful for testing.
	 * @param setTo the GameState to set to
	 */
	public void setState(GameState setTo) {
		this.state = setTo;
	}

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
	 * Inspired by the method used in Othello,
	 * checks if the piece at the square belongs to the opposing player
	 * @param loc
	 * @return boolean indicating if piece belongs to opposing player
	 */
	public boolean isOpponent(Location loc) {
		if (!board.isOnBoard(loc))
			return false;
		if (board.get(loc) == null)
			return false;
		if (board.get(loc).getColour() == getCurrentPlayerChar())
			return false;
		return true;
	}
	
	/**
	 * Gets a copy of the GameBoard.
	 * @return a copy
	 */
	public IChessBoard getGameBoard() {
		return board.copy();
	}
	
	/**
	 * Returns a copy of the current players in the game
	 * @return a list of ChessPlayers
	 */
	public Iterable<ChessPlayer> players() {
		return players.copy();
	}

	/**
	 * Moves the turn to the next player in the list.
	 */
	public void nextPlayer() {
		players.nextPlayer();
	}
	
	/**
	 * Gets the character of the current player.
	 * @return 'W' or 'B' depending on if it is White or Black's turn
	 */
	public char getCurrentPlayerChar() {
		return players.getCurrentPlayerChar();
	}

	/**
	 * Gets the ChessPlayer object of the current player
	 * @return a ChessPlayer (player 1 or player 2)
	 */
	public ChessPlayer getCurrentPlayer() {
		return players.getCurrentPlayer();
	}

	/**
	 * Sets the current player manually, used for restarts
	 * @param player the player to set to
	 */
	public void setCurrentPlayer(ChessPlayer player) {
		players.setCurrentPlayer(player);
	}
	
	/**
	 * When players are asked to make a move we don't want them to change the
	 * state of the game, so we send them a copy of the game.
	 */
	public abstract ChessGame copy();


	/**
	 * Displays the board through the use of graphics
	 */
	public abstract void displayBoard();

	/**
	 * Displays a message at the top of the game window through the use of graphics
	 * @param message the message to display
	 */
	public abstract void displayMessage(String message);

	/**
	 * Displays the player that starts the game (the player with white)
	 * Is called at the start of the game loop
	 */
	public abstract void displayStartingPlayer();
	
	/**
	 * Displays the last move of the last player to move
	 * Used in the game loop
	 * @param move the move that was made
	 * @param turn which turn of the game we are on
	 * @param white boolean indicating if white made the move or not
	 * @param check whether the move came with check
	 */
	public abstract void displayLastMove(ChessMove move, int turn, boolean white, boolean check);
	

	/**
	 * Sets up pieces on the board through a call to
	 * the board.
	 */
	protected abstract void initialize();
	
	/**
	 * Get the graphics of the current chess game
	 * @return the graphics of the game
	 */
	public abstract ChessGraphics getGraphics();

	/**
	 * Get the name of the game (should be "Chess", but can be changed if other names are wanted)
	 * @return a string representing the game name
	 */
	public abstract String getName();

	/**
	 * Restarts the game, reinitializes board.
	 */
	public abstract void restart();

	/**
	 * Sleeps for the time between moves, as defined in the game.
	 */
	public abstract void sleep();
}
