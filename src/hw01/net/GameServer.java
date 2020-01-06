/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jonathan Basom and Sebastian Ascoli
 * Section: 9 am / 11 am
 * Date: 10/9/2019
 * Time: 4:09 PM
 *
 * Project: csci205_hw
 * Package: hw01
 * Class: GameServer
 *
 * Description:
 *
 * ****************************************
 */
package hw01.net;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to manage socket on the server side
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
public class GameServer {

    /**
     * Ip address
     */
    private InetAddress ip;

    /**
     * port
     */
    private int port;

    /**
     * Server socket
     */
    private ServerSocket serverSocket;

    /**
     * Client socket
     */
    private Socket clientSocket;


    /**
     * Object output stream
     */
    private ObjectOutputStream objOut;

    /**
     * Object input stream
     */
    private ObjectInputStream objIn;


    /**
     * Default port
     */
    public static int DEFAULTPORT = 20000;

    /**
     * Default constructor with default port number
     * @throws IOException
     * @author Sebastian
     * @author Jonnathan
     */
    public GameServer() throws IOException {
        this.ip = InetAddress.getLocalHost();
        this.port = DEFAULTPORT;

        serverSocket = new ServerSocket(port);
    }

    /**
     * Constructor in which you specify port number
     * @param port port number
     * @throws IOException
     * @author Sebastian
     * @author Jonathan
     */
    public GameServer(int port) throws IOException {
        this.ip = InetAddress.getLocalHost();
        this.port = port;

        serverSocket = new ServerSocket(port);
    }


    /**
     * Waits until the client requests to connect to server
     * and then creates a client socket, it creates output and input
     * streams writers and readers
     * @throws IOException
     * @author Sebastian
     */
    public void connectToClient() throws IOException {
        clientSocket = serverSocket.accept();

        this.objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objIn = new ObjectInputStream(clientSocket.getInputStream());

    }


    /**
     * Reads object sent from client
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @author Sebastian
     */
    public Object readObject() throws IOException, ClassNotFoundException {
        return this.objIn.readObject();
    }

    /**
     * Sends object to server
     * @param o
     * @throws IOException
     * @throws ClassNotFoundException
     * @author Jonathan
     * @author Sebastian
     */
    public void sendObject(Object o) throws IOException, ClassNotFoundException {
        this.objOut.writeObject(o);
    }

    /**
     * Closes connection with client
     * @throws IOException
     * @author Jonathan
     * @author Sebastian
     */
    public void closeClientSocket() throws IOException {
        this.clientSocket.close();
    }

    /**
     * Closes server socket
     * @throws IOException
     * @author Jonathan
     * @author Sebastian
     */
    public void closeServerSocket() throws IOException {
        this.serverSocket.close();
    }


    public InetAddress getIp() {
        return ip;
    }

    /**
     * returns ip address of server
     *
     * @see
     * <a href="https://stackoverflow.com/questions/4662215/how-to-extract-a-substring-using-regex">
     * Stack overflow reference on regex
     * </a>
     *
     * @return String ip address
     * @author Sebastian
     */
    public String getFormattedIP(){
        Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
        Matcher matcher = pattern.matcher(ip.toString());
        matcher.find();
        return matcher.group();
    }

    public int getPort() {
        return port;
    }
}
