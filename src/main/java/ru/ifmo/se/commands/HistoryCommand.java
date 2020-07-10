package ru.ifmo.se.commands;

public class HistoryCommand extends ClassCommand {
    public HistoryCommand(){
        this.commandName = CommandName.HISTORY;
    }

    @Override
    public Object execute(Context context) {
        return new Object();
    }
}
