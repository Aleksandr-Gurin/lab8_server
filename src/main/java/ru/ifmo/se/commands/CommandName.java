package ru.ifmo.se.commands;

import java.io.Serializable;

/**
 * enum комманд
 */
public enum CommandName implements Serializable {
    ADD("add"),
    SHOW("show"),
    CLEAR("clear"),
    EXECUTE_SCRIPT("execute_script"),
    EXIT("exit"),
    FILTER_LESS_THEN_NUMBER_OF_PARTICIPANTS("filter_less_than_number_of_participants"),
    HELP("help"),
    HISTORY("history"),
    INFO("info"),
    MAX_BY_GENRE("max_by_genre"),
    PRINT_DESCENDING("print_descending"),
    REMOVE_BY_ID("remove_by_id"),
    REMOVE_GREATER("remove_greater"),
    REMOVE_LOWER("remove_lower"),
    UPDATE("update"),
    ERROR("error");

    private String command;

    CommandName(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
