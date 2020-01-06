/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/29/2019
 * Time: 7:56 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: NetworkSetUpController
 *
 * Description:
 * Controller to set up the network between two players
 * ****************************************
 */
package hw01.GUI.twoPlayerGame.NetworkMVC;

import hw01.GUI.twoPlayerGame.ClientMVC.ClientGameController;
import hw01.GUI.twoPlayerGame.ClientMVC.ClientGameModel;
import hw01.GUI.twoPlayerGame.ClientMVC.ClientGameView;
import hw01.GUI.twoPlayerGame.HostMVC.HostGameController;
import hw01.GUI.twoPlayerGame.HostMVC.HostGameModel;
import hw01.GUI.twoPlayerGame.HostMVC.HostGameView;
import hw01.game.MasterMindBoard;
import hw01.game.MasterMindBoardException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller to set up the network between two players
 */
public class NetworkSetUpController {

    /**
     * Thread for dealing with host
     */
    private Thread hostWaitThread;

    /**
     * Stage for the pop up network connection window
     */
    private Stage windowStage;

    /**
     * Main model for host game
     */
    private HostGameModel hostGameModel;

    /**
     * Main model for client game
     */
    private ClientGameModel clientGameModel;

    /**
     * View for the pop up window
     */
    private NetworkSetUpView view;

    /**
     * Scene for the main menu
     */
    private Scene mainMenuScene;

    /**
     * Main Stage for the application
     */
    private Stage primaryStage;

    /**
     * Name of the player
     */
    private String playerName;

    /**
     * Constructor
     * @param primaryStage primary stage
     * @param mainMenuScene main menu scene
     * @param windowStage stage in which this window is
     * @param theView the view
     */
    public NetworkSetUpController(Stage primaryStage, Scene mainMenuScene, Stage windowStage ,NetworkSetUpView theView) {

        hostWaitThread = null;
        hostGameModel = null;
        clientGameModel = null;
        this.windowStage = windowStage;
        this.view = theView;
        this.mainMenuScene = mainMenuScene;
        this.primaryStage = primaryStage;


        createJoinBtnAction();
        createHostBtnAction();
        createJoinOkBtnAction();
        createOnCloseRequestAction();
    }

    /**
     * Action taken when user closes the networking pop up,
     * this makes sure sockets are properly closed to ensure
     * it works properly if user opens the pop up again
     */
    private void createOnCloseRequestAction() {
        windowStage.setOnCloseRequest(event -> {
            if (hostWaitThread != null){
                hostWaitThread.interrupt();
            }

            if (hostGameModel != null){
                hostGameModel.closeSockets();
            }
        });
    }

    /**
     * Sets action to Ok Button (button where user clicks ok after writing servers IP and port)
     */
    private void createJoinOkBtnAction() {
        view.getJoinOkButton().setOnAction(event -> {
            try {
                clientGameModel = new ClientGameModel();
                String ip = view.getIpTextField().getText();
                int port = Integer.parseInt(view.getPortTextField().getText());

                clientGameModel.getGameClient().connectToServer(ip, port);
                windowStage.close();

                //new server game
                int hostNumRows = (int) clientGameModel.getGameClient().readObject();
                int hostNumGuesses = (int) clientGameModel.getGameClient().readObject();
                clientGameModel.setBoardSize(hostNumRows, hostNumGuesses);
                int[] secretCode = (int[]) clientGameModel.getGameClient().readObject();
                clientGameModel.createNewBoard(secretCode);
                clientGameModel.setPlayerName(playerName);
                ClientGameView clientGameView = new ClientGameView(mainMenuScene.getWidth(), mainMenuScene.getHeight(), clientGameModel, clientGameModel.getNumRows(), clientGameModel.getNumGuesses());
                ClientGameController clientGameController = new ClientGameController(primaryStage, mainMenuScene, clientGameView, clientGameModel);
                primaryStage.setScene(new Scene(clientGameView.getRoot()));

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: Make sure your IP and port values are correct");
                alert.show();
            }
            catch (MasterMindBoardException | ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: Issue with game. Try again.");
                alert.show();
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error: Make sure your IP and port values are correct");
                alert.show();
            }
        });
    }


    /**
     * Creates action to Host Btn, it sets required fields visible, asserts
     * user entered a name, it displays the IP and Port the other player
     * will need to connect and it creates a thread in which the server socket waits
     * for a client
     */
    private void createHostBtnAction() {
        view.getHostBtn().setOnAction(event -> {
            if (validatePlayerName()) return;
            view.getHostBtn().setVisible(false);
            view.getJoinBtn().setVisible(false);
            view.setHostModeProperty(true);


            try {
                hostGameModel = new HostGameModel();

                String ip = hostGameModel.getServer().getFormattedIP();
                String port = Integer.toString(hostGameModel.getServer().getPort());
                view.getIpTextField().setText(ip);
                view.getPortTextField().setText(port);

                handleHostThread();

            } catch (IOException e) {
                //This should not happen
                //e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong...");
                alert.show();
                windowStage.close();
            }
        });
    }

    /**
     * Validates that the player has correctly set their name
     * @return Boolean - true if the player has a valid name
     */
    private boolean validatePlayerName() {
        if (view.getNameInputField().getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need a name");
            alert.show();
            return true;
        }
        playerName = view.getNameInputField().getText().trim();
        return false;
    }

    /**
     * Sets action for join button (when user decides to join a game), it
     * sets required view fields to visible and verifies the user entered name
     */
    private void createJoinBtnAction() {
        view.getJoinBtn().setOnAction(event -> {
            if (validatePlayerName()) return;
            view.getHostBtn().setVisible(false);
            view.getJoinBtn().setVisible(false);
            view.setJoinModeProperty(true);
        });
    }

    /**
     * Handles thread were host waits for client to connect
     *
     * Ideas for parts of this method came from:
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
    private void handleHostThread(){

        Runnable connect = () -> {
            boolean success = false;
            try {
                hostGameModel.getServer().connectToClient();
                //System.out.println("connected");
                success = true;

                //Sends secret code to client
                hostGameModel.getServer().sendObject(MasterMindBoard.DEFAULT_MAXIMUM_ATTEMPTS);
                hostGameModel.getServer().sendObject(MasterMindBoard.ROW_SIZE);
                hostGameModel.getServer().sendObject(hostGameModel.getSecretCode());
                hostGameModel.setPlayerName(playerName);

            } catch (IOException | ClassNotFoundException e) {
            }

            boolean successFinal = success;
            Platform.runLater(() ->{
                if(successFinal){
                    windowStage.close();

                    //Sets the initial time of game at this point
                    hostGameModel.getBoard().setInitialTime();

                    //Open new Scene for 2 player Game
                    HostGameView hostView = new HostGameView(mainMenuScene.getWidth(), mainMenuScene.getHeight(), hostGameModel);
                    HostGameController hostGameController = new HostGameController(primaryStage, mainMenuScene, hostView, hostGameModel);
                    primaryStage.setScene(new Scene(hostView.getRoot()));
                }

            });

        };

        hostWaitThread = new Thread(connect);
        hostWaitThread.start();
    }

}

