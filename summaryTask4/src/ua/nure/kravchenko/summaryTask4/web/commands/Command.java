package ua.nure.kravchenko.summaryTask4.web.commands;

import ua.nure.kravchenko.summaryTask4.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws AppException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
