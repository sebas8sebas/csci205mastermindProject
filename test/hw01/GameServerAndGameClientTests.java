package hw01;

import hw01.net.GameClient;
import hw01.net.GameServer;
import hw01.net.Protocol;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests to test the GameServer and GameClient objects
 * @author Sebastian
 */
class GameServerAndGameClientTests {

    GameServer server;
    GameClient client;

    /**
     * Set up before the tests
     * @throws InterruptedException
     */
    @BeforeEach
    void setUp() throws InterruptedException {

        Runnable runnable1 = () -> {
            try {
                server = new GameServer();
                server.connectToClient();

            } catch (IOException e) {
                e.printStackTrace();
            }

        };

        Runnable runnable2 = () -> {
            try {
                client = new GameClient();
                client.connectToServer("localhost", GameServer.DEFAULTPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        Thread.sleep(100);
        t2.start();

        t1.join();
        t2.join();

    }

    /**
     * Close connections to avoid getting errors when generating new connections
     * @throws IOException
     */
    @AfterEach
    void cleanUpEach() throws IOException {
        server.closeServerSocket();
        server.closeClientSocket();
        client.close();
    }

    /**
     * Test for send object method
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    void sendObjectTest() throws IOException, ClassNotFoundException {

        server.sendObject(Protocol.READY);

        Protocol p = (Protocol) client.readObject();
        assertEquals(Protocol.READY, p);

    }

    /**
     * Test for read object method
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    void readObjectTest() throws IOException, ClassNotFoundException {

        client.sendObject(Protocol.READY);
        Protocol p = (Protocol) server.readObject();

        assertEquals(Protocol.READY, p);
    }




}