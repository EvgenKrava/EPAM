package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.Role;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTariffCommand extends Command {

    private static final Logger logger = Logger.getLogger(AddTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            logger.warn("User did not login");
            return Path.PAGE_LOGIN;
        }else if(user.getRole().equals(Role.CLIENT)){
            request.getSession().setAttribute("errorMessage", "You do not have access to this command");
            return Path.PAGE_ERROR_PAGE;
        }
        String name = request.getParameter("name");
        double price = 0.0;
        int service_id = 0;
        try {
            price = Double.parseDouble(request.getParameter("price"));
            service_id = Integer.parseInt(request.getParameter("service_id"));
        }catch (Exception e){
            request.getSession().setAttribute("errorMessage", "Incorrect input ");
            return Path.PAGE_ERROR_PAGE;
        }
        Tariff tariff = new Tariff();
        tariff.setName(name);
        tariff.setPrice(price);
        tariff.setServiceId(service_id);
        DBManager.getInstance().addTariff(tariff);
        request.setAttribute("page", "controller?command=tariffs");
        return Path.PAGE_PRG;
    }
}
