package hw01;

import hw01.game.Row;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test to test the Row class
 * @author Sebastian
 * @author Jonathan
 */
class RowTest {


    /**
     * Verify toString method of Row object
     * @author Sebastian
     * @author Jonathan
     */
    @Test
    void testToString() {
        Row testRow = new Row(2, 1, 1);
        assertEquals("**+-", testRow.toString());

        Row testRow2 = new Row(3,0,1);
        assertEquals("***-", testRow2.toString());

        Row testRow3 = new Row(0, 2, 2);
        assertEquals("++--", testRow3.toString());
    }
}