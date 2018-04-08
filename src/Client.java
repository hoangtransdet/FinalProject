import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    private static DatagramSocket socket;
    private static InetAddress serverAddress;
    private static InetAddress localAddress;
    private static int serverPort;
    private static int localPort;
    private static ArrayList<Packet> packetList;

    protected Client() {

        try {
            serverAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            localAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        serverPort = 4000;
        localPort = 4001;

        try {
            socket = new DatagramSocket(localPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Client started at " + localAddress.getCanonicalHostName() + " on port " + localPort + ".");
        UDPSocket client = new UDPSocket(socket, serverAddress, serverPort);


        ReadWriteFile r = new ReadWriteFile();
        String test = r.getFile(r.countLineFromFile());
        byte[] testByte = test.getBytes();

        double d = testByte.length;
        double result = d / 500;
        int totalPacket = 0;

        if(d % 500 == 0) {
            String temp = String.valueOf((int) result);
            totalPacket = Integer.parseInt(temp);

        }
        else {
            String temp = String.valueOf((int) result + 1);
            totalPacket = Integer.parseInt(temp);
        }

        packetList = new ArrayList<Packet>();
        for(int i = 0; i < totalPacket; i++) {
            Packet p = new Packet();
            packetList.add(p);
        }

        for(int i = 0; i < packetList.size(); i++) {
            packetList.get(i).setChksum((short) i);
            packetList.get(i).setAckno((i + 1));

            if((500 + 500 * i) > testByte.length) {
                byte[] newData = new byte[(testByte.length - (i * 500))];
                newData = Arrays.copyOfRange(testByte, (i * 500), testByte.length);
                packetList.get(i).setData(newData);
                packetList.get(i).setLen((short) packetList.get(i).getData().length);
            }
            else {
                byte[] newData = new byte[500];
                newData = Arrays.copyOfRange(testByte, (i * 500), (500 + 500 * i));
                packetList.get(i).setData(newData);
                packetList.get(i).setLen((short) packetList.get(i).getData().length);
            }

        }

        byte[] buffer = new byte[66675];

        while(true) {

            Packet c = client.receive();

            if(c == null) {
                c = new Packet();
                c.setAckno(0);
            }

            for(Packet p : packetList) {

                if(p.getAckno() > c.getAckno()) {
                    client.send(serverAddress, serverPort, p);
                }

            }

        }

    }

    public static void runClient() {

    }
}
