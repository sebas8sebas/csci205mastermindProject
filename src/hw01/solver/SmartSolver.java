/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom / Sebastian Ascoli
 * Section: 11 am / 9 am
 * Date: 10/19/2019
 * Time: 4:16 PM
 *
 * Project: csci205_hw
 * Package: hw01.solver
 * Class: SmartSolver
 *
 * Description:
 *
 * ****************************************
 */
package hw01.solver;

import hw01.game.MasterMindBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for smart solvers
 * This class contains behavior that most
 * smart algorithms would require like generating
 * a list with all possible combinations
 */
public abstract class SmartSolver extends Solver {

    /**
     * All possible codes
     */
    protected List<int[]> allPossibleCodes;

    /**
     * Constructor
     * @author Sebastian
     * @author Jonathan
     */
    public SmartSolver() {
        super();
        generateAllPossibleCodes();
    }



    /**
     * Method to generate all possible codes
     *
     * Idea to generate all possible codes came from Donald Knuth's
     * Five-Guess algorithm found on GitHub
     *
     * @see <a href="https://github.com/nattydredd/Mastermind-Five-Guess-Algorithm/blob/master/README.md">
     *     Mastermind-Five-Guess-Algorithm</a>
     *
     * @author Sebastian
     */
    private void generateAllPossibleCodes(){

        allPossibleCodes = new ArrayList<>();

        int[] curCode = new int[MasterMindBoard.ROW_SIZE];
        generateAllPossibleCodesHelper(curCode, 0);


    }

    /**
     * Helper recursive method for the generateAllPossibleCodes method
     * @author Sebastian
     * @param curCode current code being generated
     * @param index current index
     */
    private void generateAllPossibleCodesHelper(int[] curCode, int index){

        if (index == curCode.length){
            allPossibleCodes.add(curCode.clone());
        }
        else {
            for (int val = MasterMindBoard.MIN_SLOT_VALUE; val <= MasterMindBoard.MAX_SLOT_VALUE; val++) {
                curCode[index] = val;
                generateAllPossibleCodesHelper(curCode, index + 1);
            }
        }

    }


}
