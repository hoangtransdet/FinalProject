//Packet p = new Packet();
//String testString = new String("Hello");
//p.setData(testString.toCharArray());
//System.out.println(p.getData());

//byte[] byteTest = p.charToByte(p.getData());
//System.out.println(byteTest.length + "\n" + byteTest);

//p.byteToChar(byteTest);
//System.out.println(p.getData());

//byte[] byteTest = intToByte(5);
//System.out.println(byteTest.length + "\n" + byteTest);

//int intTest = byteToInt(byteTest);
//System.out.println(intTest);

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class Packet implements Serializable {
    private short chksum;
    private short len;
    private int ackno;
    private byte data[];

    protected Packet() {
        chksum = 0;
        len = 0;
        ackno = 0;
        data = new byte[500];
    }

    public byte[] intToByte(int in) {
        byte[] intByte = new byte[4];

        ByteBuffer buffer = ByteBuffer.wrap(intByte);
        buffer.putInt(in);
        return intByte;
    }

    public int byteToInt(byte[] in) {
        ByteBuffer buffer = ByteBuffer.wrap(in);
        return buffer.getInt();
    }

    public byte[] shortToByte(short in) {
        byte[] shortByte = new byte[2];

        ByteBuffer buffer = ByteBuffer.wrap(shortByte);
        buffer.putShort(in);
        return shortByte;
    }

    public short byteToShort(byte[] in) {
        ByteBuffer buffer = ByteBuffer.wrap(in);
        return buffer.getShort();
    }

    public byte[] packetToByte(Packet p) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outStream = null;
        try {
            outStream = new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.writeObject(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public Packet bytesToPacket(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream inStream = null;
        try {
            inStream = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Packet p = null;
        try {
            p = (Packet) inStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return p;
    }

    public short getChksum() {
        return chksum;
    }

    public void setChksum(short newChksum) {
        chksum = newChksum;
    }

    public short getLen() {
        return len;
    }

    public void setLen(short newLen) {
        len = newLen;
    }

    public int getAckno() {
        return ackno;
    }

    public void setAckno(int newAckno) {
        ackno = newAckno;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] newData) {
        data = new byte[newData.length];
        data = newData;
    }

    public void printPacket() {
        if(getAckno() != 0) {
            System.out.print("Acknowledgement Content:");
        }
        else {
            System.out.print("Packet Content:");
        }
        System.out.print(" Check Sum: " + getChksum());
        System.out.print(" Length: " + getLen());
        System.out.println("  Ackno: " + getAckno());
        System.out.print("  Data: ");
        System.out.println(new String(getData()));
    }


}
