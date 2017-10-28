package ua.nure.kn156.kriat.db;

import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.sql.Connection;

/**
 * Interface for connection factory which load driver
 * and create connection with specified database.
 *
 * @author Vadym Kriat
 * @see ConnectionFactoryImpl
 **/
public interface ConnectionFactory {

    /**
     * Loads the class-driver for specified database.
     * Create connection with specified URL, user-login and user-password.
     *
     * @return the connection which has been created
     * @throws DatabaseException in case of any error with DB
     * @throws RuntimeException  if the class-driver cannot be loaded caused by {@code ClassNotFoundException}
     */
    Connection createConnection() throws DatabaseException;

}
