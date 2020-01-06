package hw01.solver;

import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for MinimaxSolver class
 */
class MinimaxSolverTest {


    /**
     * Test for minmax solver, tests that average
     * is between the interval 4.47 +- 0.3
     * for 100 simulations
     * This test is statistical so there is a small
     * probability that it will fail
     * @throws Exception
     */
    @Test
    void simulate() throws Exception {
        Solver minMaxSolver = new MinimaxSolver();

        minMaxSolver.simulate(100);
        IntSummaryStatistics stats = minMaxSolver.getStats();


        //Test average with an error margin of 0.3
        assertEquals(4.47, stats.getAverage(), 0.3);

    }

}