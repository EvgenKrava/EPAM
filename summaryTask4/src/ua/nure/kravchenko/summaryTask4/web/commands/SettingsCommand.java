package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SettingsCommand extends Command {

    private static final Logger logger = Logger.getLogger(SettingsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        logger.debug("Command starts");

        logger.debug("Command finished");
        return Path.PAGE_SETTINGS;
    }
}
