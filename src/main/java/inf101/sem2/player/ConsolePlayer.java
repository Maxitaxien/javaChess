package inf101.sem2.player;

import static inf101.sem2.terminal.TerminalInput.readInt;
import static inf101.sem2.terminal.TerminalInput.readName;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import inf101.grid.Location;
import inf101.grid.Move;
import inf101.sem2.game.Game;
import inf101.sem2.game.GameBoard;
import inf101.sem2.game.MultiMoveGame;
import inf101.sem2.game.SingleMoveGame;
import inf101.sem2.game.games.ConnectFour;

/**
 * This Player enables the user to play the game by using the keyboard.
 * There is in reality two different players here,
 * one for TicTacToe and one for FourInARow
 * If you check out the makeMove method it is only an if else choosing between
 * the two types
 * <p>
 * I thought still it was good to keep all this code in one class as then
 * all players can be used in both games.
 *
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 */
public class ConsolePlayer extends AbstractPlayer {

	/**
	 * The Scanner used to read from the terminal.
	 * We use one single static Scanner for all players
	 * rather than starting a new Scanner as all ConsolePlayers
	 * on the same computer will be typing on the same keyboard.
	 * If this game were to be played over network this might need to change.
	 */
	static Scanner sc = new Scanner(System.in);

	/**
	 * Creates new console player
	 *
	 * @param symbol
	 * @param name
	 */
	public ConsolePlayer(char symbol, String name) {
		super(symbol, name);
	}

	/**
	 * Creates new Console player by reading name from Console
	 *
	 * @param symbol
	 */
	public ConsolePlayer(char symbol) {
		super(symbol, readPlayerName(symbol));
	}

	/**
	 * Reads in a move from terminal
	 */
	public Location getMoveFromGame(SingleMoveGame game) {
		game.displayBoard();
		if (game instanceof ConnectFour) {
			return getLocationConnectFour((ConnectFour) game);
		}

		return getLocation(game.getGameBoard());
	}

	/**
	 * Reads in a move from terminal
	 */
	public Move getMoveFromGame(MultiMoveGame game) {
		game.displayBoard();

		return getMoveLocations(game.getGameBoard());
	}

	/**
	 * Reads input for the FourInARow game
	 */
	private Location getLocationConnectFour(ConnectFour game) {
		System.out.println("Player " + this + " type [Col] to make a move.");
		int col = 0;
		while (true) {
			col = readInt(sc);
			Location landLocation = game.drop(col);
			if (game.canPlace(landLocation)) {
				return landLocation;
			} else {
				System.out.println("Can not place there. Try again");
			}
		}
	}

	/**
	 * Reads in a player name, only requirement is that name is not empty
	 *
	 * @param sc
	 */
	public static String readPlayerName(char symbol) {
		System.out.println("Player " + symbol + " type your name:");
		return readName(sc, "Name");
	}

	/**
	 * Can read input for any game where input is an x,y location
	 *
	 * @param gameBoard
	 * @return
	 */
	private Location getLocation(GameBoard gameBoard) {
		System.out.println("Player " + this + " type [Row] [Col] to make a move.");
		boolean done = false;
		int row = 0;
		int col = 0;
		while (!done) {
			row = readInt(sc);
			col = readInt(sc);
			if (gameBoard.canPlace(new Location(row, col))) {
				done = true;
			} else {
				System.out.println("Can not place there. Try again");
			}
		}
		return new Location(row, col);
	}

	private Move getMoveLocations(GameBoard gameBoard) {
		List<Location> locations = new ArrayList<>(2);
		System.out.println("Player " + this + " type [Row] [Col] two times to make a move.");
		for (int i = 0; i < 2; i++) {
			boolean done = false;
			int row = 0;
			int col = 0;
			while (!done) {
				row = readInt(sc);
				col = readInt(sc);
				if (gameBoard.isOnBoard(new Location(row, col))) {
					done = true;
				} else {
					System.out.println("Can not place there. Try again");
				}
			}
			locations.add(new Location(row, col));
		}
		return new Move(locations);

	}

	@Override
	public <T> T getMove(Game<T> game) {
		if (game instanceof SingleMoveGame)
			return (T) getMoveFromGame((SingleMoveGame) game);
		if (game instanceof MultiMoveGame)
			return (T) getMoveFromGame((MultiMoveGame) game);
		throw new UnsupportedOperationException("Console player does not support this type of game");
	}
}
