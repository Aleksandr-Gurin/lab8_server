package ru.ifmo.se.server;

public class ServerData {
    private SessionsManager sessinonsManager;
    public boolean stopFlag;
    //Другие важные поля, которые должны знать все клиенты
    //...

    public ServerData() {
        this.stopFlag = false;
        this.sessinonsManager = new SessionsManager();
    }

    public SessionsManager getSessionsManger() {
        return this.sessinonsManager;
    }
}
