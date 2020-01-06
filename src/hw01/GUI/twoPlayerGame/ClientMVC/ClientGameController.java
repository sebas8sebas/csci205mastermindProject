/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom / Sebastian Ascoli
 * Section: 9 am / 11 am
 * Date: 10/30/2019
 * Time: 4:02 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: ClientGameController
 *
 * Description:
 * Controller for the Client side of the game
 * ****************************************
 */
package hw01.GUI.twoPlayerGame.ClientMVC;

import hw01.GUI.onePlayerGame.OnePlayerGameController;
import hw01.GUI.twoPlayerGame.TwoPlayerGameUtilities;
import hw01.game.Score;
import hw01.net.Protocol;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Controller for the client side of the two player game
 */
public class ClientGameController extends OnePlayerGameController {

    /**
     * client game model
     */
    ClientGameModel model;

    /**
     * primary stage
     */
    Stage primaryStage;

    /**
     * Previous scene
     */
    Scene prevScene;

    /**
     * Constructor
     * @param primaryStage primary stage
     * @param prevScene main menu scene
     * @param view view
     * @param model client game model
     */
    public ClientGameController(Stage primaryStage, Scene prevScene, ClientGameView view, ClientGameModel model) {
        super(primaryStage, prevScene, view, model);
        this.model = (ClientGameModel)super.getModel();
        this.primaryStage = primaryStage;
        this.prevScene = prevScene;
        setUpGoBackBtn(view);
        handleRematchBtn(view);

    }

    /**
     * Method that deals with when the game is finished and client has to wait for host
     * Client waits for the host to get its score, then results are displayed, and
     * rematch button is set visible
     *
     * Ideas for parts of this method come from:
     * @see
     * <a href="https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrent-framework/">
     * The JavaFX Concurrent Framework
     * </a>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/javafx/interoperability-tutorial/concurrency.htm">
     * Concurrency in JavaFX
     * </a>
     */
    @Override
    public void finishGame() {

        getTheView().getResultsLbl().setText("Waiting for other player...");
        showCorrectCode(); //displays answer
        getTheView().getResultsLbl().setVisible(true);

        Runnable pleaseDontFreezeGUI = () -> {

            boolean success = false;
            Score hostScore = null;
            try {
                model.getGameClient().sendObject(getModel().getResults());
                hostScore = (Score)model.getGameClient().readObject();
                success = true;
            } catch (Exception e) {
            }

            boolean successFinal = success;
            Score finalHostScore = hostScore;
            Platform.runLater(() ->{
                if(successFinal){
                    displayTwoPlayerResults(finalHostScore);

                    getTheView().getResetBtn().setVisible(true);
                } else {
                    getTheView().getResultsLbl().setText("Other player disconnected\n" + model.getResults().toString());

                }

            });

        };
        Thread thread = new Thread(pleaseDontFreezeGUI);
        thread.start();

        getTheView().getButtons()[getCurRow()].setVisible(false);
        setCurRow(-1);

    }

    /**
     * Displays scores and plays sound (sound is different for winner and loser)
     * @param otherScore the score of the other player
     */
    public void displayTwoPlayerResults(Score otherScore){

        List<Score> scoreList = TwoPlayerGameUtilities.getOrderedListOfScores(getModel().getResults(), otherScore);

        getTheView().getResultsLbl().setText("1. "+scoreList.get(0).toString() + "\n2. " + scoreList.get(1).toString());

        //Plays sounds
        if(scoreList.get(0).equals(getModel().getResults()) && getModel().getBoard().checkWin()){
            Media winSoundMedia = new Media(new File("sound/fanfare_x.wav").toURI().toString());
            MediaPlayer winSoundMediaPlayer = new MediaPlayer(winSoundMedia);
            winSoundMediaPlayer.play();
        } else {
            Media looseSoundMedia = new Media(new File("sound/whah_whah.wav").toURI().toString());
            MediaPlayer looseSoundPlayer = new MediaPlayer(looseSoundMedia);
            looseSoundPlayer.play();
        }

    }

    /**
     * Goes back to menu and closes sockets
     */
    private void goBackToMenu(){
        primaryStage.setScene(prevScene);
        model.closeSockets();
    }

    /**
     * Sets action to go back button to ensure it closes sockets properly
     * @param view
     */
    private void setUpGoBackBtn(ClientGameView view) {
        view.getGoBackBtn().setOnAction(event -> {
            goBackToMenu();
        });
    }


    /**
     * When rematch button is clicked (this button is only visible at the end of the a game),
     * it waits for other players response, if the other
     * player wants to play again, a new game is created
     *
     * Ideas for parts of this method come from:
     * @see
     * <a href="https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrent-framework/">
     * The JavaFX Concurrent Framework
     * </a>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/javafx/interoperability-tutorial/concurrency.htm">
     * Concurrency in JavaFX
     * </a>
     *
     * @param view view
     */
    private void handleRematchBtn(ClientGameView view){

        Runnable rematch = () -> {
            boolean success = true;
            try {
                model.getGameClient().sendObject(Protocol.READY);
                Protocol serverResponse = (Protocol) model.getGameClient().readObject();
                if (serverResponse.equals(Protocol.QUIT)){
                    success = false;
                }
            } catch (Exception e) {
                success = false;
            }
            boolean finalSuccess = success;
            Platform.runLater(()->{
                if (!finalSuccess){
                    getTheView().getResultsLbl().setText("Other player disconnected");
                } else {
                    getTheView().getResultsLbl().setText("");
                    try {
                        int[] secretCode = (int[]) model.getGameClient().readObject();
                        clearBoard();
                        model.createNewGame(secretCode);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Unknown error occurred");
                        alert.show();
                        goBackToMenu();
                    }
                }

            });
        };
        view.getResetBtn().setOnAction(event -> {
            view.getCorrectAnswerBox().setVisible(false); //hides correct answer
            view.getResetBtn().setVisible(false);
            view.getResultsLbl().setText("Waiting...");
            Thread thread = new Thread(rematch);
            thread.start();
        });
    }

}
