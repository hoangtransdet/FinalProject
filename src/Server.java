import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {

    private static DatagramSocket socket;
    private static InetAddress serverAddress;
    private static int serverPort;

    protected Server() {
        try {
            serverAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        serverPort = 4000;

        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Server started at " + serverAddress.getCanonicalHostName() + " on port " + serverPort + ".");
        new UDPSocket(socket, serverAddress, serverPort);
    }
}
