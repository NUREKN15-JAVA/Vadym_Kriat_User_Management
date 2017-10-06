package ua.nure.kn156.kriat;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class TestUser extends TestCase {
    /**Actual for 2017 year*/
    private static final int AGE = 19;
    private User user;
    private Date date;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        user = new User();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1998, Calendar.MARCH, 5);
        date = calendar.getTime();
    }

    public void testGetFullName() {
        user.setFirstName("Vadym");
        user.setLastName("Kriat");
        assertEquals("Kriat, Vadym", user.getFullName());
    }

    public void testGetFullNameThrowsException() {
        user.setFirstName("Vadym");
        try {
            user.getFullName();
            fail("Missing exception");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "First name or last name equal null");
        }
    }

    public void testGetAge() {
        user.setDate(date);
        assertEquals(AGE, user.getAge());
    }


}
