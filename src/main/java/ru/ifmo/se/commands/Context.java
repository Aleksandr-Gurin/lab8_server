package ru.ifmo.se.commands;

import ru.ifmo.se.manager.App;
import ru.ifmo.se.manager.Collection;

public interface Context {
    App app();

    Collection collection();
}
