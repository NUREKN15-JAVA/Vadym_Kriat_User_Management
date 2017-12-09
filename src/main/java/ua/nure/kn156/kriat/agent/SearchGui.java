package ua.nure.kn156.kriat.agent;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.agent.exceptions.SearchException;
import ua.nure.kn156.kriat.gui.ButtonCommand;
import ua.nure.kn156.kriat.gui.UserTableModel;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class SearchGui extends JFrame {

    private SearchAgent agent;

    private JPanel contentPanel;

    private JPanel tablePanel;

    private JTable table;

    public SearchGui(SearchAgent agent) {
        this.agent = agent;
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Searcher");
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getSearchPanel(), BorderLayout.NORTH);
            contentPanel.add(new JScrollPane(getTablePanel()), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JPanel(new BorderLayout());
            tablePanel.add(getTable(), BorderLayout.CENTER);
        }
        return tablePanel;
    }

    private JTable getTable() {
        if (table == null) {
            table = new JTable(new UserTableModel(new ArrayList<>()));
        }
        return table;
    }

    private JPanel getSearchPanel() {
        return new SearchPanel(agent);
    }

    public static void main(String[] args) {
        SearchGui gui = new SearchGui(new SearchAgent());
        gui.setVisible(true);
    }

    class SearchPanel extends JPanel implements ActionListener {
        //        protected JFrame parent;
        SearchAgent agent;

        private JPanel buttonPanel;

        private JPanel fieldPanel;

        private JButton cancelButton;

        private JButton searchButton;

        private JTextField firstNameField;

        private JTextField dateOfBirthField;

        private JTextField lastNameField;


        public SearchPanel(SearchAgent agent) {
            this.agent = agent;
            initialize();
        }

        private void initialize() {
            this.setName("addPanel");
            this.setLayout(new BorderLayout());
            this.add(getFieldPanel(), BorderLayout.NORTH);
            this.add(getButtonPanel(), BorderLayout.SOUTH);
        }

        private JPanel getButtonPanel() {
            if (buttonPanel == null) {
                buttonPanel = new JPanel();
                buttonPanel.add(getSearchButton(), null);
                buttonPanel.add(getCancelButton(), null);
            }
            return buttonPanel;
        }

        private JButton getCancelButton() {
            if (cancelButton == null) {
                cancelButton = new JButton();
                cancelButton.setText(Message.getString("button.cancel"));
                cancelButton.setName("cancelButton");
                cancelButton.setActionCommand(ButtonCommand.CANCEL);
                cancelButton.addActionListener(this);
            }
            return cancelButton;
        }

        private JButton getSearchButton() {
            if (searchButton == null) {
                searchButton = new JButton();
                searchButton.setText("Search");
                searchButton.setName("okButton");
                searchButton.setActionCommand(ButtonCommand.OK);
                searchButton.addActionListener(this);
            }
            return searchButton;
        }

        private JPanel getFieldPanel() {
            if (fieldPanel == null) {
                fieldPanel = new JPanel();
                fieldPanel.setLayout(new GridLayout(2, 3));
                addLabeledField(fieldPanel, "FirstName", getFirstNameField());
                fieldPanel.add(getSearchButton());
                addLabeledField(fieldPanel, "LastName", getLastNameField());
                fieldPanel.add(getCancelButton());
            }
            return fieldPanel;
        }

        private JTextField getLastNameField() {
            if (lastNameField == null) {
                lastNameField = new JTextField();
                lastNameField.setName("lastNameField");
            }
            return lastNameField;
        }

        private void addLabeledField(JPanel panel, String labelText,
                                     JTextField textField) {
            JLabel label = new JLabel(labelText);
            label.setLabelFor(textField);
            panel.add(label);
            panel.add(textField);
        }

        private JTextField getFirstNameField() {
            if (firstNameField == null) {
                firstNameField = new JTextField();
                firstNameField.setName("firstNameField");
            }
            return firstNameField;
        }

        private void doAction(ActionEvent e) throws ParseException {
            if (ButtonCommand.OK.equalsIgnoreCase(e.getActionCommand())) {
                String firstName = getFirstNameField().getText();
                String lastName = getLastNameField().getText();
                try {
                    clearUsers();
                    agent.search(firstName, lastName);
                } catch (SearchException e1) {
                    throw new RuntimeException(e1);
                }
            }
            clearFields();
        }

        public void actionPerformed(ActionEvent e) {
            try {
                doAction(e);
            } catch (ParseException ignored) {
            }
        }

        private void clearFields() {
            getFirstNameField().setText("");
            getLastNameField().setText("");
        }
    }

    public void addUsers(Collection<User> users) {
        System.out.println("addUsers : " + users);
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.addUsers(users);
        this.repaint();
    }

    private void clearUsers() {
        System.out.println("clearUsers : ");
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.clearUsers();
        this.repaint();
    }
}