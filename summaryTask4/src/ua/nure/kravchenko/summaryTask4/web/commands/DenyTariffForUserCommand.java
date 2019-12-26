package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.Role;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DenyTariffForUserCommand extends Command {

    private static final Logger logger = Logger.getLogger(DeleteTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        String id = request.getParameter("tariffId");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            logger.warn("User did not login");
            return Path.PAGE_LOGIN;
        }
        if ("".equals(id) || id == null) {
            logger.warn("incorrectId");
            request.getSession().setAttribute("errorMessage", "Incorrect id");
            return Path.PAGE_ERROR_PAGE;
        }
        try {
            DBManager.getInstance().denyTariffForUser(user.getId(), Integer.parseInt(id));
            request.setAttribute("page", "controller?command=tariffs");
            request.getSession().setAttribute("user", DBManager.getInstance().getUserByLoginPassword(user.getLogin(), user.getPassword()));
        } catch (Exception e) {
            logger.error(e);
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_PRG;
    }
}
