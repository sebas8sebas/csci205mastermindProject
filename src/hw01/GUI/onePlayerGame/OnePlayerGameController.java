/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli and Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/26/2019
 * Time: 4:26 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI
 * Class: OnePlayerGameController
 *
 * Description:
 * Class for the Controller of a One Player Game
 * ****************************************
 */
package hw01.GUI.onePlayerGame;


import hw01.GUI.sceneTemplate.SceneViewTemplateController;
import hw01.game.MasterMindBoard;
import hw01.game.MasterMindBoardException;
import hw01.game.Row;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Class for the Controller of a One Player Game
 * @author Sebastian
 * @author Jonathan
 */
public class OnePlayerGameController extends SceneViewTemplateController {

    /** Current Row of the board */
    private int curRow;

    /** Model for a One Player Game */
    private OnePlayerGameModel model;

    /**
     * Constructor for a OnePlayerGameController
     * @param primaryStage Stage representing the Stage of the application
     * @param prevScene Scene object for the previous scene
     * @param view OnePlayerGameView
     * @param model OnePlayerGameModel
     * @author Sebastian
     * @author Jonathan
     */
    public OnePlayerGameController(Stage primaryStage, Scene prevScene, OnePlayerGameView view, OnePlayerGameModel model) {
        super(primaryStage, prevScene, view);

        this.model = model;

        curRow = 0;

        initializePegClicks(view);
        initializeRestartBtn();
        initializeGuessBtnClicks(view, model);

    }

    /**
     * Initialize the action for when a peg is clicked on the GUI board
     * @author Sebastian
     * @author Jonathan
     * @param view
     */
    private void initializePegClicks(OnePlayerGameView view) {
        //Sound effect when clicking peg
        Media sound = new Media(new File("sound/sound1.wav").toURI().toString());
        MediaPlayer clickSound = new MediaPlayer(sound);

        for (int x = 0; x < MasterMindBoard.ROW_SIZE; x++) {
            for (int y = 0; y < MasterMindBoard.DEFAULT_MAXIMUM_ATTEMPTS; y++) {

                int finalY = y;
                PegSphere curPeg = view.getPegGrid()[y][x];
                curPeg.setOnMouseClicked(event -> {

                    if (curRow == finalY) {
                        clickSound.stop();
                        curPeg.setColor(PegColor.getNextColor(curPeg.getColor()));
                        clickSound.play();
                    }
                });
            }
        }
    }

    /**
     * Initialize the action for when a guess button is clicked
     * @param view OnePlayerGameView
     * @param model OnePlayerGameModel
     * @author Jonathan
     */
    private void initializeGuessBtnClicks(OnePlayerGameView view, OnePlayerGameModel model) {

        // Initialize guess button clicks
        Button[] buttons = view.getButtons().clone();
        for (int i = 0; i < buttons.length; i++) {

            int finalI = i;
            buttons[i].setOnAction(event -> {
                // Get guesses
                int[] guesses = new int[MasterMindBoard.ROW_SIZE];
                PegSphere[] rowOfPegs = view.getPegGrid()[curRow].clone();

                for (int j = 0; j < rowOfPegs.length; j++) {
                    int numericGuess = PegColor.getColorNumber(rowOfPegs[j].getColor());

                    //The method getColorNumber returns 0 if the color is not selected
                    if (numericGuess != 0) {
                        guesses[j] = numericGuess;
                    } else {
                        view.getErrorMsg().setContentText("Invalid number of guesses! Guess must include " + MasterMindBoard.ROW_SIZE + " pegs.");
                        view.getErrorMsg().show();
                        return;
                    }
                }

                // Make guess
                Row result = null;
                try {
                    result = this.model.guess(guesses);
                } catch (MasterMindBoardException e) {
                    return;
                }
                int count = 0;
                // Add correct pegs in the correct position as red result pegs
                for (int j = 0; j < result.getCorrectPegs(); j++) {
                    view.getResultsGrid()[curRow][count].setFill(Color.RED);
                    count++;
                }
                // Add correct pegs in the incorrect position as black result pegs
                for (int j = 0; j < result.getPegsIncorrectPosition(); j++) {
                    view.getResultsGrid()[curRow][count].setFill(Color.BLACK);
                    count++;
                }

                if (checkWin(model)) return;

                updateCurrentRow(buttons, finalI);
            });
        }
    }

