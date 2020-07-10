package ru.ifmo.se.commands;

public class PrintDescendingCommand extends ClassCommand {
    public PrintDescendingCommand(){
        this.commandName = CommandName.PRINT_DESCENDING;
    }

    @Override
    public String execute(Context context) {
        return context.collection().printDescending();
    }
}