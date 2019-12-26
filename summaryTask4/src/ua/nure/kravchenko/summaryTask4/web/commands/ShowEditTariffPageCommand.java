package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEditTariffPageCommand extends Command {

    Logger logger = Logger.getLogger(ShowEditTariffPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        logger.trace("Command show edit tariff page started");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Tariff tariff = DBManager.getInstance().getTariffById(id);
            request.getSession().setAttribute("tariff", tariff);
        }catch (Exception e){
            logger.error(e);

        }
        return Path.PAGE_SHOW_EDIT_TARIFF_PAGE;
    }
}
