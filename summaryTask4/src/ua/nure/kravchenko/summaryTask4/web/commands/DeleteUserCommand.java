package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.Role;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand extends Command {

    private static final Logger logger = Logger.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            logger.warn("User did not login");
            return Path.PAGE_LOGIN;
        } else if (user.getRole().equals(Role.CLIENT)) {
            request.getSession().setAttribute("errorMessage", "You do not have access to this command");
            return Path.PAGE_ERROR_PAGE;
        }
        String login = request.getParameter("login");
        DBManager.getInstance().deleteUser(login);
        request.setAttribute("page", "controller?command=users");
        return Path.PAGE_PRG;
    }
}
