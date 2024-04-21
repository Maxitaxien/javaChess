package inf101.chess.model;

import java.util.ArrayList;
import java.util.List;

import inf101.chess.logic.GameStateDeterminer;
import inf101.chess.pieces.King;
import inf101.chess.pieces.Piece;
import inf101.chess.player.ChessPlayer;
import inf101.chess.player.ChessPlayerList;
import inf101.chess.player.ai.ChessDummyGraphics;
import inf101.chess.view.ChessGraphics;
import inf101.grid.ChessMove;
import inf101.grid.Location;

/**
 * This class models turn based games where each round the current player gets
 * to place one piece.
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
	
	/** keeps track fo the current game state */
	protected GameState state;
	
	/* ************** Constructors ****************/
	public ChessGame(ChessBoard board, ChessGraphics graphics, GameState state) {
		this.board = board;
		this.graphics = graphics;
		this.state = state;
		players = new ChessPlayerList();
	}

	public ChessGame(ChessBoard board, ChessGraphics graphics, GameState state, Iterable<ChessPlayer> players) {
		this(board, graphics, state);
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
			try {
				displayPlayerTurn();
				if (getMoves().isEmpty()) {
					this.state = determineState(this.board);
					if (this.state == GameState.CHECK) {
						System.out.println("CHECKMATE!");
					}
					else {
						System.out.println("STALEMATE!");
					}
				}

				// Get move from player and execute if valid
				ChessMove move = getCurrentPlayer().getMove(copy());
				
				if (validMove(move)) {
					makeMove(move, this.state);
					players.nextPlayer();
					
					this.state = determineState(this.board);
					
					if (this.state == GameState.CHECK) {
						System.out.println("CHECK!");
					}
				} else {
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
	
	public GameState determineState(ChessBoard inputBoard) {
		GameStateDeterminer determiner = new GameStateDeterminer(inputBoard, getCurrentPlayerChar());
		return determiner.determineCheck();
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
	
	public GameState getState() {
		return this.state;
	}

	/**
	 * This method fills in a game with the state of a given game.
	 * The method is there so the implementation of the copy method is simplified in
	 * the subclasses.
	 * 
	 * @param target
	 */
	protected void copyTo(ChessGame target) {
		target.board = (ChessBoard) board.copy(false);
		target.graphics = graphics;
		target.players = players.copy();
	}

	// TODO: Make a move be a capture or a normal move based on an attribute in the ChessMove
			// Handle the different cases differently.
			/**
			 * This method performs a move for the current player and advances to next
			 * player. 
			 * Credit to the makeMove function in BlobWars.
			 *
			 * @param loc
			 */
	public void makeMove(ChessMove move, GameState state) {		
		if (!validMove(move))
			throw new IllegalArgumentException("Cannot make move:\n" + move);
		
		board.get(move.getFrom()).moved();
		board.movePiece(move.getFrom(), move.getTo());
		if (move.isCastle()) {
			board.get(move.getRookFrom()).moved();
			board.movePiece(move.getRookFrom(), move.getRookTo());
		}
	}


	/**
	 * Check if move is valid. There cannot be any pices laying in
	 * the locations given in the move, except if the move has over 1 location.
	 * Then the starting location needs a piece that will be moved.
	 * 
	 * @param move
	 * @return true if valid move. False if not.
	 */
	
	/**
	 * Checks if the given move is valid. As this is calculated differently
	 * for each piece, this is calculated in the Piece classes.
	 * 
	 * @param move the move to make
	 * @return true if valid move. False if not.
	 */
	
	public boolean validMove(ChessMove move) {
	    // Preliminary checks for null move or incorrect starting position
	    if (move == null || board.get(move.getFrom()) == null || board.get(move.getFrom()).getColour() != getCurrentPlayerChar()) {
	        return false;
	    }

	    // Check if the destination is occupied by an opponent's piece for capture
	    Piece destinationPiece = board.get(move.getTo());
	    if (destinationPiece != null && destinationPiece.getColour() == getCurrentPlayerChar()) {
	        return false; // Cannot capture own piece
	    }

	    // Simulate the move and check if king will be in danger
	    GameStateDeterminer determiner = new GameStateDeterminer(board, getCurrentPlayerChar());
	
	    if (determiner.kingInDangerAfterMove(move)) {
	        return false; // Move leaves king in check
	    }

	    // Additional checks for the legality of the move per piece type
	    return board.get(move.getFrom()).getPossibleMoves(board).contains(move.getTo());
	}
	
	/**
	 * Look through grid. When we find a piece, calculate it's 
	 * legal moves. 
	 * 
	 */
    public List<ChessMove> getMoves() {
		List<ChessMove> possibleMoves = new ArrayList<>();
	        for (Location from: board.locations()) {
	        	if (board.get(from) != null && board.get(from).getColour() == players.getCurrentPlayerChar()) {
	        		Piece pieceToMove = board.get(from);
	        		List<Location> moveTo = pieceToMove.getPossibleMoves(board);
	        		// If the move is not made by the king, just add all the moves as normal:
	        		if (pieceToMove.getSymbol() != 'K') {
		        		for (Location to : moveTo) {
		        			ChessMove move = new ChessMove(from, to, pieceToMove);
		        			if (validMove(move)) {
		        				possibleMoves.add(move);
		        			}
		        		}
	        		}

	        		// If the move is being made by the king, check if it is a castle and handle these differently
	        		// We can check this by seeing if the valid move moves the king more than one column
	        		else {
	        			for (Location to : moveTo) {
	        				ChessMove move;
	        				if (from.col - to.col < -1) {
	        					// Indicates queenside castling, which means the rook is at four to the left
	        					// from the current king position
	        					System.out.println(from);
	        					Location oldRookLocation = new Location(to.row, from.col - 4);
	        					Location newRookLocation = new Location(to.row, from.col - 1);
	        					move = new ChessMove(from, to, pieceToMove, 
	        							oldRookLocation, newRookLocation);
	        					move.castled();
	        				}
	        				else if (from.col - to.col > 1) {
	        					System.out.println(from);
	        					// Indicates kingside castling, which means the rook is at three to the right
	        					// from the current king position
	        					Location oldRookLocation = new Location(to.row, from.col + 3);
	        					Location newRookLocation = new Location(to.row, from.col + 1);
	        					move = new ChessMove(from, to, pieceToMove, 
	        							oldRookLocation, newRookLocation);
	        					move.castled();
	        				}
	        				else {
	        					// Indicates a normal move
	        					move = new ChessMove(from, to, pieceToMove);
	        				}
	        				if (validMove(move)) {
		        				possibleMoves.add(move);
	        				}
	        					
	        				}
        					
        				}
        			}
        		}	        		
        return possibleMoves;
	}
    
    


    
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
		return (ChessBoard) board.copy(false);
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
		return board.squareEmpty(loc);
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
	public boolean isLoser(ChessPlayer player) {
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
		if (isLoser(p)) {
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
