package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand extends Command {

    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        logger.debug("Command starts");

        String language = (String) request.getSession().getAttribute("language");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        request.getSession().setAttribute("language", language);
        logger.trace("Command finished");
        return Path.PAGE_LOGIN;
    }
}
