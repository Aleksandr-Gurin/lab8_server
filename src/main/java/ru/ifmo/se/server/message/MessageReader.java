package ru.ifmo.se.server.message;

import ru.ifmo.se.commands.ErrorCommand;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageReader implements Runnable {
    private DatagramSocket socket;
    private SocketAddress address;
    private Thread messageReaderThread;
    private MessageSystem messageSystem;
    private ExecutorService service = Executors.newFixedThreadPool(2);

    public MessageReader(DatagramSocket socket, MessageSystem messageSystem) {
        this.socket = socket;
        this.messageSystem = messageSystem;
    }

    public void run() {
        this.service.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Object object = null;
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(1000000);
                    this.getBuffer(buffer);
                    ((Buffer) buffer).flip();
                    byte[] petitionBytes = new byte[buffer.remaining()];
                    buffer.get(petitionBytes);
                    if (petitionBytes.length > 0) {
                        try (ByteArrayInputStream bais = new ByteArrayInputStream(petitionBytes)) {
                            try (ObjectInputStream oos = new ObjectInputStream(bais)) {
                                object = oos.readObject();
                            }catch (EOFException var9){
                                object = new ErrorCommand();
                            }
                            catch (IOException var8) {
                                var8.printStackTrace();
                            }
                        }catch (EOFException var11){
                            object = new ErrorCommand();
                        }
                        catch (IOException var7) {
                            var7.printStackTrace();
                        }
                    }
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                System.out.println("reader: " + object);
                messageSystem.putInList(getAddress());
                messageSystem.putInQueues(MessageReader.class, new Message(object, getAddress()));
            }
        });
    }


    private void getBuffer(ByteBuffer buffer) throws IOException {
        byte[] buf = new byte[buffer.remaining()];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        this.socket.receive(packet);
        address = packet.getSocketAddress();
        buffer.put(buf, 0, packet.getLength());
    }

    SocketAddress getAddress() {
        return address;
    }

    public void start() {
        this.messageReaderThread = new Thread(this, "reader_thread");
        this.messageReaderThread.start();
    }

    public void stop() {
        this.messageReaderThread.interrupt();
        service.shutdown();
    }
}
