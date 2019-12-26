package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.Role;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowUsersCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        List<User> users = DBManager.getInstance().getAllUsers();
        for (User u: users) {
            u.getTariffs().remove(null);
            DBManager.getInstance().updateUser(u.getId(), u);
        }
        session.setAttribute("users", users);
        Role role = (Role) session.getAttribute("role");
        if(role.getName().equals("client")){
            return Path.PAGE_ERROR_PAGE;
        }
        return Path.PAGE_SHOW_USERS_PAGE;
    }
}
