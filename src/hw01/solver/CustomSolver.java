/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom / Sebastian Ascoli
 * Section: 11 am / 9 am
 * Date: 10/19/2019
 * Time: 4:12 PM
 *
 * Project: csci205_hw
 * Package: hw01.solver
 * Class: CustomSolver
 *
 * Description:
 *
 * ****************************************
 */
package hw01.solver;

import hw01.game.MasterMindBoard;
import hw01.game.MasterMindUtility;
import hw01.game.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Our custom algorithm
 * This algorithm picks a move by randomly selecting a solution within the
 * set of solutions that are still possible. The algorithm is based
 * on solution discussed in the link mentioned below
 *
 * By doing 10,000 simulations we got an average of 4.64 moves per game with a minimum of 1 and maximum of 8.
 *
 * @see <a href="https://puzzling.stackexchange.com/questions/546/clever-ways-to-solve-mastermind">Clever Ways to Solve Mastermind</a>
 */
public class CustomSolver extends SmartSolver{


    /**
     * Set of all codes that are still possible
     */
    private List<int[]> s;

    /**
     * Constructor
     */
    public CustomSolver() {
        super();
    }


    /**
     * Gets the next move from randomly choosing an element from
     * the set of possible solutions
     * @author Sebastian
     * @author Jonathan
     * @return int array with next move
     */
    @Override
    protected int[] getNextMove() {
        Random rand = new Random();
        int index = rand.nextInt(s.size());
        return s.get(index);

    }

    /**
     * Plays one game
     * @author Sebastian
     * @author Jonnathan
     * @return score represented by an int
     * @throws Exception
     */
    @Override
    protected int play() throws Exception {
        s = new ArrayList<>(allPossibleCodes);

        MasterMindBoard board = new MasterMindBoard(true);

        int[] curGuess = getNextMove();
        Row result = board.guess(curGuess);

        while (!board.checkWin()){

            List<int[]> newS = new ArrayList<>();
            for (int[] possibleSolution:
                    s) {
                Row resultForPossibleSolution = MasterMindUtility.makeGuess(curGuess, possibleSolution);
                if (resultForPossibleSolution.equals(result)){
                    newS.add(possibleSolution);
                }

            }
            s = new ArrayList<>(newS);

            curGuess = getNextMove();

            result = board.guess(curGuess);

        }

        return board.getGuesses();
    }


}

