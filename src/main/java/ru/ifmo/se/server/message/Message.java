package ru.ifmo.se.server.message;


import java.net.SocketAddress;
import java.util.Objects;

public class Message {
    private Object message;
    private SocketAddress receiver;

    public Message(Object message, SocketAddress receiver) {
        this.message = message;
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return message.equals(message1.message) &&
                receiver.equals(message1.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, receiver);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Object getMessage() {
        return message;
    }

    public SocketAddress getReceiver() {
        return receiver;
    }
}
