package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

public class ClientAuthoriseCommand extends Command {

    private static final Logger logger = Logger.getLogger(ClientAuthoriseCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        logger.debug("Command starts");
        //get all tariffs
        List<Tariff> tariffs = DBManager.getInstance().getAllTariffs();
        //sort tariffs by price
        tariffs.sort(Comparator.comparing(Tariff::getPrice));
        // put tariff list to the request
        request.setAttribute("tariffs", tariffs);
        logger.trace("Set the request attribute: tariffs --> " + tariffs);
        logger.debug("Command finished");
        return Path.PAGE_CLIENT_PAGE;
    }
}
