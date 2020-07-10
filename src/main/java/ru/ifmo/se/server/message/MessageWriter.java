package ru.ifmo.se.server.message;

import com.ctc.wstx.shaded.msv_core.reader.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ForkJoinPool;

public class MessageWriter implements Runnable{
    private DatagramSocket socket;
    private Object objectResponse;
    private ForkJoinPool messageWriterPool;
    private Thread messageWriterThread;
    private MessageSystem messageSystem;

    public MessageWriter(MessageSystem messageSystem, DatagramSocket socket) {
        this.messageSystem = messageSystem;
        this.socket = socket;
        this.messageWriterPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, new Thread.UncaughtExceptionHandler() {
            /**
             * Method invoked when the given thread terminates due to the
             * given uncaught exception.
             * <p>Any exception thrown by this method will be ignored by the
             * Java Virtual Machine.
             *
             * @param t the thread
             * @param e the exception
             */
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        }, false);
    }

    @Override
    public void run() {

            messageWriterPool.execute(() -> {
                while(!Thread.currentThread().isInterrupted()) {
                    try {
                        Message message = (Message) messageSystem.getFromQueues(MessageWriter.class);
                        if (message == null) {
                            return;
                        }
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(baos);
                            try {
                                oos.writeObject(message.getMessage());
                                oos.flush();
                            } catch (IOException var5) {
                                var5.printStackTrace();
                            } finally {
                                baos.close();
                            }

                            // get the byte array of the object
                            ByteBuffer Buf = ByteBuffer.wrap(baos.toByteArray());
                            byte[] buf = new byte[Buf.remaining()];
                            Buf.get(buf);

                            // now send the payload
                            DatagramPacket packet = new DatagramPacket(buf, buf.length, message.getReceiver());
                            socket.send(packet);

                            System.out.println("DONE SENDING");
                        } catch (SocketException var7) {
                            MessageWriter o = new MessageWriter(messageSystem, this.socket);
                            messageSystem.putInQueues(Controller.class, new Message("Файл слишком велик", message.getReceiver()));
                            o.run();
                        } catch (IOException var6) {
                            var6.printStackTrace();
                        } finally {
                            baos.close();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void start() {
        this.messageWriterThread = new Thread(this, "writer_thread");
        this.messageWriterThread.setDaemon(true);
        this.messageWriterThread.start();
    }

    public void stop() {
        this.messageWriterPool.shutdown();
        this.messageWriterThread.interrupt();
    }
}
