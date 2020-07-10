package ru.ifmo.se.jdbc;

public interface DAO<Entity, Key> {
    String create(Entity model, Key key);
    Entity read(Key key1);
    boolean update(Entity model);
    boolean delete(Entity model);
}
