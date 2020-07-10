package ru.ifmo.se.commands;

public class MaxByGenreCommand extends ClassCommand {
    public MaxByGenreCommand(){
        this.commandName = CommandName.MAX_BY_GENRE;
    }

    @Override
    public Object execute(Context context) {
        return context.collection().maxByGenre();
    }
}
