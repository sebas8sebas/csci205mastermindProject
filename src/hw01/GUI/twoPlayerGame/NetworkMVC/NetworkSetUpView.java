/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan
 * Section: 11 am / 9 am
 * Date: 10/29/2019
 * Time: 7:56 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: NetworkSetUpView
 *
 * Description:
 * View of the network set up pop up
 * ****************************************
 */
package hw01.GUI.twoPlayerGame.NetworkMVC;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View of the network set up pop up
 */
public class NetworkSetUpView {

    /** Parent node of the scene */
    private VBox root;

    /** Where user enters name */
    private TextField nameInputField;

    /** Button to choose to host the game */
    private Button hostBtn;

    /** Button to choose to join a game */
    private Button joinBtn;

    /** Where user enters IP address of host player */
    private TextField ipTextField;

    /** Where user enters port number of host player */
    private TextField portTextField;

    /** Label displaying IP address to the host player */
    private Label ipLabel;

    /** Label displaying port number to the host player */
    private Label portLabel;

    /** Ok button for when user is ready to start game */
    private Button joinOkButton;

    /** Determines if user is playing as host */
    private SimpleBooleanProperty hostModeProperty;

    /** Determines if user is playing as client */
    private SimpleBooleanProperty joinModeProperty;

    /**
     * Constructor
     */
    public NetworkSetUpView() {
        initializeModeProperties();
        initializeRoot();
        HBox nameHBox = initializeNameInput();
        HBox btnHbox = initializeHostJoinBtns();
        HBox ipHbox = initializeIPField();
        HBox portHbox = initializePortField();
        Label waitingForClientLabel = initializeWaitLbl();
        initializeOkBtn();

        root.getChildren().addAll(nameHBox, btnHbox, ipHbox, portHbox, waitingForClientLabel, joinOkButton);
    }

    /**
     * Initialize the properties to determine host or client mode
     */
    private void initializeModeProperties() {
        hostModeProperty = new SimpleBooleanProperty(false);
        joinModeProperty = new SimpleBooleanProperty(false);
    }

    /**
     * Initialize the Ok button to allow two player game to begin
     */
    private void initializeOkBtn() {
        joinOkButton = new Button("OK");
        joinOkButton.visibleProperty().bind(joinModeProperty);
    }

    /**
     * Initialize the label to tell player to wait for opponent
     * @return Label
     */
    private Label initializeWaitLbl() {
        Label waitingForClientLabel = new Label("Waiting for other player...");
        waitingForClientLabel.visibleProperty().bind(hostModeProperty);
        return waitingForClientLabel;
    }

    /**
     * Initialize the fields for the host's port number
     * @return HBox with the Port number TextField and Label
     */
    private HBox initializePortField() {
        HBox portHbox = new HBox();
        portHbox.setSpacing(10);
        portTextField = new TextField();
        portTextField.visibleProperty().bind(hostModeProperty.or(joinModeProperty));
        portTextField.disableProperty().bind(hostModeProperty);
        portLabel = new Label("Port:");
        portLabel.setPrefWidth(30);
        portLabel.visibleProperty().bind(hostModeProperty.or(joinModeProperty));
        portHbox.getChildren().addAll(portLabel, portTextField);
        return portHbox;
    }

    /**
     * Initialize the fields for the host's IP address
     * @return HBox containing the IP address TextField and Label
     */
    private HBox initializeIPField() {
        HBox ipHbox = new HBox();
        ipHbox.setSpacing(10);
        ipTextField = new TextField();
        ipTextField.visibleProperty().bind(hostModeProperty.or(joinModeProperty));
        ipTextField.disableProperty().bind(hostModeProperty);
        ipLabel = new Label("IP:");
        ipLabel.setPrefWidth(30);
        ipLabel.visibleProperty().bind(hostModeProperty.or(joinModeProperty));
        ipHbox.getChildren().addAll(ipLabel, ipTextField);
        return ipHbox;
    }

    /**
     * Initialize buttons to decide whether the player wants to host or join a game
     * @return HBox containing the initialized Host/Join buttons
     */
    private HBox initializeHostJoinBtns() {
        hostBtn = new Button("Host");
        joinBtn = new Button("Join");

        HBox btnHbox = new HBox();
        btnHbox.setSpacing(20);
        btnHbox.getChildren().addAll(hostBtn, joinBtn);
        return btnHbox;
    }

    /**
     * Initialize the fields for the player name
     * @return HBox containing the name Label and TextField
     */
    private HBox initializeNameInput() {
        HBox nameHBox = new HBox();

        nameInputField = new TextField();
        nameInputField.disableProperty().bind(hostModeProperty.or(joinModeProperty));
        nameHBox.setSpacing(10);
        nameHBox.getChildren().addAll(new Label("Your name:") ,nameInputField);
        return nameHBox;
    }

    /**
     * Initialize the parent node of the scene graph
     */
    private void initializeRoot() {
        this.root = new VBox();
        root.setPrefWidth(300);
        root.setPrefHeight(200);
        root.setSpacing(15);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);
    }

    public VBox getRoot() {
        return root;
    }

    public TextField getNameInputField() {
        return nameInputField;
    }

    public Button getHostBtn() {
        return hostBtn;
    }

    public Button getJoinBtn() {
        return joinBtn;
    }

    public TextField getIpTextField() {
        return ipTextField;
    }

    public TextField getPortTextField() {
        return portTextField;
    }

    public Label getIpLabel() {
        return ipLabel;
    }

    public Label getPortLabel() {
        return portLabel;
    }

    public Button getJoinOkButton() {
        return joinOkButton;
    }

    public void setHostModeProperty(boolean hostModeProperty) {
        this.hostModeProperty.set(hostModeProperty);
    }

    public void setJoinModeProperty(boolean joinModeProperty) {
        this.joinModeProperty.set(joinModeProperty);
    }

    public boolean isHostModeProperty() {
        return hostModeProperty.get();
    }

    public SimpleBooleanProperty hostModePropertyProperty() {
        return hostModeProperty;
    }

    public boolean isJoinModeProperty() {
        return joinModeProperty.get();
    }

    public SimpleBooleanProperty joinModePropertyProperty() {
        return joinModeProperty;
    }
}
