package ru.ifmo.se.server;

import ru.ifmo.se.jdbc.PostgreDB;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);


            Server server = new Server(port, new PostgreDB(args[1], args[2], args[3], args[4], args[5]));

            Runtime.getRuntime().addShutdownHook(new Thread(server::disconnect));

            server.run();
        } catch (SocketException var2) {
            System.exit(-1);
        } catch (IndexOutOfBoundsException var3) {
            System.out.println("Неправильно указаны аргументы(java -jar Server.jar [port] [db_host] [db_port] [db_name] [db_user] [db_password])");
        } catch (InterruptedException | ClassNotFoundException | ExecutionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Неправильно введены данные бд");
        }
    }
}
