package ua.nure.kn156.kriat.gui;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;

public class EditPanel extends AddPanel {

    private User user;

    public EditPanel(MainFrame mainFrame) {
        super(mainFrame);
        this.setName("editPanel");
    }

    public void setUserData(User user) {
        this.user = new User(user);
        getFirstNameField().setText(user.getFirstName());
        getLastNameField().setText(user.getLastName());
        getDateOfBirthField().setText(DateFormat.getDateInstance().format(user.getDate()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (ButtonCommand.OK.equalsIgnoreCase(actionCommand)) {
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            try {
                user.setDate(DateFormat.getDateInstance().parse(getDateOfBirthField().getText()));
                parent.getDAO().update(user);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Database error.", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        user = null;
        this.setVisible(false);
        parent.showBrowsePanel();
        clearFields();
    }
}
