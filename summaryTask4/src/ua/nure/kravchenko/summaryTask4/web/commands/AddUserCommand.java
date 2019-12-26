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
import java.io.UnsupportedEncodingException;

public class AddUserCommand extends Command {

    private static final Logger logger = Logger.getLogger(AddUserCommand.class);

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
        HttpSession session = request.getSession();
        session.setAttribute("users", DBManager.getInstance().getAllUsers());
        Role role = (Role) session.getAttribute("role");
        if (role.getName().equals("client")) {
            return Path.PAGE_ERROR_PAGE;
        }
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user = new User();
        String login = request.getParameter("login");
        user.setLogin(login);
        String password = request.getParameter("password");
        user.setPassword(password);
        String name = request.getParameter("name");
        user.setFirstName(name);
        String surname = request.getParameter("surname");
        user.setLastName(surname);
        user.setRoleId(request.getParameter("role").equals("ADMIN") ? 1 : 0);
        if (login.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return Path.PAGE_ERROR_PAGE;
        }
        DBManager.getInstance().addUser(user);
        request.setAttribute("page", "controller?command=users");
        return Path.PAGE_PRG;
    }
}
