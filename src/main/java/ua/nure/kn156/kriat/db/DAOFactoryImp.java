package ua.nure.kn156.kriat.db;

public class DAOFactoryImp extends DAOFactory {

    @Override
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
}
