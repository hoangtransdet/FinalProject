import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSocket {

    private static DatagramSocket socket;
    private static InetAddress address;
    private static int port;
    private static ReceiverThread receiverThread;

    protected UDPSocket(DatagramSocket s, InetAddress a, int p) {
        socket = s;
        address = a;
        port = p;

        receiverThread = new ReceiverThread(socket, address, port);
        receiverThread.start();
    }

    public static void send(InetAddress address, int port, Packet p) {

        if(p != null) {

            byte[] buffer = p.packetToByte(p);
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, address, port);
            try {
                socket.send(sendPacket);
                System.out.println("==> Packet " + p.getAckno() + " " + p.getData().length + " bytes send to " + address.getCanonicalHostName() + " at port " + port);
                Thread.sleep(4000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static void socketReceive(InetAddress address, int port, Packet p) {
        System.out.println("==> Packet " + p.getAckno() + " " + p.getData().length + " received from " + address.getCanonicalHostName() + " at port " + port);

    }

    public static Packet receive() {
        return new Packet();
    }
}

class ReceiverThread extends Thread {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    protected ReceiverThread(DatagramSocket newSocket, InetAddress newAddress, int newPort) {
        socket = newSocket;
        address = newAddress;
        port = newPort;
    }

    public void run() {

        while(true) {

            byte[] buffer = new byte[66675];
            DatagramPacket receive = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(receive);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            buffer = receive.getData();
            if(buffer.length > 0) {
                Packet p = new Packet();
                p = p.bytesToPacket(buffer);
                UDPSocket.socketReceive(receive.getAddress(), receive.getPort(), p);
            }
        }

    }
}