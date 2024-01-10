package inf101.sem2.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import inf101.GetStarted;
import inf101.sem2.game.games.ConnectFour;
import inf101.sem2.game.games.TicTacToe;
import inf101.sem2.player.Player;
import inf101.sem2.player.ai.DumbPlayer;
import inf101.sem2.terminal.TerminalGraphics;
import org.junit.jupiter.api.Test;

class TestGame {
	@BeforeEach
	void testReadConditions() {
		assertTrue(GetStarted.hasRead);
	}

	@Test
	void testDumbPlayerCanPlay() {
		Player p1 = new DumbPlayer('X');
		Player p2 = new DumbPlayer('O');
		Game<?> game = new TicTacToe(new TerminalGraphics(), p1, p2);
		game.run();
		assertTrue(game.gameOver());

		game = new ConnectFour(new TerminalGraphics(), p1, p2);
		game.run();
		assertTrue(game.gameOver());
	}

}
