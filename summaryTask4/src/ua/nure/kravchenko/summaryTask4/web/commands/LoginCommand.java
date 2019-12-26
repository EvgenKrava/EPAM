package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.Role;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        logger.debug("Command starts");

        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            switch (user.getRole()) {
                case ADMIN:
                    return Path.PAGE_ADMIN_PAGE;
                case CLIENT:
                    return Path.PAGE_CLIENT_PAGE;
                default:
                    return Path.PAGE_LOGIN;
            }
        }

        DBManager dbManager = DBManager.getInstance();

        String login = request.getParameter("login");
        logger.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("Login/password cannot be empty");
        }

        User user = dbManager.getUserByLoginPassword(login, password);
        logger.trace("Found in DB: user --> " + user);

        if (user == null) {
            throw new AppException("Cannot find user with such login/password");
        }

        Role role = Role.getRole(user);
        logger.trace("User role --> " + role);

        String forward = Path.PAGE_ERROR_PAGE;

        if (role.equals(Role.ADMIN)) {
            forward = Path.PAGE_ADMIN_PAGE;
        }

        if (role.equals(Role.CLIENT)) {
            forward = Path.PAGE_CLIENT_PAGE;
        }

        session.setAttribute("user", user);
        logger.trace("Set the session attribute: user --> " + user);

        session.setAttribute("role", role);
        logger.trace("Set the session attribute: userRole --> " + role);

        logger.info("User " + user + " logged as " + role.toString().toLowerCase());

        logger.debug("Command finished");
        return forward;
    }
}
