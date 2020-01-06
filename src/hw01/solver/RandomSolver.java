/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom / Sebastian Ascoli
 * Section: 9am / 11 am
 * Date: 10/17/2019
 * Time: 1:51 PM
 *
 * Project: csci205_hw
 * Package: hw01.solver
 * Class: RandomSolver
 *
 * Description:
 *
 * ****************************************
 */
package hw01.solver;

import hw01.game.MasterMindBoard;
import hw01.game.MasterMindBoardException;
import hw01.game.MasterMindUtility;

/**
 * Randomly solves mastermind
 */
public class RandomSolver extends Solver {

    /**
     * Constructor
     * @author Sebastian
     * @author Jonathan
     */
    public RandomSolver() {
        super();
    }


    /**
     * Gets next move by generating a random code
     * @author Sebastian
     * @author Jonathan
     * @return random code
     */
    @Override
    protected int[] getNextMove() {
        return MasterMindUtility.generateRandomSecretCode();
    }


    /**
     * Plays game by doing random guesses
     * @author Sebastian
     * @author Jonathan
     * @return Score
     * @throws MasterMindBoardException
     */
    @Override
    protected int play() throws MasterMindBoardException {
        MasterMindBoard board = new MasterMindBoard(true);
        while (!board.checkWin()) {
            board.guess(this.getNextMove());
        }
        return board.getGuesses();
    }



}
