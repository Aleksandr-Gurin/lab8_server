package ru.ifmo.se.commands;

import ru.ifmo.se.musicians.MusicBand;

import java.util.List;

public class ShowCommand extends ClassCommand {
    public ShowCommand(){
        this.commandName = CommandName.SHOW;
    }

    @Override
    public List<MusicBand> execute(Context context) {
        return context.collection().show();
    }
}
