package dbService;

import base.DBService;
import base.UserProfile;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author v.chibrikov
 */
public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl() {
        this.connection = getPgConnection();
    }

    public void create() throws DBException {
        try {
            System.out.println("Creating table users if needed");
            (new UsersDAO(connection)).createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public UserProfile getUser(String login) throws DBException {
        try {
            UsersDAO dao = new UsersDAO(connection);
            UsersDataSet dataSet = dao.get(login);
            return new UserProfile(dataSet.getLogin(), dataSet.getPassword());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long addUser(UserProfile userProfile) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.insertUser(userProfile);
            connection.commit();
            return dao.getUserId(userProfile.getLogin());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public void check() throws DBException {
        try {
            System.out.println("Driver name: " + connection.getMetaData().getDriverName());
            System.out.println("Driver version: " + connection.getMetaData().getDriverVersion());

            UsersDAO dao = new UsersDAO(connection);
            int count = dao.getUsersCount();
            System.out.println("Count of records in users: " + count);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.cleanup();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

//    private static Connection getH2Connection() {
//        try {
//            String url = "jdbc:h2:./h2db";
//            String name = "tully";
//            String pass = "tully";
//
//            JdbcDataSource ds = new JdbcDataSource();
//            ds.setURL(url);
//            ds.setUser(name);
//            ds.setPassword(pass);
//
//            Connection connection = DriverManager.getConnection(url, name, pass);
//            return connection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static Connection getPgConnection() {
        try {

            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());

            String url = "jdbc:postgresql://localhost:5432/bd_example";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password", "123");
            Connection connection = DriverManager.getConnection(url, props);

            url = "jdbc:postgresql://localhost/test?user=postgres&password=123";

            System.out.println("URL: " + url + "\n");
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
