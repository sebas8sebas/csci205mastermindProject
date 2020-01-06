/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Names: Jonathan Basom and Sebastian Ascoli
 * Section: 9am / 11 am
 * Date: 10/30/2019
 * Time: 5:45 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: MasterMindBoard
 *
 * Description:
 * Main class for MasterMind game GUI
 * ****************************************
 */

package hw01.GUI;

import hw01.GUI.startMenu.StartMenuController;
import hw01.GUI.startMenu.StartMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * Main class for MasterMind game GUI
 */
public class MastermindGameMain extends Application {

    /** Initial view for the GUI */
    private StartMenuView startView;

    /** Controller for the initial view */
    private StartMenuController startMenuController;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * init method
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        startView = new StartMenuView();
    }

    /**
     * Start the application
     * @param primaryStage Stage object for the application
     * @throws FileNotFoundException
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        startMenuController = new StartMenuController(startView, primaryStage);

        //Set title and image icon
        primaryStage.setTitle("Mastermind");
        primaryStage.getIcons().add(new Image("file:images/icon1.png"));

        // Set min dimensions
        primaryStage.setMinHeight(StartMenuView.HEIGHT);
        primaryStage.setMinWidth(StartMenuView.WIDTH);

        //Start Scene
        Scene scene = new Scene(startView.getRoot());
        primaryStage.setScene(scene);

        //Display scene
        primaryStage.show();
    }
}
