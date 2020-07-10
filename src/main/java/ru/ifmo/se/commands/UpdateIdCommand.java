package ru.ifmo.se.commands;

import ru.ifmo.se.musicians.MusicBand;

public class UpdateIdCommand extends ClassCommand {
    private boolean requireInputs = true;
    private MusicBand musicBand = null;
    public UpdateIdCommand(){
        this.commandName = CommandName.UPDATE;
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
        musicBand.setId((Integer) this.getArgument());
        return context.collection().update(musicBand, this.getUser());
    }
}