    /**
     * Updates the current row on the GUI and shows the guess button for only the new row
     * @param buttons Button[] for the list of guess buttons
     * @param finalI int index of buttons for the current row
     * @author Jonathan
     */
    private void updateCurrentRow(Button[] buttons, int finalI) {
        // Make next button visible if not currently on last button
        if (finalI != buttons.length - 1) {
            buttons[finalI + 1].setVisible(true);
        }
        else {
            finishGame();
        }
        buttons[finalI].setVisible(false);

        // Update current row
        curRow++;
    }

    /**
     * Check if player won
     * @param model OnePlayerGameModel
     * @return boolean true if player has won
     * @author Sebastian
     * @author Jonathan
     */
    private boolean checkWin(OnePlayerGameModel model) {
        // Check win
        if (model.checkWin()) {
            finishGame();
            return true;
        }
        return false;
    }

    /**
     * Create an action for clicking the restart button
     * @author Sebastian
     * @author Jonathan
     */
    private void initializeRestartBtn() {
        // Initialize Restart Button
        getTheView().getResetBtn().setOnAction(event -> {
            clearBoard();
        });
    }

    /**
     * Handles a game once it finishes
     * @author Sebastian
     * @author Jonathan
     */
    public void finishGame() {
        if (model.checkWin()) {
            Media winSoundMedia = new Media(new File("sound/fanfare_x.wav").toURI().toString());
            MediaPlayer winSoundMediaPlayer = new MediaPlayer(winSoundMedia);
            winSoundMediaPlayer.play();
        } else {
            Media winSoundMedia = new Media(new File("sound/whah_whah.wav").toURI().toString());
            MediaPlayer winSoundMediaPlayer = new MediaPlayer(winSoundMedia);
            winSoundMediaPlayer.play();
        }
        getTheView().getButtons()[curRow].setVisible(false);
        curRow = -1;

        showCorrectCode();
        displayResults();
    }

    /**
     * Clears the current board on the GUI
     * @author Sebastian
     * @author Jonathan
     */
    public void clearBoard() {
        //Clears peg spheres
        for (PegSphere[] row:
             getTheView().getPegGrid()) {
            for (PegSphere peg:
                 row) {
                peg.setColor(Color.WHITE);
            }
        }

        //Clears result pegs
        for (Circle[] resultRow:
             getTheView().getResultsGrid()) {
            for (Circle resultPeg: resultRow) {
                resultPeg.setFill(Color.web(OnePlayerGameView.BGCOLOR));
            }
        }

        try {
            getTheView().getButtons()[model.getCurrentTurn()].setVisible(false);
        } catch (IndexOutOfBoundsException e) {
            //If this is the last move this error will occur,
            //the button will be already hidden so there is nothing else to do
        }

        // Clear the results label
        getTheView().getResultsLbl().setVisible(false);
        getTheView().getCorrectAnswerBox().setVisible(false);

        getTheView().getButtons()[0].setVisible(true);
        curRow = 0;
        this.model.createNewGame();

    }

    /**
     * Displays the end of game results
     * @author Jonathan
     */
    public void displayResults() {
        getTheView().getResultsLbl().setText(model.getResults().toString());
        getTheView().getResultsLbl().setVisible(true);
    }

    /**
     * Displays the correct code at the bottom of the window
     * @author Sebastian
     */
    public void showCorrectCode(){

        getTheView().getCorrectAnswerBox().setVisible(true);
        getTheView().getCorrectAnswerBox().getChildren().clear();
        getTheView().getCorrectAnswerBox().setSpacing(5);
        for (int numb:
             getModel().getBoard().getSecretCode()) {
            PegSphere peg = new PegSphere(OnePlayerGameView.GRIDSQUARESIZE / 6);
            peg.setColor(PegColor.colors.get(numb - 1));
            getTheView().getCorrectAnswerBox().getChildren().add(peg);
        }
    }

    /**
     * Retrieves the object's view
     * @return OnePlayerGameView object
     */
    @Override
    public OnePlayerGameView getTheView() {
        return (OnePlayerGameView) super.getTheView();
    }

    /**
     * Retrieves the current row of the GUI board
     * @return int for the current row
     */
    public int getCurRow() {
        return curRow;
    }

    /**
     * Retrieves the object's model
     * @returnn OnePlayerGameModel
     */
    public OnePlayerGameModel getModel() {
        return model;
    }

    /**
     * Sets the GUI's current row
     * @param curRow int for the new current row
     */
    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }


}
