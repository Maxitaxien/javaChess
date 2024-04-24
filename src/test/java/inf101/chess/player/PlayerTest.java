package inf101.chess.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;

import inf101.GetStarted;
import inf101.chess.player.ai.DumbChessPlayer;


import org.junit.jupiter.api.Test;

class PlayerTest {

	@BeforeEach
	void testReadConditions() {
		assertTrue(GetStarted.hasRead);
	}

	public static void testConstructor(String symbol, String name, ChessPlayer p) {
		assertEquals(name, p.toString());
		assertEquals(symbol, p.getSymbol());
	}

	@Test
	void testisValidName() {
		assertTrue(AbstractChessPlayer.isValidName("Martin"));
		assertFalse(AbstractChessPlayer.isValidName(" "));
		assertFalse(AbstractChessPlayer.isValidName("\n"));
		assertFalse(AbstractChessPlayer.isValidName("\t"));
	}

	@Test
	void testValidateName() {
		testValidName("Martin");
		try {
			AbstractChessPlayer.validateName("");
		} catch (IllegalArgumentException e) {
			return;
		} catch (Exception e) {
			fail("Should throw an IllegalArgumentException");
		}
	}

	private void testValidName(String name) {
		try {
			assertEquals(name, AbstractChessPlayer.validateName(name));
		} catch (Exception e) {
			fail(name + " is not a vailld name.");
		}
	}

	@Test
	void testConstruct() {
		testConstruct(new DumbChessPlayer('W'));
	}

	private void testConstruct(ChessPlayer player) {
		testValidName(player.toString());

	}

}
