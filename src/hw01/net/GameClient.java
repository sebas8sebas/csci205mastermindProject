/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom and Sebastian Ascoli
 * Section: 9 am / 11 am
 * Date: 10/10/2019
 * Time: 2:54 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: gameClient
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import java.io.*;
import java.net.Socket;


/**
 * Class to manage the sockets on the client side
 *
 * Ideas for this class and its code came from docs.oracle.com and learning.oreilly.com
 *
 * @see
 * <a href="https://docs.oracle.com/javase/tutorial/networking/index.html.">
 * Oracle's tutorial on networking
 * </a>
 * <a href="https://learning.oreilly.com/library/view/tcpip-sockets-in/9780080568782/ch02.html">
 * TCP/IP Sockets in Java by Michael J. Donahoo, Kenneth L. Calvert
 * </a>
 */
public class GameClient {

    /**Socket object */
    Socket clientSocket;

    /**Object Output Stream */
    ObjectOutputStream objOut;

    /**Object input stream */
    ObjectInputStream objIn;


    /**
     * Connects to server
     * @param ipadress ip adress of the server
     * @param port port
     * @throws IOException if something goes wrong when trying to connect
     * @author Jonathan
     * @author Sebastian
     */
    public void connectToServer(String ipadress, int port) throws IOException {
        clientSocket = new Socket(ipadress, port);

        this.objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objIn = new ObjectInputStream(clientSocket.getInputStream());

    }


    /**
     * Read object sent from host
     * @return object received
     * @throws IOException
     * @throws ClassNotFoundException
     * @author Jonathan
     * @author Sebastian
     */
    public Object readObject() throws IOException, ClassNotFoundException {
        return objIn.readObject();
    }

    /**
     * Send object to host
     * @param o object to be sent
     * @throws IOException
     * @author Jonathan
     * @author Sebastian
     */
    public void sendObject(Object o) throws IOException {
        objOut.writeObject(o);
    }


    /**
     * Close connection
     * @throws IOException
     * @author Sebastian
     */
    public void close() throws IOException {
        clientSocket.close();
    }

}
