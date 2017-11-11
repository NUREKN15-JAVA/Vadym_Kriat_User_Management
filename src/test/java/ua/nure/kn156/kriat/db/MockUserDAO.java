package ua.nure.kn156.kriat.db;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDAO implements UserDAO {

    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) throws DatabaseException {
        Long currId = ++id;
        user.setId(currId);
        users.put(currId, user);
        return user;
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currId = user.getId();
        User updatedUser = users.get(currId);
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        users.remove(currId);
        users.put(currId, updatedUser);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long currId = user.getId();
        users.remove(currId);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
