package ua.nure.kn156.kriat.db;

import junit.framework.TestCase;

public class TestDAOFactory extends TestCase {

    private DAOFactory dao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = DAOFactory.getInstance();
    }

    public void testGetUserDAO() {
        assertNotNull("DAOFactory instance is null.", dao);
        UserDAO userDAO = dao.getUserDAO();
        assertNotNull("UserDao instance is null", userDAO);
    }
}
