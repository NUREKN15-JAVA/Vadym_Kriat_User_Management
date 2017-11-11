package ua.nure.kn156.kriat.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.MockDAOFactory;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

public class TestMainFrame extends JFCTestCase {
    private List<User> users;
    private MainFrame mainFrame;
    private Mock mockUserDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        try {
            Properties properties = new Properties();
            properties.setProperty("dao.factory", MockDAOFactory.class.getName());
            DAOFactory.init(properties);
            mockUserDao = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDao();
            User expectedUser = new User(2L, "Vadym", "Kriat", new Date());
            users = Collections.singletonList(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setHelper(new JFCTestHelper());
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            mockUserDao.verify();
            mainFrame.setVisible(false);
            TestHelper.cleanUp(this);
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBrowseControls() {
        find(JPanel.class, "browsePanel");
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(3, table.getColumnCount());
        assertEquals(Message.getString("table.column.id"), table.getColumnName(0));
        assertEquals(Message.getString("table.column.firstname"), table.getColumnName(1));
        assertEquals(Message.getString("table.column.lastname"), table.getColumnName(2));
    }

    public void testAddUser() {
        String firstName = "John";
        String lastName = "Dendy";
        Date date = new Date();

        User user = new User(lastName, firstName, date);
        User expectedUser = new User(1L, lastName, firstName, date);
        mockUserDao.expectAndReturn("create", user, expectedUser);

        List<User> users = new ArrayList<>(this.users);
        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        fillFields(firstName, lastName, date);

        JButton okButton = (JButton) find(JButton.class, "okButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(2, userTable.getRowCount());
    }

    public void testCancelAddUser() {
        mockUserDao.expectAndReturn("findAll", this.users);
        find(JPanel.class, "browsePanel");
        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());

        JButton okButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "addPanel");
        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
        find(JPanel.class, "browsePanel");

        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());
    }

    public void testDeleteUser() {
        User expectedUser = new User(2L, "Vadym", "Kriat", new Date());
        mockUserDao.expect("delete", expectedUser);
        List<User> users = new ArrayList<>(this.users);
        users.remove(this.users.get(0));
        mockUserDao.expectAndReturn("findAll", users);

        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());

        JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
        getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

        JDialog dialog = (JDialog) TestHelper.getShowingDialogs().get(0);
        JButton okButton = (JButton) new ComponentFinder(JButton.class).find(dialog, 0);
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
        assertEquals(0, userTable.getRowCount());
    }

    public void testCancelDeleteUser() {
        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());

        JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
        getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));

        JDialog dialog = (JDialog) TestHelper.getShowingDialogs().get(0);
        JButton cancelButton = (JButton) new ComponentFinder(JButton.class).find(dialog, 1);
        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
        assertEquals(1, userTable.getRowCount());
    }

    public void testEditUser() {
        User user = new User(this.users.get(0));
        User expectedUser = new User(user);
        expectedUser.setLastName(user.getLastName() + "1");

        mockUserDao.expect("update", user);
        List<User> users = new ArrayList<>();
        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());

        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));

        JButton addButton = (JButton) find(JButton.class, "editButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "editPanel");

        JTextField lastNameTextField = (JTextField) find(JTextField.class, "lastNameField");
        getHelper().sendString(new StringEventData(this, lastNameTextField, "1"));

        JButton okButton = (JButton) find(JButton.class, "okButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");
        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());
    }

    public void testCancelEditUser() {
        mockUserDao.expectAndReturn("findAll", this.users);
        find(JPanel.class, "browsePanel");
        JTable userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());

        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));

        JButton editButton = (JButton) find(JButton.class, "editButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

        find(JPanel.class, "editPanel");
        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
        find(JPanel.class, "browsePanel");

        userTable = (JTable) find(JTable.class, "userTable");
        assertEquals(1, userTable.getRowCount());
    }

    public void testDetailsPanel() {
        User expectedUser = this.users.get(0);
        mockUserDao.expectAndReturn("findAll", this.users);

        find(JPanel.class, "browsePanel");

        JTable userTable = (JTable) find(JTable.class, "userTable");
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));

        JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));

        find(JPanel.class, "detailsPanel");

        JLabel firstNameLabel = (JLabel) find(JLabel.class, "firstNameLabel");
        JLabel lastNameLabel = (JLabel) find(JLabel.class, "lastNameLabel");
        JLabel ageLabel = (JLabel) find(JLabel.class, "ageLabel");

        assertEquals(firstNameLabel.getText(), expectedUser.getFirstName());
        assertEquals(lastNameLabel.getText(), expectedUser.getLastName());
        assertEquals(ageLabel.getText(), String.valueOf(expectedUser.getAge()));

        JButton closeButton = (JButton) find(JButton.class, "closeButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, closeButton));

        find(JPanel.class, "browsePanel");
    }

    private void fillFields(String firstName, String lastName, Date date) {
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, DateFormat.getDateInstance().format(date)));
    }

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component " + name + "'", component);
        return component;
    }
}