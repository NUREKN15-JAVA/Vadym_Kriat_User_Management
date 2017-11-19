package ua.nure.kn156.kriat.web;

import org.junit.Before;
import ua.nure.kn156.kriat.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BrowseServletTest extends MockServletTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(1000L, "John", "Dao", new Date());
        List<User> users = Collections.singletonList(user);
        mockUserDao.expectAndReturn("findAll", users);
        doGet();
        Collection returnedUsers = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull(returnedUsers);
        assertSame(users, returnedUsers);
    }
}