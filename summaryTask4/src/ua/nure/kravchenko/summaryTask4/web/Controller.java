package ua.nure.kravchenko.summaryTask4.web;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.exception.AppException;
import ua.nure.kravchenko.summaryTask4.web.commands.Command;
import ua.nure.kravchenko.summaryTask4.web.commands.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Controller.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Controller started");
        String commandName = request.getParameter("command");
        logger.trace("Request parameter: command --> " + commandName );

        Command command = CommandContainer.getCommand(commandName);
        logger.trace("Obtain command ==> " +command);

        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            logger.error(e);
        }
        logger.trace("Forward address --> " + forward);

        logger.trace("Controller finished, now go to forward address --> " + forward);

        // go to forward
        request.getRequestDispatcher(forward).forward(request, response);

    }
}
