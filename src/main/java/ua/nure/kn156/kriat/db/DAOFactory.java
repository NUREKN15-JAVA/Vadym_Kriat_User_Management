package ua.nure.kn156.kriat.db;

import java.io.IOException;
import java.util.Properties;

public class DAOFactory {
    private final Properties properties;

    private static final DAOFactory INSTANCE = new DAOFactory();

    private DAOFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("setting.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    public UserDAO getUserDAO() {
        UserDAO userDAO = null;
        try {
            Class clazz = Class.forName(properties.getProperty("ua.nure.kn156.kriat.db.UserDao"));
            userDAO = (UserDAO) clazz.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        userDAO.setConnectionFactory(getConnectionFactory());
        return userDAO;
    }

    private ConnectionFactory getConnectionFactory() {
        String driver = properties.getProperty("connection.driver");
        String url = properties.getProperty("connection.url");
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }
}
