/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 4:14 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI
 * Class: OnePlayerGameModel
 *
 * Description:
 * Class for the Model of a One Player Game
 * ****************************************
 */
package hw01.GUI.onePlayerGame;

import hw01.game.MasterMindBoard;
import hw01.game.MasterMindBoardException;
import hw01.game.Row;
import hw01.game.Score;

/**
 * Class for the Model of a One Player Game
 * @author Sebastian
 * @author Jonathan
 */
public class OnePlayerGameModel {

    /** MasterMindBoard for the game */
    private MasterMindBoard board;

    /**
     * No parameter Constructor
     * @author Sebastian
     * @author Jonathan
     */
    public OnePlayerGameModel() {
        board = new MasterMindBoard();
    }

    /**
     * Make a guess on the board
     * @param guesses int[] representing the guesses for the board
     * @return Row object for the result of the guess
     * @author Jonathan
     * @throws MasterMindBoardException
     */
    public Row guess(int[] guesses) throws MasterMindBoardException {
        return board.guess(guesses);
    }

    /**
     * Check the board for a win
     * @author Sebastian
     * @author Jonathan
     * @return boolean - true if win
     */
    public boolean checkWin() {
        return board.checkWin();
    }

    /**
     * Create a new game
     * @author Sebastian
     * @author Jonathan
     */
    public void createNewGame() {
        board = new MasterMindBoard();
    }

    /**
     * Create a new game with a specified secret code
     * @param secretCode int[] representing the secret code
     * @author Sebastian
     * @author Jonathan
     * @throws MasterMindBoardException
     */
    public void createNewGame(int[] secretCode) throws MasterMindBoardException {
        board = new MasterMindBoard(secretCode);
    }

    /**
     * Retrieves the results at the end of a game
     * @author Sebastian
     * @author Jonathan
     * @return Score Object containing the results of the game
     */
    public Score getResults() {
        return new Score(getBoard().getGuesses(), getBoard().getPlayTime(), "You", checkWin());
    }

    /**
     * Creates a new MasterMindBoard with a specified secret code
     * @param secretCode int[] representing the secret code of the game
     * @author Sebastian
     * @author Jonathan
     * @throws MasterMindBoardException
     */
    public void createNewBoard(int[] secretCode) throws MasterMindBoardException {
        board = new MasterMindBoard(secretCode);
    }

    /**
     * Retrieve the current turn of the game
     * @return
     */
    public int getCurrentTurn() {
        return board.getGuesses();
    }

    /**
     * Retrieve the board for the game
     * @return MasterMindBoard
     */
    public MasterMindBoard getBoard() {
        return board;
    }

}
