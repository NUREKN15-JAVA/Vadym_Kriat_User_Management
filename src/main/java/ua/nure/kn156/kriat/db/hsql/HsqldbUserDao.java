package ua.nure.kn156.kriat.db.hsql;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.ConnectionFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;
import ua.nure.kn156.kriat.db.UserDAO;
import ua.nure.kn156.kriat.db.UserTableInf;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class HsqldbUserDao implements UserDAO {

    private static final String INSERT_QUERY = "INSERT INTO " + UserTableInf.TABLE_NAME + " (" +
            UserTableInf.FIRST_NAME + "," + UserTableInf.LAST_NAME + "," + UserTableInf.DATE_OF_BIRTH
            + ") VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE " + UserTableInf.TABLE_NAME + " SET " +
            UserTableInf.FIRST_NAME + "=?, " + UserTableInf.LAST_NAME + "=?," + UserTableInf.DATE_OF_BIRTH +
            "=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM " + UserTableInf.TABLE_NAME + " WHERE id=?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM " + UserTableInf.TABLE_NAME + " WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM " + UserTableInf.TABLE_NAME;
    private static final String SELECT_BY_FL_NAME = "SELECT * FROM " + UserTableInf.TABLE_NAME + " WHERE " +
            UserTableInf.FIRST_NAME + "=? AND " + UserTableInf.LAST_NAME + "=?";

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao() {

    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private void closeResource(AutoCloseable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public User create(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement statement = null;
        CallableStatement callableStatement = null;
        ResultSet keys = null;
        try {
            connection = connectionFactory.createConnection();
            statement = connection.prepareStatement(INSERT_QUERY);
            int i = 1;
            statement.setString(i++, user.getFirstName());
            statement.setString(i++, user.getLastName());
            statement.setDate(i, new Date(user.getDate().getTime()));
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of inserted rows: " + n);
            }
            callableStatement = connection.prepareCall("call IDENTITY()");
            keys = callableStatement.executeQuery();
            User insertedUser = new User(user);
            if (keys.next()) {
                insertedUser.setId(keys.getLong(1));
            }
            return insertedUser;
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResource(keys);
            closeResource(callableStatement);
            closeResource(statement);
            closeResource(connection);
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            connection = connectionFactory.createConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeQuery();
            User user = null;
            if (result.next()) {
                user = new User();
                user.setId(result.getLong(UserTableInf.ID));
                user.setFirstName(result.getString(UserTableInf.FIRST_NAME));
                user.setLastName(result.getString(UserTableInf.LAST_NAME));
                user.setDate(result.getDate(UserTableInf.DATE_OF_BIRTH));
            }
            return user;
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResource(result);
            closeResource(preparedStatement);
            closeResource(connection);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionFactory.createConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            int i = 1;
            preparedStatement.setString(i++, user.getFirstName());
            preparedStatement.setString(i++, user.getLastName());
            preparedStatement.setDate(i++, new Date(user.getDate().getTime()));
            preparedStatement.setLong(i, user.getId());
            int n = preparedStatement.executeUpdate();
            if (n <= 0) {
                throw new DatabaseException("Number of updated rows: " + n);
            }
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResource(preparedStatement);
            closeResource(connection);
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionFactory.createConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, user.getId());
            int n = preparedStatement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of deleted rows: " + n);
            }
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResource(preparedStatement);
            closeResource(connection);
        }
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            Collection<User> list = new ArrayList<>();
            connection = connectionFactory.createConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(SELECT_ALL_QUERY);
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(UserTableInf.ID));
                user.setFirstName(result.getString(UserTableInf.FIRST_NAME));
                user.setLastName(result.getString(UserTableInf.LAST_NAME));
                user.setDate(result.getDate(UserTableInf.DATE_OF_BIRTH));
                list.add(user);
            }
            return list;
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            closeResource(result);
            closeResource(statement);
            closeResource(connection);
        }
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_FL_NAME)) {
            int i = 1;
            ps.setString(i++, firstName);
            ps.setString(i, lastName);
            ResultSet rs = ps.executeQuery();
            Collection<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(UserTableInf.ID));
                user.setFirstName(rs.getString(UserTableInf.FIRST_NAME));
                user.setLastName(rs.getString(UserTableInf.LAST_NAME));
                user.setDate(rs.getDate(UserTableInf.DATE_OF_BIRTH));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
