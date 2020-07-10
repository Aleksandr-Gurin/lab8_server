package ru.ifmo.se.server;

import ru.ifmo.se.jdbc.PostgreDB;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;

public class Server {
    private ServerData serverData;
    private DatagramSocket socket;
    private ClientSession clientSession;

    Server(int port, PostgreDB postgreDB) throws SocketException, ClassNotFoundException {
        this.serverData = new ServerData();
        this.socket = new DatagramSocket(new InetSocketAddress(port));
        clientSession = new ClientSession(socket, postgreDB);
    }

    void run() throws ExecutionException, InterruptedException {
        //Создается клиентская сессия
        this.serverData.getSessionsManger().addSession(clientSession);

        //Запуск логики работы с клиентом

        clientSession.run();
    }

    void disconnect() {
        clientSession.disconnect();
        socket.close();
    }
}
