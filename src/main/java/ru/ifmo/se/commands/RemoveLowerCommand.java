package ru.ifmo.se.commands;

import ru.ifmo.se.musicians.MusicBand;

public class RemoveLowerCommand extends ClassCommand {
    private MusicBand musicBand = null;

    public RemoveLowerCommand() {
        this.commandName = CommandName.REMOVE_LOWER;
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
        return context.collection().removeLower(musicBand, getUser());
    }
}
