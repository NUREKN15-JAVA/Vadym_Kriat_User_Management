package ua.nure.kn156.kriat.gui;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

public class AddPanel extends JPanel implements ActionListener {

    protected MainFrame parent;
    private JPanel fieldPanel;
    private JPanel buttonPanel;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;

    private JButton okButton;
    private JButton cancelButton;

    private static final String EMPTY_STRING = "";
    private static final Color BG_COLOR = Color.WHITE;

    public AddPanel(MainFrame mainFrame) {
        this.parent = mainFrame;
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
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            GridLayout gl = new GridLayout();
            gl.setColumns(2);
            gl.setRows(3);
            fieldPanel.setLayout(gl);
            addLabeledComponent(fieldPanel, Message.getString("label.firstname"), getFirstNameField());
            addLabeledComponent(fieldPanel, Message.getString("label.lastname"), getLastNameField());
            addLabeledComponent(fieldPanel, Message.getString("label.dateofbirth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private void addLabeledComponent(JPanel panel, String labelText, JTextField component) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setLabelFor(component);
        panel.add(label);
        panel.add(component);
    }

    protected JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    protected JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    protected JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(Message.getString("button.ok"));
            okButton.setName("okButton");
            okButton.setActionCommand(ButtonCommand.OK);
            okButton.addActionListener(this);
        }
        return okButton;
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

    protected void clearFields() {
        getFirstNameField().setText(EMPTY_STRING);
        getLastNameField().setText(EMPTY_STRING);
        getDateOfBirthField().setText(EMPTY_STRING);
        getDateOfBirthField().setBackground(BG_COLOR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ButtonCommand.OK.equalsIgnoreCase(e.getActionCommand())) {
            User user = new User();
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            try {
                user.setDate(DateFormat.getDateInstance().parse(getDateOfBirthField().getText()));
                parent.getDAO().create(user);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(),
                        Message.getString("exception.database"), JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        this.setVisible(false);
        parent.showBrowsePanel();
        clearFields();
    }
}
