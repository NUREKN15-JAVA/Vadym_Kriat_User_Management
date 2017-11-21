package ua.nure.kn156.kriat.web;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.UserDAO;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;
import ua.nure.kn156.kriat.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class BrowseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter(Const.PARAM_ADD) != null) {
            add(req, resp);
        } else if (req.getParameter(Const.PARAM_EDIT) != null) {
            edit(req, resp);
        } else if (req.getParameter(Const.PARAM_DELETE) != null) {
            delete(req, resp);
        } else if (req.getParameter(Const.PARAM_DETAILS) != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Collection<User> users = DAOFactory.getInstance().getUserDAO().findAll();
            req.getSession().setAttribute(Const.KEY_USERS, users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter(Const.PARAM_ID);
        if (idParam == null || idParam.length() == 0) {
            req.setAttribute(Const.KEY_ERR, Message.getString("options.warning.selectuser"));
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            User user = DAOFactory.getInstance().getUserDAO().find(new Long(idParam));
            req.getSession().setAttribute(Const.KEY_USER, user);
        } catch (DatabaseException e) {
            req.setAttribute(Const.KEY_ERR, e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter(Const.PARAM_ID);
        if (idParam == null || idParam.length() == 0) {
            req.setAttribute(Const.KEY_ERR, Message.getString("options.warning.selectuser"));
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            UserDAO dao = DAOFactory.getInstance().getUserDAO();
            User user = dao.find(new Long(idParam));
            dao.delete(user);
        } catch (DatabaseException e) {
            req.setAttribute(Const.KEY_ERR, e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/browse");
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter(Const.PARAM_ID);
        if (idParam == null || idParam.length() == 0) {
            req.setAttribute(Const.KEY_ERR, Message.getString("options.warning.selectuser"));
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DAOFactory.getInstance().getUserDAO().find(new Long(idParam));
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute(Const.KEY_ERR, e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").forward(req, resp);
    }
}