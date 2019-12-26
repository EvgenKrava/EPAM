package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

public class SetLanguageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Locale locale = new Locale(request.getParameter("language"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("text", locale);
        request.getSession().setAttribute("language", request.getParameter("language"));
        return "";
    }
}
