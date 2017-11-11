package ua.nure.kn156.kriat.gui;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowsePanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JScrollPane tablePanel;
    private JPanel buttonsPanel;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JTable userTable;

    public BrowsePanel(MainFrame mainFrame) {
        this.parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName("browsePanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);

    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable");
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel model;
        try {
            model = new UserTableModel(parent.getDAO().findAll());
            userTable.setModel(model);
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), Message.getString("exception.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel getButtonsPanel() {
        if (buttonsPanel == null) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getAddButton(), null);
            buttonsPanel.add(getEditButton(), null);
            buttonsPanel.add(getDeleteButton(), null);
            buttonsPanel.add(getDetailsButton(), null);
        }
        return buttonsPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setName("addButton");
            addButton.setText(Message.getString("button.add"));
            addButton.setActionCommand(ButtonCommand.ADD);
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setName("editButton");
            editButton.setText(Message.getString("button.edit"));
            editButton.setActionCommand(ButtonCommand.EDIT);
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setName("deleteButton");
            deleteButton.setText(Message.getString("button.delete"));
            deleteButton.setActionCommand(ButtonCommand.DELETE);
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setName("detailsButton");
            detailsButton.setText(Message.getString("button.details"));
            detailsButton.setActionCommand(ButtonCommand.DETAILS);
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private boolean rowTableIsSelected() {
        return getUserTable().getSelectedRow() != -1;
    }

    private void deleteUser() {
        int option = JOptionPane.showConfirmDialog(this,
                Message.getString("options.warning.delete"),
                Message.getString("options.warning"), JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            UserTableModel model = (UserTableModel) userTable.getModel();
            try {
                parent.getDAO().delete(model.getSelectedRowData(userTable.getSelectedRow()));
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), Message.getString("exception.database"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            initTable();
        }
    }

    private User getSelectedUser() {
        return ((UserTableModel) userTable.getModel()).getSelectedRowData(userTable.getSelectedRow());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ButtonCommand.ADD:
                this.setVisible(false);
                parent.showAddPanel();
                break;
            case ButtonCommand.EDIT:
                if (rowTableIsSelected()) {
                    this.setVisible(false);
                    parent.showEditPanel(getSelectedUser());
                } else {
                    JOptionPane.showMessageDialog(this,
                            Message.getString("options.warning.selectuser"),
                            Message.getString("options.warning"), JOptionPane.WARNING_MESSAGE);
                }
                break;
            case ButtonCommand.DELETE:
                if (rowTableIsSelected()) {
                    deleteUser();
                } else {
                    JOptionPane.showMessageDialog(this,
                            Message.getString("options.warning.selectuser"),
                            Message.getString("options.warning"), JOptionPane.WARNING_MESSAGE);
                }
                break;
            case ButtonCommand.DETAILS:
                if (rowTableIsSelected()) {
                    this.setVisible(false);
                    parent.showDetailsPanel(getSelectedUser());
                } else {
                    JOptionPane.showMessageDialog(this,
                            Message.getString("options.warning.selectuser"),
                            Message.getString("options.warning"), JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }
}
