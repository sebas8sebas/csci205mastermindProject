/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom and Sebastian Ascoli
 * Section: 9 am / 11 am
 * Date: 10/10/2019
 * Time: 2:48 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: TwoPlayerGameClientSide
 *
 * Description:
 *
 * ****************************************
 */
package hw01.game;

import hw01.net.Protocol;
import hw01.net.GameClient;

import java.net.SocketException;
import java.util.Scanner;

/**
 * Class to manage game on client side
 */
public class TwoPlayerGameClientSide {

    /**
     * Name of player
     */
    private String clientName;

    /**
     * Game client object to manage networking
     */
    private GameClient gameClient;

    /**
     * Mastermind board object
     */
    private MasterMindBoard board;

    /**
     * Constructor
     * @param playername name of the player
     * @author Jonathan
     * @author Sebastian
     */
    public TwoPlayerGameClientSide(String playername) {
        this.clientName = playername;
    }

    /**
     * Play game in command line interface
     * @throws Exception
     * @author Jonathan
     * @author Sebastian
     */
    public void playCommandLine() throws Exception {
        Scanner in = new Scanner(System.in);
        this.gameClient = createGameClient(in);

        System.out.println("Successfully connected to server");

        boolean play = true;

        try {
            while (play) {
                //Receive secret code and confirm reception
                int[] code = (int[])gameClient.readObject();
                gameClient.sendObject(Protocol.RECEIVED);


                board = new MasterMindBoard(code);
                board.playCommandLine();

                Score score = new Score(board.getGuesses(), board.getPlayTime(), this.clientName, board.checkWin());

                gameClient.sendObject(score);
                System.out.println("Waiting for the server...");
                Protocol response = (Protocol) gameClient.readObject();

                GameResults scores = (GameResults) gameClient.readObject();

                System.out.println("\nResults:");
                System.out.println(scores);

                // See if Client wants to play again
                System.out.println("Do you want to rematch your opponent? [yes/no]");
                String answer = MasterMindUtility.getValidInput(in, new String[]{"yes", "no"});
                // If Client does not want to play again, quit
                if (answer.equalsIgnoreCase("no")) {
                    play = false;
                    Protocol quit = Protocol.QUIT;
                    gameClient.sendObject(quit);
                }
                // Otherwise, see if host wants to play again
                else {
                    Protocol again = Protocol.READY;
                    gameClient.sendObject(again);
                    System.out.println("Waiting for host...");
                    // If host does not want to play again, quit
                    if (!((Protocol) gameClient.readObject()).equals(Protocol.READY)) {
                        System.out.println("Opponent has disconnected");
                        play = false;
                    }
                    // Otherwise, both want to play again
                }
            }
        } catch (SocketException e) {
            System.out.println("Opponent has disconnected");
        }

        gameClient.close();
    }


    /**
     * Repeatedly prompts user with ip and port until it gets proper credentials
     * to connect to host
     * @param in scanner
     * @return GameClient object successfully connected to port
     * @author Sebastian
     */
    private GameClient createGameClient (Scanner in){


        while(true){

            try {

                System.out.print("Please enter the host's ip address: ");
                String ipadress = in.nextLine();
                System.out.print("Please enter the host's port number: ");
                int portNumber = MasterMindUtility.getIntegerPositiveInput(in);
                GameClient client = new GameClient();
                client.connectToServer(ipadress, portNumber);
                return client;

            } catch (Exception e) {
                System.out.println("Error: Please enter valid IP and port number");
            }

        }

    }
}
