/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Sebastian Ascoli / Jonathan Basom
 * Section: 11 am / 9 am
 * Date: 10/29/2019
 * Time: 10:03 PM
 *
 * Project: csci205_hw
 * Package: hw01.GUI.twoPlayerGame
 * Class: HostGameMain
 *
 * Description:
 * Model for the host side of a two player game
 * ****************************************
 */
package hw01.GUI.twoPlayerGame.HostMVC;

import hw01.GUI.onePlayerGame.OnePlayerGameModel;
import hw01.game.Score;
import hw01.net.GameServer;

import java.io.IOException;

/**
 * Model for the host side of a two player game
 */
public class HostGameModel extends OnePlayerGameModel {

    /** GameServer object to host game */
    private GameServer server;

    /** Determines if connected to client */
    private boolean isConnected;

    /** Name of the host player */
    private String playerName;

    /**
     * Constructor
     * @throws IOException
     */
    public HostGameModel() throws IOException {
        super();
        server = new GameServer();
        playerName = "Player1";
    }

    /**
     * Retrieves the end of game results for the host player
     * @return Score object containing the results
     */
    @Override
    public Score getResults() {
        return new Score(getBoard().getGuesses(), getBoard().getPlayTime(), playerName, checkWin());
    }

    /**
     * Retrieves the secret code for the host's game
     * @return int[] for the secret code
     */
    public int[] getSecretCode() {
        return getBoard().getSecretCode();
    }

    /**
     * Retrieves the GameServer object of the host
     * @return GameServer
     */
    public GameServer getServer() {
        return server;
    }

    /**
     * Retrieves the host's name
     * @return String for name of host
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Allows player to reset name
     * @param playerName String for the name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Closes sockets
     */
    public void closeSockets() {
        try {
            getServer().closeServerSocket();
        } catch (IOException e) {
        }
        try {
            getServer().closeClientSocket();
        } catch (Exception e) {
        }
    }

}

