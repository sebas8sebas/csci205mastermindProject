/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/31/2019
 * Time: 1:33 AM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: twoPlayerGameUtilities
 *
 * Description:
 * Useful functions to deal with the 2 player game logistics
 * ****************************************
 */
package hw01.GUI.twoPlayerGame;


import hw01.game.GameResults;
import hw01.game.Score;

import java.util.List;

/**
 * Useful functions to deal with the 2 player game logistics
 */
public final class TwoPlayerGameUtilities {

    /**
     * Orders scores
     * @param score1 score player 1
     * @param score2 score player 2
     * @return list of ordered score objects
     */
    public static List<Score> getOrderedListOfScores(Score score1, Score score2){

        GameResults results = new GameResults();
        results.addScore(score1);
        results.addScore(score2);
        results.sortByMoves();
        return results.getScores();
    }

}
