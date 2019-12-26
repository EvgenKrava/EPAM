package ua.nure.kravchenko.summaryTask4;

import org.junit.Test;
import ua.nure.kravchenko.summaryTask4.Path;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.web.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class Tests {

    @Test
    public void controllerTest() throws ServletException, IOException {
        Controller controller = new Controller();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void dbUserTest() throws SQLException {
        User user = mock(User.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        Connection connection = mock(Connection.class);
        DataSource dataSource = mock(DataSource.class);
        ResultSet resultSet = mock(ResultSet.class);
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(dataSource.getConnection()).thenReturn(connection);
        user = new User();
        user.setId(1);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("Name");
        user.setLastName("Surname");
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn(user.getLogin());
        when(resultSet.getString(3)).thenReturn(user.getPassword());
        when(resultSet.getString(4)).thenReturn(user.getFirstName());
        when(resultSet.getString(5)).thenReturn(user.getLastName());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }
}
