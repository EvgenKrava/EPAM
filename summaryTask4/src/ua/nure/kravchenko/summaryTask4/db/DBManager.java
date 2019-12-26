package ua.nure.kravchenko.summaryTask4.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ua.nure.kravchenko.summaryTask4.db.entity.Service;
import ua.nure.kravchenko.summaryTask4.db.entity.Tariff;
import ua.nure.kravchenko.summaryTask4.db.entity.User;
import ua.nure.kravchenko.summaryTask4.exception.DBException;
import ua.nure.kravchenko.summaryTask4.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBManager {

    private static DBManager instance;

    public static DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
    }

    private static Logger logger = Logger.getLogger(DBManager.class);

    private static final String SQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/st4db?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static final String SQL_ADD_USER = "insert into users (login, password, first_name, last_name, role_id, balance) values(?, ?, ?, ?, ?, ?)";

    private static final String SQL_GET_USER_BY_LOGIN_AND_PASSWORD = "select * from users where login = ? and password = ?";

    private static final String SQL_GET_SERVICES_ID_BY_USER_ID = "select service_id from users_services where user_id = ?";

    private static final String SQL_GET_SERVICE_BY_ID = "select * from services where id = ?";

    private static final String SQL_GET_TARIFFS_BY_SERVICE_ID = "select * from tariffs where service_id = ? ";

    private static final String SQL_GET_TARIFF_BY_ID = "select * from tariffs where id = ?";

    private static final String SQL_GET_ALL_USERS = "select * from users";

    private static final String SQL_GET_TARIFF_ID_BY_USER_ID = "select * from users_tariffs where user_id = ?";

    private static final String SQL_GET_ALL_TARIFFS = "select * from tariffs";

    private static final String SQL_GET_ID_BY_LOGIN = "select id from users where login = ?";

    private static final String SQL_REMOVE_USER_BY_LOGIN = "delete from users where login = ?";

    private static final String SQL_GET_ALL_SERVICES = "select * from services";

    private static final String SQL_ADD_TARIFF_TO_USER = "insert into users_tariffs values (? , ?)";

    private static final String SQL_UPDATE_USER = "update users set login = ?, password = ? ,first_name = ?, last_name = ?, role_id= ?, balance = ? where id = ?";

    private static final String SQL_DENY_TARIFF_FOR_USER = "delete from users_tariffs where user_id = ? and tariff_id = ?";

    private static final String SQL_ADD_MONEY = "update users set balance = ? where login = ?";

    private static final String SQL_GET_BALANCE_BY_LOGIN = "select balance from users where login = ?";

    private static final String SQL_ADD_TARIFF = "insert into tariffs (name, price, service_id) values  (?, ?, ?)";

    private static final String DELETE_TARIFF_BY_ID = "delete from tariffs where id = ?";

    private static final String SQL_EDIT_TARIFF = "update tariffs set name = ?, price = ?, service_id = ? where id = ?";

    private static final String SQL_GET_USER_BY_ID = "select * from users where id = ?";

    private static final String SQL_DELETE_TARIFF_TO_USER = "delete from users_tariffs where user_id =? and tariff_id = ?";

    private static final String SQL_GET_USERS_COUNT_BY_TARIFF_ID = "select * from users_tariffs where tariff_id = ?";

    private Connection getConnection() throws DBException {
        Context context;
        Connection connection = null;
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource) context
                    .lookup("java:comp/env/jdbc/pool");
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException | NamingException e) {
            logger.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
        }
        return connection;
    }

    public void addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setInt(5, user.getRoleId());
            preparedStatement.setDouble(6, user.getBalance());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException | DBException e) {
            rollback(connection);
            logger.error(Messages.ERR_CANNOT_ADD_USER, e);

        } finally {
            close(connection, preparedStatement, null);
        }


    }

    public User getUserByLoginPassword(String login, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setFirstName(resultSet.getString(Fields.FIRST_NAME));
                user.setLastName(resultSet.getString(Fields.LAST_NAME));
                user.setRoleId(resultSet.getInt(Fields.ROLE_ID));
                user.setBalance(resultSet.getDouble(Fields.BALANCE));
                user.setId(resultSet.getInt(Fields.ID));
                connection.commit();
                close(connection, preparedStatement, resultSet);
                List<Integer> tariffId = getTariffsIdByUserId(user.getId());
                List<Tariff> tariffs = new ArrayList<>();

                for (Integer i : tariffId) {
                    tariffs.add(getTariffById(i));
                }
                user.setTariffs(tariffs);

            }
        } catch (DBException | SQLException e) {
            rollback(connection);
            logger.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    public Tariff getTariffById(int tariffId) {
        Tariff tariff = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_TARIFF_BY_ID);
            preparedStatement.setInt(1, tariffId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tariff = extractTariff(resultSet);
            }
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return tariff;
    }

    private Service getServiceById(int serviceId) {
        Service service = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            service = new Service();
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_SERVICE_BY_ID);
            preparedStatement.setInt(1, serviceId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                service.setId(serviceId);
                service.setName(resultSet.getString(Fields.NAME));
            }
            connection.commit();
            close(connection, preparedStatement, resultSet);
            List<Tariff> tariffs = getTariffsByServiceId(serviceId);
            service.setTariffs(tariffs);
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return service;
    }

    public void addTariffToUser(int userId, int tariffId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_TARIFF_TO_USER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tariffId);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public List<Tariff> getTariffsByServiceId(int serviceId) {
        List<Tariff> tariffs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            tariffs = new ArrayList<>();
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_TARIFFS_BY_SERVICE_ID);
            preparedStatement.setInt(1, serviceId);
            resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                Tariff tariff = new Tariff();
                tariff.setId(resultSet.getInt(Fields.ID));
                tariff.setPrice(resultSet.getDouble(Fields.PRICE));
                tariff.setName(resultSet.getString(Fields.NAME));
                tariff.setServiceId(resultSet.getInt(Fields.SERVICE_ID));
                tariffs.add(tariff);
            }
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return tariffs;
    }

    public List<Integer> getTariffsIdByUserId(int userId) {
        List<Integer> tariffId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            tariffId = new ArrayList<>();
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_TARIFF_ID_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tariffId.add(resultSet.getInt(Fields.TARIFF_ID));
            }
            connection.commit();
        } catch (DBException | SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return tariffId;
    }

    public int getIdByLogin(String login) {
        int result = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ID_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(Fields.ID);
            }
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return result;
    }

    public int deleteUser(String login) {
        int id = getIdByLogin(login);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_REMOVE_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
        return id;
    }

    /*public Tariff getTariffById(int tariffId) {
        Tariff tariff = new Tariff();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_TARIFF_BY_ID);
            preparedStatement.setInt(1, tariffId);
            resultSet = preparedStatement.executeQuery();
            tariff.setId(resultSet.getInt(Fields.ID));
            tariff.setName(resultSet.getString(Fields.NAME));
            tariff.setPrice(resultSet.getDouble(Fields.PRICE));
            tariff.setServiceId(resultSet.getInt(Fields.SERVICE_ID));
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return tariff;
    }
*/

    public void updateUser(int id, User newUser) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
            preparedStatement.setString(1, newUser.getLogin());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getFirstName());
            preparedStatement.setString(4, newUser.getLastName());
            preparedStatement.setInt(5, newUser.getRoleId());
            preparedStatement.setDouble(6, newUser.getBalance());
            preparedStatement.setInt(7, id);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }

    }

    public List<Service> getAllServices() {
        List<Service> services = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            services = new ArrayList<>();
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_SERVICES);
            while (resultSet.next()) {
                Service service = new Service();
                service.setId(resultSet.getInt(Fields.ID));
                service.setName(resultSet.getString(Fields.NAME));
                services.add(service);
            }
            connection.commit();
            close(connection, statement, resultSet);
            for (Service c : services) {
                List<Tariff> tariffs = getTariffsByServiceId(c.getId());
                c.setTariffs(tariffs);
            }
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, statement, resultSet);
        }
        return services;
    }

    public List<User> getAllUsers() {
        List<User> userList = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            userList = new ArrayList<>();
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_USERS);
            while (resultSet.next()) {
                userList.add(extractUser(resultSet));
            }
            connection.commit();
            close(connection, statement, resultSet);
            for (User u : userList) {
                List<Integer> tariffsId = getTariffsIdByUserId(u.getId());
                List<Tariff> tariffs = new ArrayList<>();
                for (Integer i : tariffsId) {
                    tariffs.add(getTariffById(i));
                }
                u.setTariffs(tariffs);
            }
        } catch (DBException | SQLException e) {
            rollback(connection);
            logger.error(e);
        } finally {
            close(connection, statement, resultSet);
        }
        return userList;
    }

    /**
     * Extracts a user entity from the result set.
     *
     * @param rs Result set from which a user entity will be extracted.
     * @return User entity
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(Fields.ID));
        user.setLogin(rs.getString(Fields.LOGIN));
        user.setPassword(rs.getString(Fields.PASSWORD));
        user.setFirstName(rs.getString(Fields.FIRST_NAME));
        user.setLastName(rs.getString(Fields.LAST_NAME));
        user.setRoleId(rs.getInt(Fields.ROLE_ID));
        user.setBalance(rs.getDouble(Fields.BALANCE));
        return user;
    }

    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con Connection to be rollbacked.
     */
    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error("Cannot rollback transaction", ex);
            }
        }
    }

    public List<Tariff> getAllTariffs() {
        List<Tariff> tariffs = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            tariffs = new ArrayList<>();
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_GET_ALL_TARIFFS);
            while (resultSet.next()) {
                tariffs.add(extractTariff(resultSet));
            }
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, statement, resultSet);
        }
        return tariffs;
    }

    private Tariff extractTariff(ResultSet resultSet) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getInt(Fields.ID));
        tariff.setName(resultSet.getString(Fields.NAME));
        tariff.setServiceId(resultSet.getInt(Fields.SERVICE_ID));
        tariff.setPrice(resultSet.getDouble(Fields.PRICE));
        return tariff;
    }

    public void giveMoneyFromUsers() {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getRole().equals(Role.ADMIN)) {
                continue;
            }
            double sum = 0.0;
            for (Tariff t : u.getTariffs()) {
                if (t != null) {
                    sum += t.getPrice();
                }
            }
            u.setBalance((u.getBalance() - sum) >= 0 ? (u.getBalance() - sum) : u.getBalance());
            updateUser(u.getId(), u);
        }

    }

    public void denyTariffForUser(int userId, int tariffId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_DENY_TARIFF_FOR_USER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tariffId);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public void addMoney(String login, double amount) {
        double balance = getBalanceByLogin(login);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_MONEY);
            preparedStatement.setDouble(1, balance + amount);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    private double getBalanceByLogin(String login) {
        double result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_BALANCE_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getDouble("balance");
            }
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return result;
    }

    public void addTariff(Tariff tariff) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_TARIFF);
            preparedStatement.setString(1, tariff.getName());
            preparedStatement.setDouble(2, tariff.getPrice());
            preparedStatement.setInt(3, tariff.getServiceId());
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public void deleteTariff(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_TARIFF_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public void editTariff(Tariff tariff) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_EDIT_TARIFF);
            preparedStatement.setString(1, tariff.getName());
            preparedStatement.setDouble(2, tariff.getPrice());
            preparedStatement.setInt(3, tariff.getServiceId());
            preparedStatement.setInt(4, tariff.getId());
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public User getUserById(int id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    public void deleteTariffToUser(int userId, int tariffId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_TARIFF_TO_USER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, tariffId);
            preparedStatement.execute();
            connection.commit();
        } catch (DBException | SQLException e) {
            logger.error(e);
            rollback(connection);
        } finally {
            close(connection, preparedStatement, null);
        }
    }


    public int getUsersCountByTariffId(int id) {
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USERS_COUNT_BY_TARIFF_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result++;
            }
            connection.commit();
        } catch (DBException | SQLException e) {
            rollback(connection);
            logger.error(e);
        }finally {
            close(connection, preparedStatement, resultSet);
        }
        return result;
    }
}
