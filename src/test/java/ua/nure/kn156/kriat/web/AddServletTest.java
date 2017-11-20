package ua.nure.kn156.kriat.web;

import ua.nure.kn156.kriat.User;

import java.text.DateFormat;
import java.util.Date;

public class AddServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    public void testAdd() {
        User user = new User("John", "Dao", new Date());
        User createdUser = new User(user);
        createdUser.setId(1000L);
        mockUserDao.expectAndReturn("create", user, createdUser);

        addRequestParameter("ok", "OK");
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("date", DateFormat.getDateInstance().format(user.getDate()));
        doPost();
    }
}
