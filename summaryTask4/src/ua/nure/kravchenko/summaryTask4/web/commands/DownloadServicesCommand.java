package ua.nure.kravchenko.summaryTask4.web.commands;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.DBManager;
import ua.nure.kravchenko.summaryTask4.db.entity.Service;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DownloadServicesCommand extends Command {

    private static final Logger logger = Logger.getLogger(DownloadServicesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        logger.trace("Download service command started");
        List<Service> services = DBManager.getInstance().getAllServices();
        String language = (String) request.getSession().getAttribute("language");
        StringBuilder stringBuilder = new StringBuilder().append("\t\t\t");
        if ("rus".equals(language)){
            stringBuilder.append("Сервисы").append(System.lineSeparator());
        }else {
            stringBuilder.append("Services").append(System.lineSeparator());
        }

        for (Service s : services) {
            stringBuilder.append("\t").append(s.getName().toUpperCase()).append(System.lineSeparator());
            if ("rus".equals(language)){
                stringBuilder.append("   Имя            Цена").append(System.lineSeparator());
            }else {
                stringBuilder.append("   Name           Price").append(System.lineSeparator());
            }
            for (Tariff t : s.getTariffs()) {
                stringBuilder.append(" \u2022 ").append(t.getName());
                for (int i = 15; i > t.getName().length(); i--) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(t.getPrice()).append(System.lineSeparator());
            }
        }
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=Provider_services.txt");
        try (OutputStream out = response.getOutputStream();
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            outputStreamWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            logger.error(e);
        }
        return Path.PAGE_SHOW_TARIFFS_PAGE_FOR_CLIENT;
    }
}
