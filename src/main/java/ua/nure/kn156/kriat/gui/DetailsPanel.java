package ua.nure.kn156.kriat.gui;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailsPanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel ageLabel;

    public DetailsPanel(MainFrame parent) {
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        this.setName("detailsPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            GridLayout gl = new GridLayout();
            gl.setColumns(2);
            gl.setRows(3);
            fieldPanel.setLayout(gl);
            addLabeledComponent(fieldPanel, Message.getString("label.firstname"), getFirstNameLabel());
            addLabeledComponent(fieldPanel, Message.getString("label.lastname"), getLastNameLabel());
            addLabeledComponent(fieldPanel, Message.getString("label.age"), getAgeLabel());
        }
        return fieldPanel;
    }


    private JLabel getFirstNameLabel() {
        if (firstNameLabel == null) {
            firstNameLabel = new JLabel();
            firstNameLabel.setName("firstNameLabel");
        }
        return firstNameLabel;
    }

    private JLabel getLastNameLabel() {
        if (lastNameLabel == null) {
            lastNameLabel = new JLabel();
            lastNameLabel.setName("lastNameLabel");
        }
        return lastNameLabel;
    }

    private JLabel getAgeLabel() {
        if (ageLabel == null) {
            ageLabel = new JLabel();
            ageLabel.setName("ageLabel");
        }
        return ageLabel;
    }

    private void addLabeledComponent(JPanel panel, String labelText, JLabel component) {
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setLabelFor(component);
        panel.add(label);
        panel.add(component);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getCloseButton(), null);
        }
        return buttonPanel;
    }

    private JButton getCloseButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setName("closeButton");
            okButton.setText(Message.getString("button.close"));
            okButton.setActionCommand(ButtonCommand.CLOSE);
            okButton.addActionListener(this);
        }
        return okButton;
    }

    public void setUserData(User user) {
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        ageLabel.setText(String.valueOf(user.getAge()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ButtonCommand.CLOSE.equalsIgnoreCase(e.getActionCommand())) {
            this.setVisible(false);
            parent.showBrowsePanel();
        }
    }
}
