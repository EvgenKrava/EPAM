package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IncludeTariffForUserCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        int tariffId = Integer.parseInt(request.getParameter("tariffId"));
        User user = (User) request.getSession().getAttribute("user");
        Tariff tariff = DBManager.getInstance().getTariffById(tariffId);
        System.out.println(user.getTariffs());
        for (Tariff t: user.getTariffs()) {
            if(t!=null){
                if (t.getServiceId() == tariff.getServiceId()){
                    DBManager.getInstance().deleteTariffToUser(user.getId(), t.getId());
                }
            }
        }
        if(tariff.getPrice()>user.getBalance()){
            request.setAttribute("errorMessage", "Not enough money");
            return Path.PAGE_ERROR_PAGE;
        }
        user.setBalance(user.getBalance()-tariff.getPrice());
        DBManager.getInstance().updateUser(user.getId(), user);
        DBManager.getInstance().addTariffToUser(user.getId(), tariffId);
        request.setAttribute("page", "controller?command=tariffs");
        request.getSession().setAttribute("user", DBManager.getInstance().getUserByLoginPassword(user.getLogin(), user.getPassword()));
        return Path.PAGE_PRG;
    }
}
