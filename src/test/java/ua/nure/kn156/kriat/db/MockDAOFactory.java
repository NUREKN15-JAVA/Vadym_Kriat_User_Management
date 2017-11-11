package ua.nure.kn156.kriat.db;

import com.mockobjects.dynamic.Mock;

public class MockDAOFactory extends DAOFactory {

    private Mock mockUserDao;

    public MockDAOFactory() {
        mockUserDao = new Mock(UserDAO.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    public UserDAO getUserDAO() {
        return (UserDAO) mockUserDao.proxy();
    }
}
