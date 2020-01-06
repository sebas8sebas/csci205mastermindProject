package hw01.solver;

import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for Solver abstract class
 */
class SolverTest {

    /**
     * Test the getSimTime method
     * @throws Exception
     */
    @Test
    void getSimTime() throws Exception {

        Solver customSolver = new CustomSolver();

        long startTime = System.nanoTime();
        customSolver.simulate(1000);
        long endTime = System.nanoTime();

        int ellapsedTime = (int)((startTime - endTime) * Math.pow(10, -9));

        assertEquals(ellapsedTime, customSolver.getSimTime());
    }

    /**
     * Test the getStats method
     * @throws Exception
     */
    @Test
    void getStats() throws Exception {

        Solver randSolver = new RandomSolver();

        randSolver.simulate(1000);

        // Make sure number of simulations is correct
        IntSummaryStatistics stats = randSolver.getStats();
        assertEquals(1000, stats.getCount());

    }
}