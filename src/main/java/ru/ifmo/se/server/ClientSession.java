package ru.ifmo.se.server;

import ru.ifmo.se.jdbc.PostgreDB;
import ru.ifmo.se.jdbc.UserDAO;
import ru.ifmo.se.manager.App;
import ru.ifmo.se.manager.Collection;
import ru.ifmo.se.server.message.MessageReader;
import ru.ifmo.se.server.message.MessageSystem;
import ru.ifmo.se.server.message.MessageWriter;

import java.net.DatagramSocket;

class ClientSession {
    private DatagramSocket socket;
    private Controller controller;
    private MessageReader messageReader;
    private MessageWriter messageWriter;

    ClientSession(DatagramSocket socket, PostgreDB postgreDB) {
        this.socket = socket;
        MessageSystem messageSystem = new MessageSystem();
        messageReader = new MessageReader(socket, messageSystem);
        messageWriter = new MessageWriter(messageSystem, socket);
        App app = new App();
        Collection collection = new Collection(postgreDB);
        UserDAO dao = new UserDAO(postgreDB.getConnection());
        controller = new Controller(messageSystem, app, collection, dao);
    }


    void run() {
        if (socket.isBound()) {
            messageReader.start();
            controller.start();
            messageWriter.start();
        }
    }

    void disconnect(){
        controller.disconnect();
        messageReader.stop();
        messageWriter.stop();
    }
}
