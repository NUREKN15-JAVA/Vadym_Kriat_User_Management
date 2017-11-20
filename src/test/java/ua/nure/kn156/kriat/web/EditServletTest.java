package ua.nure.kn156.kriat.web;

import ua.nure.kn156.kriat.User;

import java.text.DateFormat;
import java.util.Date;

public class EditServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    public void testUpdate() {
        Date currDate = new Date();
        User user = new User(1000L, "John", "Dao", currDate);
        mockUserDao.expect("update", user);

        addRequestParameter("ok", "OK");
        addRequestParameter("id", user.getId().toString());
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("date", DateFormat.getDateInstance().format(user.getDate()));
        doPost();
    }

    public void testEditEmptyFirstName() {
        addRequestParameter("ok", "OK");
        addRequestParameter("id", "1000");
        addRequestParameter("lastName", "Dao");
        addRequestParameter("date", "11.11.1111");
        doPost();

        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Session don't return the error message", error);
    }

    public void testEditEmptyLastName() {
        addRequestParameter("ok", "OK");
        addRequestParameter("id", "1000");
        addRequestParameter("fistName", "John");
        addRequestParameter("date", "11.11.1111");
        doPost();

        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Session don't return the error message", error);
    }

    public void testEditEmptyDate() {
        addRequestParameter("ok", "OK");
        addRequestParameter("id", "1000");
        addRequestParameter("fistName", "John");
        addRequestParameter("lastName", "Dao");
        doPost();

        String error = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Session don't return the error message", error);
    }
}
