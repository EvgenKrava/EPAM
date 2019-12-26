package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTariffCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        Tariff tariff = (Tariff) request.getSession().getAttribute("tariff");
        tariff.setName(request.getParameter("name"));
        tariff.setPrice(Double.parseDouble(request.getParameter("price")));
        tariff.setServiceId(Integer.parseInt(request.getParameter("service_id")));
        DBManager.getInstance().editTariff(tariff);
        request.setAttribute("page", "controller?command=tariffs");
        return Path.PAGE_PRG;
    }
}
