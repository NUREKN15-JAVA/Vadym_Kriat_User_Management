package ua.nure.kn156.kriat.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.MockDAOFactory;

import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    protected Mock mockUserDao;

    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.init(properties);
        setMockUserDao(((MockDAOFactory)DAOFactory.getInstance()).getMockUserDao());
    }

    public void tearDown() throws Exception {
        mockUserDao.verify();
        super.tearDown();
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }
}
