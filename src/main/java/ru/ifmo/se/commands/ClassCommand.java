package ru.ifmo.se.commands;

import ru.ifmo.se.model.User;
import ru.ifmo.se.musicians.MusicBand;

import java.io.Serializable;

public abstract class ClassCommand implements Command, Serializable {
    private Object argument;
    protected CommandName commandName = null;
    private User user;

    public ClassCommand() {
    }

    public Object getArgument() {
        return argument;
    }

    public ClassCommand(Object argument) {
        this.argument = argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public void addBandInput(MusicBand musicBand) {
    }

    public MusicBand getBand() {
        return null;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "CommandName=" + commandName.toString() + '}';
    }

    public CommandName getCommandName() {
        return commandName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
