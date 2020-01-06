/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom
 * Section: 9am
 * Date: 10/30/2019
 * Time: 3:13 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: HostGameView
 *
 * Description:
 * View for the host side of a two player game
 * ****************************************
 */
package hw01.GUI.twoPlayerGame.HostMVC;

import hw01.GUI.onePlayerGame.OnePlayerGameView;

/**
 * View for the host side of a two player game
 */
public class HostGameView extends OnePlayerGameView {

    /**
     * Constructor
     * @param w double width of scene
     * @param h double height of scene
     * @param model HostGameModel model of the MVC
     */
    public HostGameView(double w, double h, HostGameModel model) {
        super(w, h, model);
        getResetBtn().setVisible(false);
        getResetBtn().setText("Rematch");
        getTitle().setText("Multiplayer");
    }
}
