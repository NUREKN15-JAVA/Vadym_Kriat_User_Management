package ua.nure.kn156.kriat.gui;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The {@code UserTableModel} class extends {@code AbstractTableModel}
 * and implement all required methods.
 * This class represent table model for {@code JTable} class.
 * Table model uses as a basis for class {@code User}.
 *
 * @author Vadym Kriat
 */
public class UserTableModel extends AbstractTableModel {
    /**
     * List of users stored in the table
     */
    private List<User> users;
    /**
     * Array of names for each table columns
     */
    private static final String[] COLUMN_NAMES = {
            Message.getString("table.column.id"),
            Message.getString("table.column.firstname"),
            Message.getString("table.column.lastname")};
    /**
     * Array of classes stored in the table for each table columns
     */
    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};

    /**
     * Constructor a table model
     *
     * @param users list users stored in the table
     */
    public UserTableModel(Collection<User> users) {
        this.users = new ArrayList<>(users);
    }

    /**
     * Define count of users in the table
     *
     * @return count of rows
     */
    @Override
    public int getRowCount() {
        return users.size();
    }

    /**
     * Return the user of the selected
     *
     * @param rowIndex index of selected user
     * @return of the selected user
     */
    public User getSelectedRowData(int rowIndex) {
        return users.get(rowIndex);
    }

    /**
     * Return the value for selected row and column indexes
     *
     * @param rowIndex    row index of the selected user
     * @param columnIndex column index of the selected user
     * @return of the selected object
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
        }
        return null;
    }

    /**
     * Return the column count
     *
     * @return column count
     */
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * Return class for index of column
     *
     * @param columnIndex index of column
     * @return class for index
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    /**
     * Return column name for index of column
     *
     * @param column index of column
     * @return name of column
     */
    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void addUsers(Collection<User> users) {
        this.users.addAll(users);
    }

    public void clearUsers() {
        this.users.clear();
    }
}
