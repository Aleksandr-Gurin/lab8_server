package ru.ifmo.se.commands;

import ru.ifmo.se.musicians.MusicBand;

public class AddCommand extends ClassCommand {
    private MusicBand musicBand = null;

    public AddCommand() {
        this.commandName = CommandName.ADD;
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
        return context.collection().add(musicBand, getUser());
    }
}
