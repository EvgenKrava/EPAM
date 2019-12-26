package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowTopUpPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return Path.PAGE_SHOW_TOP_UP_PAGE;
    }
}
