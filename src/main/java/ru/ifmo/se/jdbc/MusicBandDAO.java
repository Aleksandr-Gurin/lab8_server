package ru.ifmo.se.jdbc;

import ru.ifmo.se.musicians.*;

import java.sql.*;
import java.time.ZoneId;

public class MusicBandDAO implements DAO<MusicBand, Integer> {
    private Connection connection;

    public MusicBandDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public String create(final MusicBand model, final Integer userId) {
        String result = "";

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, model.getName());
            statement.setLong(2, model.getCoordinates().getX());
            statement.setDouble(3, model.getCoordinates().getY());
            statement.setDate(4, Date.valueOf(model.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setInt(5, model.getNumberOfParticipants());
            if (model.getEstablishmentDate() == null){
                statement.setDate(6, null);
            }else {
                statement.setDate(6, Date.valueOf(model.getEstablishmentDate()));
            }
            if (model.getGenre() == null){
                statement.setString(7, "");
            }else {
                statement.setString(7, model.getGenre().toString());
            }
            statement.setString(8, model.getFrontMan().getName());
            statement.setDouble(9, model.getFrontMan().getHeight());
            if (model.getFrontMan().getEyeColor() == null){
                statement.setString(10, "");
            }else {
                statement.setString(10, model.getFrontMan().getEyeColor().toString());
            }
            if (model.getFrontMan().getHairColor() == null){
                statement.setString(11, "");
            }else {
                statement.setString(11, model.getFrontMan().getHairColor().toString());
            }
            if (model.getFrontMan().getNationality() == null){
                statement.setString(12, "");
            }else {
                statement.setString(12, model.getFrontMan().getNationality().toString());
            }
            statement.setInt(13, userId);
            final ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                model.setId(resultSet.getInt("id"));
                result = "Объект добавлен в коллекцию";
            }else {
                result = "Объект не сохранен, повторите попытку";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public MusicBand read(Integer id) {
        MusicBand musicBand = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                musicBand = new MusicBand();
                musicBand.setId(rs.getInt("id"));
                musicBand.setName(rs.getString("name"));
                musicBand.setCoordinates(new Coordinates(rs.getLong("coordinates_x"),
                        rs.getDouble("coordinates_y")));
                musicBand.setNumberOfParticipants(rs.getInt("numberofparticipants"));
                if (rs.getDate("establishmentdate") == null){
                    musicBand.setEstablishmentDate(null);
                }else {
                    musicBand.setEstablishmentDate(rs.getDate("establishmentdate").toLocalDate());
                }
                if (rs.getString("genre").equals("")){
                    musicBand.setGenre(null);
                }else {
                    musicBand.setGenre(MusicGenre.valueOf(rs.getString("genre")));
                }
                Color color1;
                Color color2;
                Country country;
                if (rs.getString("person_eyecolor").equals("")){
                    color1 = null;
                }else {
                    color1 = Color.valueOf(rs.getString("person_eyecolor"));
                }
                if (rs.getString("person_haircolor").equals("")){
                    color2 = null;
                }else {
                    color2 = Color.valueOf(rs.getString("person_haircolor"));
                }
                if (rs.getString("person_nationality").equals("")){
                    country = null;
                }else {
                    country = Country.valueOf(rs.getString("person_nationality"));
                }
                musicBand.setFrontMan(new Person(rs.getString("person_name"),
                        rs.getDouble("person_height"),
                        color1,
                        color2,
                        country));
                musicBand.setUserId(rs.getInt("userid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musicBand;
    }


    @Override
    public boolean update(MusicBand model) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, model.getName());
            statement.setLong(2, model.getCoordinates().getX());
            statement.setDouble(3, model.getCoordinates().getY());
            statement.setDate(4, Date.valueOf(model.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            statement.setInt(5, model.getNumberOfParticipants());
            if (model.getEstablishmentDate() != null) {
                statement.setDate(6, Date.valueOf(model.getEstablishmentDate()));
            } else {
                statement.setDate(6, null);
            }
            if (model.getGenre() == null){
                statement.setString(7, "");
            }else {
                statement.setString(7, model.getGenre().toString());
            }
            statement.setString(8, model.getFrontMan().getName());
            statement.setDouble(9, model.getFrontMan().getHeight());
            if (model.getFrontMan().getEyeColor() == null){
                statement.setString(10, "");
            }else {
                statement.setString(10, model.getFrontMan().getEyeColor().toString());
            }
            if (model.getFrontMan().getHairColor() == null){
                statement.setString(11, "");
            }else {
                statement.setString(11, model.getFrontMan().getHairColor().toString());
            }
            if (model.getFrontMan().getNationality() == null){
                statement.setString(12, "");
            }else {
                statement.setString(12, model.getFrontMan().getNationality().toString());
            }
            statement.setInt(13, model.getId());
            final ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(MusicBand model) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setInt(1,model.getId());
            if (statement.executeQuery().next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAccess(Integer user_id, Integer id){
        String result = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLUser.SEARCH.QUERY)){
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,user_id);
            if (read(id) != null){
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    result = "true";
                }else result = "Нет доступа к этому объекту";
            }else result = "Объекта с таким id не существует";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    enum SQLUser {
        GET("SELECT * FROM music_bands WHERE id = (?)"),
        INSERT("INSERT INTO music_bands VALUES (DEFAULT, (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?)) RETURNING id"),
        DELETE("DELETE FROM music_bands WHERE id = (?) RETURNING id"),
        UPDATE("update music_bands set name = (?), coordinates_x = (?), coordinates_y = (?), creationdate = (?)," +
                " numberofparticipants = (?), establishmentdate = (?), genre = (?), person_name = (?), person_height = (?)," +
                " person_eyecolor = (?), person_haircolor = (?), person_nationality = (?) where id = (?) RETURNING id"),
        SEARCH("SELECT * FROM music_bands WHERE id = (?) AND userid = (?)");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
