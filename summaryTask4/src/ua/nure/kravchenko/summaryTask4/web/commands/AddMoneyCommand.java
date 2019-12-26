package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddMoneyCommand extends Command {

    private static final Logger logger = Logger.getLogger(AddMoneyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        User user = (User) request.getSession().getAttribute("user");
        logger.trace("Command Add Money started");
        String login = request.getParameter("login");
        HttpSession session = request.getSession();
        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
            if (amount <= 0) {
                logger.trace("Amount of money < 0");
                session.setAttribute("errorMessage", "Uncorrected input ");
                return Path.PAGE_ERROR_PAGE;
            }
        } catch (Exception e) {
            logger.warn("Incorrect input to amount form", e);
            session.setAttribute("errorMessage", "Uncorrected input ");
            return Path.PAGE_ERROR_PAGE;
        }
        logger.trace("Add money login: " + login + " amount: " + amount);
        DBManager.getInstance().addMoney(login, amount);
        if (user != null) {
            request.getSession().setAttribute("user", DBManager.getInstance()
                    .getUserByLoginPassword(user.getLogin(), user.getPassword()));
        }
        request.setAttribute("page", "/summaryTask4/controller?command=home");
        return Path.PAGE_PRG;
    }
}
