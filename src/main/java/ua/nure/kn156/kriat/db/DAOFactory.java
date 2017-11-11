package ua.nure.kn156.kriat.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DAOFactory {
    protected static Properties properties;
    private static final String DAO_FACTORY = "dao.factory";
    private static DAOFactory instance;

    static {
        properties = new Properties();
        try {
            properties.load(DAOFactory.class.getClassLoader().getResourceAsStream("setting.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected DAOFactory() {
    }

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            try {
                Class factoryClass = Class.forName(properties
                        .getProperty(DAO_FACTORY));
                instance = (DAOFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }

    public abstract UserDAO getUserDAO();


    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }
}
