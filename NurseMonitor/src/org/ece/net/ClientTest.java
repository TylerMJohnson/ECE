package org.ece.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientTest {

    private String host;
    private int port;
    private SocketChannel socket;

    public ClientTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception {
        socket = SocketChannel.open();
        socket.configureBlocking(true);
        socket.connect(new InetSocketAddress(host, port));
    }

    public void disconnect() throws Exception {
        socket.close();
    }

    public void send(String s) {
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        try {
            socket.write(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Receives server response
     * */
    public String receive() throws IOException{
        ByteBuffer rpHeader= ByteBuffer.allocateDirect(1024);
        socket.read(rpHeader);
        rpHeader.flip();
        byte[] headerBytes = new byte[rpHeader.remaining()];
        rpHeader.get(headerBytes);
        return new String(headerBytes, "UTF-8");
    }
}
