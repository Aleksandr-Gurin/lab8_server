package ru.ifmo.se.commands;

public class ErrorCommand extends ClassCommand {
    public ErrorCommand(){
        this.commandName = CommandName.ERROR;


    }

    @Override
    public String execute(Context context) {
        return "Команда введена неверно";
    }
}
