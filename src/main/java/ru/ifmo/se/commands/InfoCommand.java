package ru.ifmo.se.commands;

public class InfoCommand extends ClassCommand {
    public InfoCommand(){
        this.commandName = CommandName.INFO;
    }

    @Override
    public String execute(Context context) {
        return context.collection().info();
    }
}
