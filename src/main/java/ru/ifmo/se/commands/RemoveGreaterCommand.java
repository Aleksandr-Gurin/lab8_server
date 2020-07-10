package ru.ifmo.se.commands;

import ru.ifmo.se.musicians.MusicBand;

public class RemoveGreaterCommand extends ClassCommand {
    private MusicBand musicBand = null;
    public RemoveGreaterCommand(){
        this.commandName = CommandName.REMOVE_GREATER;
    }

    @Override
    public void addBandInput(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    @Override
    public MusicBand getBand() {
        return super.getBand();
    }

    @Override
    public String execute(Context context) {
        return context.collection().removeGreater(musicBand, getUser());
    }
}
