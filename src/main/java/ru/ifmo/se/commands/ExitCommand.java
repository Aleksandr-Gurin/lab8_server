package ru.ifmo.se.commands;

public class ExitCommand extends ClassCommand {
    public ExitCommand(){
        this.commandName = CommandName.EXIT;
    }

    @Override
    public Object execute(Context context) {
        return new Object();
    }
}
