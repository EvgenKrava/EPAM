package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

public class AdminAuthoriseCommand extends Command {

    private static final Logger logger = Logger.getLogger(AdminAuthoriseCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        logger.debug("Command starts");

        // get user list
        List<User> users = DBManager.getInstance().getAllUsers();
        logger.trace("Found in DB: menuItemsList --> " + users);

        // sort users by Last Name
        users.sort(Comparator.comparing(User::getLogin));

        //get all tariffs
        List<Tariff> tariffs = DBManager.getInstance().getAllTariffs();
        //sort tariffs by price
        tariffs.sort(Comparator.comparing(Tariff::getPrice));
        // put user list to the request
        request.getSession().setAttribute("users", users);
        logger.trace("Set the request attribute: users --> " + users);
        request.getSession().setAttribute("tariffs", tariffs);
        logger.trace("Set the request attribute: tariffs --> " + tariffs);

        logger.debug("Command finished");
        return Path.PAGE_ADMIN_PAGE;
    }
}
