package ua.nure.kn156.kriat.db;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.util.Collection;
import java.util.Iterator;

/**
 * Interface for User class which DAO pattern with all CRUD operations.
 *
 * @author Vadym Kriat
 * @see ua.nure.kn156.kriat.db.hsql.HsqldbUserDao
 **/
public interface UserDAO {
    /**
     * Add user info DB users table and get new user's id from DB.
     *
     * @param user all field of user must be not-null except of id field
     * @return copy of user from DB which id auto-created
     * @throws DatabaseException in case of any error with DB
     */
    User create(User user) throws DatabaseException;

    /**
     * Try finds user in DB at the specified ID.
     *
     * @param id the ID of the user to find in DB
     * @return user from DB at the specified ID. In case of user is missing the return null
     * @throws DatabaseException in case of any error with DB
     */
    User find(Long id) throws DatabaseException;

    /**
     * Update the user in DB at the specified ID.
     *
     * @param user the user to be update.
     * @throws DatabaseException in case of any error with DB
     */
    void update(User user) throws DatabaseException;

    /**
     * Delete the user from DB at the specified ID.
     *
     * @param user the user to be deleted
     * @throws DatabaseException in case of any error with DB
     */
    void delete(User user) throws DatabaseException;

    /**
     * Find all users info from DB users table.
     *
     * @return list of all users from DB. In case of users are missing the return empty list
     * @throws DatabaseException in case of any error with DB
     */
    Collection<User> findAll() throws DatabaseException;

    default Collection<User> find(String firstName, String lastName) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Set the connection factory.
     *
     * @param connectionFactory the factory which implemented {@code ConnectionFactory}
     */
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
