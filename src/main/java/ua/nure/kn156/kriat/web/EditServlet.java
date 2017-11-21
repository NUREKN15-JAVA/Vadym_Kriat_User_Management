package ua.nure.kn156.kriat.web;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;
import ua.nure.kn156.kriat.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

public class EditServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter(Const.PARAM_OK) != null) {
            doOk(req, resp);
        } else if (req.getParameter(Const.PARAM_CANCEL) != null) {
            doCancel(resp);
        } else {
            showPage(req, resp);
        }
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = new User(getUser(req));
            passToDB(user);
        } catch (ValidationException e) {
            req.setAttribute(Const.KEY_ERR, e.getMessage());
            showPage(req, resp);
            return;
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect("/browse");
    }

    private void doCancel(HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/browse");
    }

    private User getUser(HttpServletRequest req) throws ValidationException {
        User user = new User();

        String idStr = req.getParameter(Const.PARAM_ID);
        String firstName = req.getParameter(Const.PARAM_FIRST_NAME);
        String lastName = req.getParameter(Const.PARAM_LAST_NAME);
        String date = req.getParameter(Const.PARAM_DATE);

        if (firstName == null || firstName.isEmpty()) {
            throw new ValidationException(Message.getString("exception.missing.firstname"));
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new ValidationException(Message.getString("exception.missing.lastname"));
        }
        if (date == null) {
            throw new ValidationException(Message.getString("exception.missing.dateofbirth"));
        }
        if (idStr != null) {
            user.setId(Long.valueOf(idStr));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDate(DateFormat.getDateInstance().parse(date));
        } catch (ParseException e) {
            throw new ValidationException(Message.getString("exception.missing.dateformat"));
        }
        return user;
    }

    protected void passToDB(User user) throws DatabaseException {
        DAOFactory.getInstance().getUserDAO().update(user);
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }
}
