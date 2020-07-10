package ru.ifmo.se.commands;


public class ExecuteScriptCommand extends ClassCommand {
    public ExecuteScriptCommand(){
        this.commandName = CommandName.EXECUTE_SCRIPT;    }

    @Override
    public Object execute(Context context) {return new Object();
    }
}
