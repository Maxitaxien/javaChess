package inf101.chess.main;

import inf101.chess.view.ChessMenu;

/**
 * Class to start the menu and runs the game.
 * This is where the game is started from.
 */
public class MainChessGUI {
	public static void main(String[] args) {
 		ChessMenu menu = new ChessMenu();
 		menu.run();
	}
}
