package ru.ifmo.se.commands;


public class ClearCommand extends ClassCommand {
    public ClearCommand(){
        this.commandName = CommandName.CLEAR;
    }

    @Override
    public String execute(Context context) {
        return context.collection().clear(getUser());
    }
}
