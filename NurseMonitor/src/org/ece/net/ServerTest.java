package org.ece.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTest{
	
    public String host;
    public int port;
    public Selector selector;
    public ServerSocketChannel server;
    public ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    public SocketChannel currentClient;
    public ConcurrentHashMap<String, SocketChannel> sockets = new ConcurrentHashMap<String, SocketChannel>();
    
    public ServerTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Listens to clients
     *
     */
    public void listen() {

        try {
            selector = Selector.open();
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(this.host, this.port));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server " + host + " start at port " + port + "...");

            while (true) {
                Thread.sleep(10);
                selector.select();
                for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {
                    SelectionKey key = i.next();
                    i.remove();

                    if (key.isConnectable()) {
                        ((SocketChannel)key.channel()).finishConnect();
                    }

                    if (key.isAcceptable()) {
                        // accept connection
                        currentClient = server.accept();
                        currentClient.configureBlocking(false);
                        currentClient.socket().setTcpNoDelay(true);
                        currentClient.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println(currentClient.getRemoteAddress() + " connected...");
                    }

                    if (key.isReadable()) {
                        // ...read messages...
                        currentClient = (SocketChannel) key.channel();

                        buffer.clear();
                        int numBytesRead = -1;

                        try {
                            numBytesRead = currentClient.read(buffer);
                        } catch (Exception e) {
                            System.out.println("Client has been disconnected");
                        }

                        if (numBytesRead == -1) {
                            // No more bytes can be read from the channel
                            currentClient.close();
                        } else {
                            try {
                                byte[] receivedBytes = getReceivedBytes(buffer, numBytesRead);
                                String input = new String(receivedBytes);
                                System.out.println("Client: " + input);
                                
                                if(!Entry.getUI().active){
                                	send(currentClient, "UInotrunnn".getBytes());
                                	return;
                                }
                                if(input.equalsIgnoreCase("privacytrue")){
                                	Entry.getUI().togglePrivacy(true);
                                	send(currentClient, "group1true".getBytes());
                                	
                                } else if(input.equalsIgnoreCase("privacyfalse")){
                                	Entry.getUI().togglePrivacy(false);
                                	send(currentClient, "group1fals".getBytes());
                                	
                                } else if(input.equalsIgnoreCase("sanitizertrue")){
                                	Entry.getUI().toggleSanitizer(true);
                                	send(currentClient, "group2true".getBytes());
                                	
                                } else if(input.equalsIgnoreCase("sanitizerfalse")){
                                	Entry.getUI().toggleSanitizer(false);
                                	send(currentClient, "group2fals".getBytes());
                                	
                                }else if(input.equalsIgnoreCase("glovetrue")){
                                	Entry.getUI().toggleGloves(true);
                                	send(currentClient, "group3true".getBytes());
                                	
                                } else if(input.equalsIgnoreCase("glovefalse")){
                                	Entry.getUI().toggleGloves(false);
                                	send(currentClient, "group3fals".getBytes());
                                	
                                }else if(input.equalsIgnoreCase("identificationtrue")){
                                	Entry.getUI().toggleIdentification(true);
                                	send(currentClient, "group4true".getBytes());
                                	
                                } else if(input.equalsIgnoreCase("identificationfalse")){
                                	Entry.getUI().toggleIdentification(false);
                                	send(currentClient, "group4fals".getBytes());
                                	
                                }
                                
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets receive byte
     * @param buffer
     * @param length
     * @return
     */
    public static byte[] getReceivedBytes(ByteBuffer buffer, int length) {
        buffer.flip();
        byte[] receivedBytes = new byte[length];
        for (int j = 0; j < receivedBytes.length; j++) {
            receivedBytes[j] = buffer.get(j);
        }
        return receivedBytes;
    }

    /**
     * Sends bytes via socket channel
     * @param sc
     * @param bytes
     */
    public boolean send(SocketChannel sc, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        try {
            while (buffer.hasRemaining()) {
                sc.write(buffer);
            }

            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }
}