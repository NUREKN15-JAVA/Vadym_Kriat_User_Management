package ua.nure.kn156.kriat.web;

import org.junit.Before;
import ua.nure.kn156.kriat.User;

import java.text.DateFormat;
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
        Collection usersInSession = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull(usersInSession);
        assertSame(users, usersInSession);
    }

    public void testEdit() {
        User user = new User(1000L, "John", "Dao", new Date());
        mockUserDao.expectAndReturn("find", 1000L, user);
        addRequestParameter("edit", "Edit");
        addRequestParameter("id", "1000");
        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(userInSession);
        assertSame(user, userInSession);

    }

    public void testDetails() {
        User user = new User(1000L, "John", "Dao", new Date());
        mockUserDao.expectAndReturn("find", 1000L, user);

        addRequestParameter("details", "Details");
        addRequestParameter("id", user.getId().toString());
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("date", DateFormat.getDateInstance().format(user.getDate()));
        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(userInSession);
        assertSame(user, userInSession);
    }
}