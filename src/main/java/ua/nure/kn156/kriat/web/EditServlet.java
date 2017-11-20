package ua.nure.kn156.kriat.web;

import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

//todo i18n
public class EditServlet extends HttpServlet {

    private static final String OK_PARAM = "ok";
    private static final String CANCEL_PARAM = "cancel";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter(OK_PARAM) != null) {
            doOk(req, resp);
        } else if (req.getParameter(CANCEL_PARAM) != null) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = new User(getUser(req));
            DAOFactory.getInstance().getUserDAO().update(user);
            req.getRequestDispatcher("/browse").forward(req, resp);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            showPage(req, resp);
            return;
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    private void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    private User getUser(HttpServletRequest req) throws ValidationException {
        String idStr = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String date = req.getParameter("date");

        if (idStr == null) {
            throw new ValidationException("Id is missing");
        }
        if (firstName == null) {
            throw new ValidationException("First name is missing");
        }
        if (lastName == null) {
            throw new ValidationException("Last name is missing");
        }
        if (date == null) {
            throw new ValidationException("Date of birth is missing");
        }

        User user = new User();
        user.setId(Long.valueOf(idStr));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDate(DateFormat.getDateInstance().parse(date));
        } catch (ParseException e) {
            throw new ValidationException("Date format is incorrect");
        }
        return user;
    }
}
