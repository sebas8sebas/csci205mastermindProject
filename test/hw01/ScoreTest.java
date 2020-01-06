package hw01;

import hw01.game.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test to test the Score class
 * @author Jonathan
 */
class ScoreTest {

    /**
     * Verify toString method of Score object
     * @author Jonathan
     */
    @Test
    void testToString() {
        Score score1 = new Score(5, 40, "User1", true);
        assertEquals("User1 guessed the code in: 5 turns, 40 seconds", score1.toString());

        Score score2 = new Score(12, 120, "User2", false);
        assertEquals("User2 did not guess the code", score2.toString());
    }
}