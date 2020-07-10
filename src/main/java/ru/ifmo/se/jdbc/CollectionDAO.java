package ru.ifmo.se.jdbc;

import ru.ifmo.se.musicians.MusicBand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CollectionDAO implements DAO<Set<MusicBand>, String> {
    private Connection connection;
    private DAO<MusicBand, Integer> musicBandDao;

    public CollectionDAO(Connection connection) {
        this.connection = connection;
        musicBandDao = new MusicBandDAO(connection);
    }


    @Override
    public String create(final Set linkedHashSet, final String key) {
        return "";
    }

    @Override
    public Set<MusicBand> read(final String key) {
        LinkedHashSet<MusicBand> musicBands = new LinkedHashSet<>();
        try(PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                musicBands.add((MusicBand) musicBandDao.read(resultSet.getInt("id")));
            }
        } catch (SQLException var1){
            var1.printStackTrace();
        }
        return Collections.synchronizedSet(musicBands);
    }

    @Override
    public boolean update(final Set linkedHashSet) {
        return false;
    }

    @Override
    public boolean delete(final Set linkedHashSet) {
        return false;
    }

    enum SQLUser {
        GET("SELECT id FROM music_bands"),
        INSERT("INSERT INTO music_bands (id, login, password) VALUES (DEFAULT, (?), (?)) RETURNING id"),
        DELETE("DELETE FROM music_bands WHERE id = (?) AND login = (?) AND password = (?) RETURNING id"),
        UPDATE("UPDATE music_bands SET password = (?) WHERE id = (?) RETURNING id");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
