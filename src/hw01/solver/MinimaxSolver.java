/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli and Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/18/2019
 * Time: 11:20 AM
 *
 * Project: csci205_hw
 * Package: hw01.solver
 * Class: MinimaxSolver
 *
 * Description:
 *
 * ****************************************
 */
package hw01.solver;

import hw01.game.MasterMindBoard;
import hw01.game.MasterMindUtility;
import hw01.game.Row;

import java.util.*;

/**
 * This class is a Solver class that uses Donald Knuth's
 * Five-Guess algorithm to solve a game of MasterMind in
 * 5 or less moves.  Ideas for this class came from GitHub
 * at the following links.
 *
 * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm/blob/master/README.md">
 *     Mastermind-Five-Guess-Algorithm</a>
 * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm/blob/master/Five-Guess-Algorithm.cpp">
 *     Mastermind-Five-Guess-Algorithm / Five-Guess-Algorithm.cpp</a>
 */
public class MinimaxSolver extends SmartSolver{

    /**
     * first guess
     */
    private final static int[] FIRSTGUESS= {1, 1, 2, 2};

    /**
     * Set of all codes that are still possible
     */
    private HashSet<int[]> s;

    /**
     * Constructor
     * @author Sebastian
     * @author Jonathan
     */
    public MinimaxSolver() {
        super();
    }

    /**
     * Get next move using Knuth's algorithm
     * @author Sebastian
     * @author Jonathan
     * @return int array with next move
     */
    @Override
    protected int[] getNextMove() {

        if(s.size() == 1){
            return (int[])s.toArray()[0];
        }

        Hashtable<Row, Integer>[] scoreTable = new Hashtable[allPossibleCodes.size()];

        int index = 0;
        for (int[] possibleGuess:
             allPossibleCodes) {

            for (int[] solution:
                 s) {

                Row guess = MasterMindUtility.makeGuess(possibleGuess, solution);

                if (scoreTable[index] == null){
                    scoreTable[index] = new Hashtable<Row, Integer>();
                    scoreTable[index].put(guess, 1);
                }
                else if (scoreTable[index].containsKey(guess)){
                    scoreTable[index].put(guess, scoreTable[index].get(guess) + 1);
                }
                else {
                    scoreTable[index].put(guess, 1);
                }
            }

            index++;
        }

        int minMaxIndex = 0;
        int minMax = Integer.MAX_VALUE;
        index = 0;
        for (Hashtable<Row, Integer> column:
        scoreTable) {
            int max = 0;
            for (int element:
                 column.values()) {
                if (element > max) {
                    max = element;
                }
            }

            if (max < minMax){
                minMax = max;
                minMaxIndex = index;
            } else if (max == minMax && !s.contains(allPossibleCodes.get(minMaxIndex))){
                minMaxIndex = index;

            }

            index++;
        }

        int[] besGuess = allPossibleCodes.get(minMaxIndex);

        return besGuess;
    }

    /**
     * Plays one game using Knuth's algorithm
     * @author Sebastian
     * @author Jonathan
     * @return Score of the game
     * @throws Exception
     */
    @Override
    protected int play() throws Exception {

        s = new HashSet(allPossibleCodes);

        MasterMindBoard board = new MasterMindBoard(true);

        int[] curGuess = FIRSTGUESS;
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
            s = new HashSet<>(newS);

            curGuess = getNextMove();

            result = board.guess(curGuess);
        }

        return board.getGuesses();
    }

}
