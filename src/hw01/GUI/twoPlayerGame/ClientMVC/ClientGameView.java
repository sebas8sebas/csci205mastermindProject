/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom / Sebastian Ascoli
 * Section: 9am / 11 am
 * Date: 10/30/2019
 * Time: 3:58 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: ClientGameView
 *
 * Description:
 * View for the client side of a two player game
 * ****************************************
 */
package hw01.GUI.twoPlayerGame.ClientMVC;

import hw01.GUI.onePlayerGame.OnePlayerGameView;

/**
 * View for the client side of a two player game
 */
public class ClientGameView extends OnePlayerGameView {

    /**
     * Constructor
     * @param w double width of view
     * @param h double height of view
     * @param model ClientGameMode
     * @param boardRows int for number of rows on board
     * @param numGuesses int for number of guesses per row
     */
    public ClientGameView(double w, double h, ClientGameModel model, int boardRows, int numGuesses) {

        super(w, h, model, boardRows, numGuesses);

        getTitle().setText("Two Player Game");

        getResetBtn().setVisible(false);
        getResetBtn().setText("rematch");
        getTitle().setText("Multiplayer");
    }

}
