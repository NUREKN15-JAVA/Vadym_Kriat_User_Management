package ua.nure.kn156.kriat.web;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

//todo i18n
public class BrowseServlet extends HttpServlet {

    private static final String ADD_PARAM = "add";
    private static final String EDIT_PARAM = "edit";
    private static final String DELETE_PARAM = "delete";
    private static final String DETAILS_PARAM = "details";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter(ADD_PARAM) != null) {
            add(req, resp);
        } else if (req.getParameter(EDIT_PARAM) != null) {
            edit(req, resp);
        } else if (req.getParameter(DELETE_PARAM) != null) {
            delete(req, resp);
        } else if (req.getParameter(DETAILS_PARAM) != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Collection<User> users = DAOFactory.getInstance().getUserDAO().findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.length() == 0) {
            req.setAttribute("error", "You must select the user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            User user = DAOFactory.getInstance().getUserDAO().find(new Long(idParam));
            System.out.println(user);
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void details(HttpServletRequest req, HttpServletResponse resp) {

    }
}