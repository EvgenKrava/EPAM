package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowHomeCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        return Path.PAGE_SHOW_HOME_PAGE;
    }
}
