package hw01;

import hw01.game.GameResults;
import hw01.game.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the GameResults object
 * @author Jonathan
 */
class GameResultsTest {

    private GameResults results;

    @BeforeEach
    void setUp() {
        results = new GameResults();
        Score score1 = new Score(6, 60, "User1", true);
        Score score2 = new Score(5, 70, "User2", true);
        Score score3 = new Score(12, 45, "User3", false);

        results.addScore(score1);
        results.addScore(score2);
        results.addScore(score3);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test sortByMoves method
     */
    @Test
    void sortByMoves() {
        Score initialScore = results.getScores().get(0);
        assertEquals("User1", initialScore.getName());

        results.sortByMoves();
        initialScore = results.getScores().get(0);
        assertEquals("User2", initialScore.getName());
    }

    /**
     * Test sortByTime method
     */
    @Test
    void sortByTime() {
        Score initialScore = results.getScores().get(0);
        assertEquals("User1", initialScore.getName());

        results.sortByTime();
        initialScore = results.getScores().get(0);
        assertEquals("User3", initialScore.getName());
    }

    /**
     * Test toString method
     */
    @Test
    void testToString() {
        String expectedOutput = "1. User1 guessed the code in: 6 turns, 60 seconds\n" +
                "2. User2 guessed the code in: 5 turns, 70 seconds\n" +
                "3. User3 did not guess the code\n";
        assertEquals(expectedOutput, results.toString());
    }
}