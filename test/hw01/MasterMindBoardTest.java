package hw01;


import hw01.game.MasterMindBoard;
import hw01.game.MasterMindBoardException;
import hw01.game.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests to test the MasterMindBoard class
 * @author Sebastian
 * @author Jonathan
 */
class MasterMindBoardTest {

    MasterMindBoard board;

    @BeforeEach
    void setUp() throws MasterMindBoardException {
        int[] code = {1, 3, 3, 5};
        board = new MasterMindBoard(code);

    }


    /**
     * Test guess method
     * @author Sebastian
     * @author Jonathan
     */
    @Test
    void guessTest() throws Exception {
        Row guessResult = board.guess(new int[]{1, 1, 1, 1});
        assertEquals("*---", guessResult.toString());

        guessResult = board.guess(new int[]{3, 1, 5, 4});
        assertEquals("+++-", guessResult.toString());

        guessResult = board.guess(new int[]{3, 3, 2, 2});
        assertEquals("*+--", guessResult.toString());

        guessResult = board.guess(new int[]{2, 2, 2, 2});
        assertEquals("----", guessResult.toString());

        guessResult = board.guess(new int[]{1, 3, 5, 3});
        assertEquals("**++", guessResult.toString());

        guessResult = board.guess(new int[]{3, 3, 0, 3});
        assertEquals("*+--", guessResult.toString());

        guessResult = board.guess(new int[]{1, 3, 3, 5});
        assertEquals("****", guessResult.toString());

    }


    /**
     * Test Constructor Exception
     * @author Sebastian
     */
    @Test
    void testConstructorException(){

        assertThrows(MasterMindBoardException.class, () -> new MasterMindBoard(new int[]{3, 3, 4}));
        assertThrows(MasterMindBoardException.class, () -> new MasterMindBoard(new int[]{3, 3, 4, 5, 6, 6}));
        assertThrows(MasterMindBoardException.class, () -> new MasterMindBoard(new int[]{7, 7, 7, 7}));
    }

    /**
     * Test checkWin method
     * @author Jonathan
     */
    @Test
    void checkWin() throws Exception {
        assertEquals(false, board.checkWin());

        board.guess(new int[] {1,3,4,4});
        assertEquals(false, board.checkWin());

        board.guess(new int[]{1, 3, 3, 5});
        assertEquals(true, board.checkWin());
    }

    /**
     * Test getGuesses method
     * @author Jonathan
     */
    @Test
    void getGuesses() throws Exception {
        assertEquals(0, board.getGuesses());
        board.guess(new int[] {1,3,4,4});
        board.guess(new int[] {1,2,3,4});
        board.guess(new int[]{1, 3, 3, 5});
        assertEquals(3, board.getGuesses());

    }
}