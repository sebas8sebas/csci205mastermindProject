/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 1:16 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI
 * Class: StartMenuButton
 *
 * Description:
 * Class representing a button on the start screen
 * ****************************************
 */
package hw01.GUI.startMenu;

import javafx.scene.control.Button;

/**
 * Class representing a button on the start screen
 * @author Sebastian
 * @author Jonathan
 */
public class StartMenuButton extends Button {

    /**
     * Creates a button with the specified text as its label.
     *
     * @param text A text string for its label.
     * @author Sebastian
     * @author Jonathan
     */
    public StartMenuButton(String text) {
        super(text);
        setStyle("-fx-background-color: #7ec6e6; -fx-font-size: 2em");
        setPrefSize(StartMenuView.WIDTH / 3, StartMenuView.HEIGHT / 8);

    }
}
