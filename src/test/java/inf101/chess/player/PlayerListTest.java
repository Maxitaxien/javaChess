package inf101.chess.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import inf101.GetStarted;
import inf101.chess.player.ai.DumbChessPlayer;

import org.junit.jupiter.api.Test;


class PlayerListTest {

	@BeforeEach
	void testReadConditions() {
		assertTrue(GetStarted.hasRead);
	}

	@Test
	void testAlternatingPlayers() {
		ChessPlayerList players = new ChessPlayerList();
		ChessPlayer p1 = new DumbChessPlayer('W');
		ChessPlayer p2 = new DumbChessPlayer('B');
		players.add(p1);
		players.add(p2);
		assertEquals(p1, players.getCurrentPlayer());
		assertEquals(p2, players.nextPlayer());
		assertEquals(p2, players.getCurrentPlayer());
		assertEquals(p1, players.nextPlayer());
		assertEquals(p1, players.getCurrentPlayer());
		assertEquals(p2, players.nextPlayer());
		assertEquals(p2, players.getCurrentPlayer());
	}

	@Test
	void testAddPlayers() {
		ChessPlayerList players = new ChessPlayerList();
		ChessPlayer p1 = new DumbChessPlayer('W');
		ChessPlayer p2 = new DumbChessPlayer('B');
		players.add(p1);
		players.add(p2);

		assertThrows(IllegalArgumentException.class, () -> players.add(p1));

		ChessPlayer p3 = new DumbChessPlayer('W');
		assertThrows(IllegalArgumentException.class, () -> players.add(p3));
	}

	@Test
	void testRemovePlayers() {
		ChessPlayerList players = new ChessPlayerList();
		ChessPlayer p1 = new DumbChessPlayer('W');
		ChessPlayer p2 = new DumbChessPlayer('B');
		players.add(p1);

		assertThrows(IllegalArgumentException.class, () -> players.remove(p2));
		players.add(p2);

		players.nextPlayer();
		assertEquals(p2, players.getCurrentPlayer());

		players.nextPlayer();
		players.remove(p1);
		assertEquals(p2, players.getCurrentPlayer());
		players.add(p1);

		players.nextPlayer();
		players.remove(p1);
		assertEquals(p2, players.getCurrentPlayer());
		players.remove(p2);
	}
}
