package ru.ifmo.se.commands;

import ru.ifmo.se.musicians.MusicBand;

import java.util.List;

public class FilterLessThanNumberOfParticipantsCommand extends ClassCommand {
    public FilterLessThanNumberOfParticipantsCommand(){
        this.commandName = CommandName.FILTER_LESS_THEN_NUMBER_OF_PARTICIPANTS;
    }

    @Override
    public List<MusicBand> execute(Context context) {
        return context.collection().filterLessThanNumberOfParticipants((Integer)this.getArgument());
    }
}