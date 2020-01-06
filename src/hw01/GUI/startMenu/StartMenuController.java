/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 3:03 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI
 * Class: StartMenuController
 *
 * Description:
 * Controller for the Start Menu scene
 * ****************************************
 */
package hw01.GUI.startMenu;

import hw01.GUI.onePlayerGame.OnePlayerGameController;
import hw01.GUI.onePlayerGame.OnePlayerGameModel;
import hw01.GUI.onePlayerGame.OnePlayerGameView;
import hw01.GUI.settings.SettingsController;
import hw01.GUI.settings.SettingsView;
import hw01.GUI.twoPlayerGame.NetworkMVC.NetworkSetUpController;
import hw01.GUI.twoPlayerGame.NetworkMVC.NetworkSetUpView;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller for the Start Menu scene
 */
public class StartMenuController {

    /** View for the start menu */
    private StartMenuView startMenuView;

    /** Stage of the application */
    private final Stage stage;

    /** Stage of any network settings */
    private final Stage networkSettingsWindow;

    /**
     * Constructor
     * @param startMenuView StartMenusView
     * @param primaryStage Stage object for the application's stage
     */
    public StartMenuController(StartMenuView startMenuView, Stage primaryStage) {
        this.startMenuView = startMenuView;
        stage = primaryStage;
        networkSettingsWindow = new Stage();

        MediaPlayer clickSound = initializeClickSound();

        // Button clicks
        handleSingleBtnClick(startMenuView, primaryStage, clickSound);
        handleTwoPlayerBtnClick(clickSound);
        handleSettingsBtnClick(startMenuView, primaryStage, clickSound);
        handleCloseRequest(primaryStage);

    }

    /**
     * Initialize the sound of a button click
     * @return MediaPlayer object with the corresponding sound
     */
    private MediaPlayer initializeClickSound() {
        //click sound
        Media sound = new Media(new File("sound/sound2.wav").toURI().toString());
        return new MediaPlayer(sound);
    }

    /**
     * Handle a request to close the application
     * @param primaryStage Stage object of the application
     */
    private void handleCloseRequest(Stage primaryStage) {
        // Make sure networking settings window (if opened) is also closed if the game is exited
        primaryStage.setOnCloseRequest(event -> {
            if(networkSettingsWindow.isShowing()){
                networkSettingsWindow.close();
            }
        });
    }

    /**
     * Creates action for the Two Player Button click
     * @param clickSound MediaPlayer object with sound of a button click
     */
    private void handleTwoPlayerBtnClick(MediaPlayer clickSound) {
        this.startMenuView.getTwoPlayerBtn().setOnAction(event -> {

            if(!networkSettingsWindow.isShowing()){
                clickSound.stop();
                NetworkSetUpView networkSetUpView = new NetworkSetUpView();
                NetworkSetUpController networkSetUpController = new NetworkSetUpController(stage, stage.getScene(), networkSettingsWindow ,networkSetUpView);
                networkSettingsWindow.setScene( new Scene(networkSetUpView.getRoot()));
                networkSettingsWindow.show();
                clickSound.stop();
            }

        });
    }

    /**
     * Creates action for the Settings Button click
     * @param startMenuView StartMenuView
     * @param primaryStage Stage object for the application
     * @param clickSound MediaPlayer with the sound of a button click
     */
    private void handleSettingsBtnClick(StartMenuView startMenuView, Stage primaryStage, MediaPlayer clickSound) {
        this.startMenuView.getConfigBtn().setOnAction(event -> {
            clickSound.stop();
            SettingsView settingsView = new SettingsView(startMenuView.getRoot().getWidth(), startMenuView.getRoot().getHeight());
            SettingsController settingsController = new SettingsController(stage, stage.getScene(), settingsView);
            clickSound.stop();
            primaryStage.setScene(new Scene(settingsView.getRoot()));

        });
    }

    /**
     * Creates action for the Single Player button click
     * @param startMenuView StartMenuView
     * @param primaryStage Stage objcet for the application
     * @param clickSound MediaPlayer with the sound of a button click
     */
    private void handleSingleBtnClick(StartMenuView startMenuView, Stage primaryStage, MediaPlayer clickSound) {
        //Switch to one player view when button is pressed
        this.startMenuView.getSingleBtn().setOnAction(event -> {

            clickSound.stop();
            OnePlayerGameModel onePlayerGameModel = new OnePlayerGameModel();
            OnePlayerGameView onePlayerGameView = new OnePlayerGameView(startMenuView.getRoot().getWidth(), startMenuView.getRoot().getHeight(), onePlayerGameModel);
            OnePlayerGameController onePlayerGameController = new OnePlayerGameController(stage, stage.getScene(), onePlayerGameView, onePlayerGameModel);
            clickSound.play();

            primaryStage.setScene(new Scene(onePlayerGameView.getRoot()));
        });
    }
}
