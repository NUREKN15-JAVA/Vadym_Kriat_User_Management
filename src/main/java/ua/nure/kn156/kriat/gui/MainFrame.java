package ua.nure.kn156.kriat.gui;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.UserDAO;
import ua.nure.kn156.kriat.util.Message;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MainFrame extends JFrame {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 400;

    private JPanel contentPanel;
    private BrowsePanel browsePanel;
    private AddPanel addPanel;
    private EditPanel editPanel;
    private DetailsPanel detailsPanel;

    private UserDAO dao;

    static {
        UIManager.put("OptionPane.cancelButtonText", Message.getString("optionpane.cancel"));
        UIManager.put("OptionPane.noButtonText", Message.getString("optionpane.no"));
        UIManager.put("OptionPane.okButtonText", Message.getString("optionpane.ok"));
        UIManager.put("OptionPane.yesButtonText", Message.getString("optionpane.yes"));
    }

    public MainFrame() {
        super();
        dao = DAOFactory.getInstance().getUserDAO();
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Message.getString("title"));
        this.setContentPane(getContentPanel());
        JOptionPane.setDefaultLocale(Locale.getDefault());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private Container getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    public BrowsePanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        browsePanel.initTable();
        return browsePanel;
    }

    private AddPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private EditPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }

    private DetailsPanel getDetailsPanel() {
        if (detailsPanel == null) {
            detailsPanel = new DetailsPanel(this);
        }
        return detailsPanel;
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showEditPanel(User user) {
        EditPanel editPanel = getEditPanel();
        editPanel.setUserData(user);
        showPanel(editPanel);
    }

    public void showDetailsPanel(User user) {
        DetailsPanel detailsPanel = getDetailsPanel();
        detailsPanel.setUserData(user);
        showPanel(detailsPanel);
    }

    public UserDAO getDAO() {
        return dao;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
