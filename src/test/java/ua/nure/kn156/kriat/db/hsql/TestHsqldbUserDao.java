package ua.nure.kn156.kriat.db.hsql;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.ConnectionFactory;
import ua.nure.kn156.kriat.db.ConnectionFactoryImpl;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.util.Collection;
import java.util.Date;

public class TestHsqldbUserDao extends DatabaseTestCase {

    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao(connectionFactory);
    }

    public void testCreate() {
        User user = new User();
        user.setFirstName("Dave");
        user.setLastName("Dao");
        user.setDate(new Date());

        assertNull(user.getId());
        User createdUser = null;
        try {
            createdUser = dao.create(user);
            assertNotNull(createdUser);
            assertNotNull(createdUser.getId());
            assertEquals(user.getFullName(), createdUser.getFullName());
            assertEquals(user.getAge(), createdUser.getAge());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFind() {
        final Long id = 1L;
        try {
            User user = dao.find(id);
            assertNotNull("User is null", user);
            assertEquals("Ids does not match", id, user.getId());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFindIdIsMissing() {
        final Long id = 200L;
        try {
            User user = dao.find(id);
            assertNull("User must be null", user);
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testUpdate() {
        final Long id = 2L;
        try {
            User user = dao.find(id);
            assertNotNull("User is missing", user);
            user.setFirstName("Mark");
            dao.update(user);
            User updatedUser = dao.find(id);
            assertNotNull("User has been not updated", updatedUser);
            assertEquals(user.getId(), updatedUser.getId());
            assertEquals(user.getFullName(), updatedUser.getFullName());
            assertEquals(user.getAge(), updatedUser.getAge());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testDelete() {
        User user = null;
        final Long id = 2L;
        try {
            user = dao.find(id);
            assertNotNull("User is missing", user);
            dao.delete(user);
            User deletedUser = dao.find(id);
            assertNull("User has been not deleted", deletedUser);
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFindAll() {
        try {
            Collection<User> collection = dao.findAll();
            assertNotNull("Collection is null", collection);
            assertEquals("Collection size", 2, collection.size());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/usermanagement",
                "sa",
                "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(
                getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
    }
}
