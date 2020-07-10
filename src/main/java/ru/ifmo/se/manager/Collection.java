package ru.ifmo.se.manager;

import ru.ifmo.se.jdbc.*;
import ru.ifmo.se.model.User;
import ru.ifmo.se.musicians.MusicBand;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, управляющий коллекцией
 */
public class Collection implements Serializable {
    private volatile Set<MusicBand> musicBands;
    private PostgreDB postgreDB;
    private MusicBandDAO musicBandDao;
    private DAO<Set<MusicBand>, String> collectionDAO;
    private DAO<User, String> userDAO;

    private Date initDate = new Date();

    /**
     * Constructor collection
     *
     * @param postgreDB
     */
    public Collection(PostgreDB postgreDB) {
        this.postgreDB = postgreDB;
        musicBandDao = new MusicBandDAO(postgreDB.getConnection());
        collectionDAO = new CollectionDAO(postgreDB.getConnection());
        userDAO = new UserDAO(postgreDB.getConnection());
        musicBands = collectionDAO.read("");
    }

    /**
     * Добавляет новый элемент в коллекцию
     *
     * @param musicBand добавляемый элемент
     */
    public String add(MusicBand musicBand, User user) {
        musicBands = collectionDAO.read("");
        User loginUser = userDAO.read(user.getLogin());
        if (loginUser.equals(user)){
            musicBandDao.create(musicBand, loginUser.getId());
            musicBand.setUserId(loginUser.getId());
            musicBands.add(musicBand);
            return "Объект добавлен в коллекцию";
        }else {
            return "Нельзя создать объект, потому что указанного пользователя не существует";
        }
    }

    /**
     * Обновляет значения элемента в коллекции
     *
     * @param mb Объект, который содержит значения, кторые должен принять обновляемый элемент
     */
    public String update(MusicBand mb, User user) {
        musicBands = collectionDAO.read("");
        User loginUser = userDAO.read(user.getLogin());
        String result = musicBandDao.getAccess(loginUser.getId(), mb.getId());

        if (result.equals("true")) {
            musicBandDao.update(mb);
            if (musicBandDao.update(mb)) {
                musicBands.stream().filter((o) -> o.getId().equals(mb.getId())).forEachOrdered((o) -> {
                    o.setNumberOfParticipants(mb.getNumberOfParticipants());
                    o.setName(mb.getName());
                    o.setGenre(mb.getGenre());
                    o.setFrontMan(mb.getFrontMan());
                    o.setEstablishmentDate(mb.getEstablishmentDate());
                    o.setCoordinates(mb.getCoordinates());
                });
                result = "Объект успешно обновлен";
            } else {
                 result = "Не удалось обновить объект";
            }
        }
        return result;
    }

    /**
     * Удаляет элемент коллекции
     *
     * @param id id, удаляемого элемента
     */
    public String remove(int id, User user) {
        musicBands = collectionDAO.read("");
        MusicBand musicBand;
        User loginUser = userDAO.read(user.getLogin());
        String result = musicBandDao.getAccess(loginUser.getId(), id);

        musicBand = musicBands.stream().filter((e) -> e.getId() == id).findFirst().orElse(null);
        if (result.equals("true")) {
            if (musicBand != null && musicBandDao.delete(musicBand)) {
                musicBands.remove(musicBand);
                return "Объект удален";
            } else {
                return "id не найден, повторите команду";
            }
        }else return result;
    }

    /**
     * Удаляет элементы коллекции, которые больше данного
     *
     * @param musicBand Объект, с которым сравниваются элементы коллекции
     */
    public String removeGreater(MusicBand musicBand, User user) {
        musicBands = collectionDAO.read("");
        User loginUser = userDAO.read(user.getLogin());

        List<MusicBand> deleted = musicBands.stream().filter((o) -> o.compareTo(musicBand) > 0).collect(Collectors.toList());
        
        deleted.forEach((o) -> {
            if(musicBandDao.getAccess(loginUser.getId(), o.getId()).equals("true")){
                musicBandDao.delete(o);
            }
        });
        musicBands.removeIf((e) -> e.compareTo(musicBand) > 0);
        return ("Созданные вами объекты удалены");
    }


    /**
     * Удаляет элементы коллекции, которые меньше данного
     *
     * @param musicBand Объект, с которым сравниваются элементы коллекции
     */
    public String removeLower(MusicBand musicBand, User user) {
        musicBands = collectionDAO.read("");
        User loginUser = userDAO.read(user.getLogin());

        List<MusicBand> deleted = musicBands.stream().filter((o) -> o.compareTo(musicBand) < 0).collect(Collectors.toList());

        deleted.forEach((o) -> {
            if(musicBandDao.getAccess(loginUser.getId(), o.getId()).equals("true")){
                musicBandDao.delete(o);
            }
        });
        musicBands.removeIf((e) -> e.compareTo(musicBand) < 0);
        return ("Объекты удалены");
    }

    /**
     * Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public List<MusicBand> show() {
        return new ArrayList<MusicBand>(collectionDAO.read("")) ;
    }

    /**
     * Возвращает дату инициализации коллекции
     *
     * @return Date
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * Выводит элементы в обратном порядке
     */
    public String printDescending() {
        musicBands = collectionDAO.read("");
        StringBuilder result = new StringBuilder();
        musicBands.stream().sorted().forEachOrdered((o) -> result.append(o.toString()).append("\n"));
        return (result.toString());
    }

    /**
     * Возвращает коллекцию
     *
     * @return LinkedHashSet
     */
    public Set<MusicBand> getCollection() {
        return musicBands;
    }

    /**
     * Очищает коллекцию
     */
    public String clear(User user) {
        User loginUser = userDAO.read(user.getLogin());
        musicBands = collectionDAO.read("");

        musicBands.forEach((o) -> {
            if(musicBandDao.getAccess(loginUser.getId(), o.getId()).equals("true")){
                musicBandDao.delete(o);
            }
        });
        musicBands = collectionDAO.read("");
        return ("Все созданные вами объекты удалены");
    }

    /**
     * Возвращает элементы, значение поля numberOfParticipants которых меньше заданного
     *
     * @param nop numberOfParticipants
     */
    public List<MusicBand> filterLessThanNumberOfParticipants(int nop) {
        musicBands = collectionDAO.read("");
        ArrayList<MusicBand> result = new ArrayList<>();
        musicBands.stream().filter((o) -> o.getNumberOfParticipants() < nop).forEach(result::add);
        return result;
    }

    /**
     * Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    public String info() {
        musicBands = collectionDAO.read("");
        return ("Тип: " + musicBands.getClass() + "\nДата инициализации: " + getInitDate() + "\nКоличество элементов: " + musicBands.size());
    }

    /**
     * Выводит любой объект из коллекции, значение поля genre которого является максимальным
     */
    public Object maxByGenre() {
        musicBands = collectionDAO.read("");
        MusicBand mb = musicBands.stream().filter((o) -> o.getGenre() != null).max(Comparator.comparing(MusicBand::getGenre)).orElse(null);
        if (mb != null) {
            return (mb);
        } else return "Элемент не найден";
    }
}
