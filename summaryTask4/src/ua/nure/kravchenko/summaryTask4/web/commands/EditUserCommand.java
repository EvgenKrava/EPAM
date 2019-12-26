package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        User user = (User) request.getSession().getAttribute("euser");
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("name"));
        user.setLastName(request.getParameter("surname"));
        user.setRoleId(request.getParameter("role").equals("ADMIN")?1:0);
        DBManager.getInstance().updateUser(user.getId(), user);
        request.setAttribute("page", "controller?command=users");
        return Path.PAGE_PRG;
    }
}
