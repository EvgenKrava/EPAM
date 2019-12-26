package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.Role;
import ua.nure.kravchenko.summaryTask4.db.entity.Service;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTariffsCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        List<Service> services = DBManager.getInstance().getAllServices();
        List<Tariff> tariffs = DBManager.getInstance().getAllTariffs();
        Map<Integer, Integer> usersCount = new HashMap<>();
        for (Tariff t: tariffs) {
            usersCount.put(t.getId(), DBManager.getInstance().getUsersCountByTariffId(t.getId()));
        }
        session.setAttribute("tariffs", tariffs);
        session.setAttribute("services", services);
        session.setAttribute("usersCount", usersCount);
        Role role = (Role) session.getAttribute("role");
        if ("client".equals(role.getName())) {
            return Path.PAGE_SHOW_TARIFFS_PAGE_FOR_CLIENT;
        }
        return Path.PAGE_SHOW_TARIFFS_PAGE;
    }
}
