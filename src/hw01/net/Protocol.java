package hw01.net;

import java.io.Serializable;

/**
 * Network communication protocol
 * @author Jonathan
 * @author Sebastian
 */
public enum Protocol implements Serializable {

    RECEIVED, READY, QUIT;
}
