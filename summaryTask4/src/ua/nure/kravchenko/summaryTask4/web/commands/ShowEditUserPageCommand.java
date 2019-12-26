package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEditUserPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = DBManager.getInstance().getUserById(id);
        request.getSession().setAttribute("euser", user);
        return Path.PAGE_SHOW_EDIT_USER_PAGE;
    }
}
