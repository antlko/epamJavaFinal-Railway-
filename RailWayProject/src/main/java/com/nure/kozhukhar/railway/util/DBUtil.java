package com.nure.kozhukhar.railway.util;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;

import static java.util.Arrays.asList;


/**
 * DB Util. Works with MYSQL DB.
 * <p>
 * This class using singleton pattern.
 * Method {@link #getInstance} is synchronized.
 *</p>
 * @author Anatol Kozhukhar
 */
public class DBUtil {

    private static final Logger LOG = Logger.getLogger(DBUtil.class);

    /**
     * Connection type which loading from application.properties.
     * There exist two type of connection :
     * <ol>
     * <li>JNDI using (loading with context info).</li>
     * <li>MysqlConnectionPoolDataSource connection.</li>
     * </ol>
     */
    private static String connectionType;

    private static DBUtil instance;

    private DataSource ds;

    /*
      Static field for loading connection type from application.properties
     */
    static {
        Properties appProps = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("application.properties");
        try {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionType = appProps.getProperty("connectionType")
                .trim().replaceAll("\\D+", "");
    }

    /**
     * Instance (DBUtil) contains data source information and
     * connection type information which can set in application.properties.
     *  <br>
     *  <h3>Instance creating :</h3>
     * {@code new DBUtil(connectionType)}
     *
     * @return DBUtil instance
     * @throws DBException
     * @throws ClassNotFoundException
     */
    public static synchronized DBUtil getInstance() throws DBException, ClassNotFoundException {
        if (instance == null) {
            instance = new DBUtil(connectionType);
        }
        return instance;
    }

    /**
     * Constructor of singleton class DBUtil.
     *
     * @param type Connection type {@link #connectionType}
     * @throws DBException
     */
    private DBUtil(String type) throws DBException {
        if (!"".equals(type) && Integer.valueOf(type) == 1) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                ds = (DataSource) envContext.lookup("jdbc/ResConDB");
                LOG.trace("Data source ==> " + ds);
            } catch (NamingException ex) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            }
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
                dataSource
                        .setURL("jdbc:mysql://localhost:3306/db_train?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
                dataSource.setUser("jusertest");
                dataSource.setPassword("12345");
                ds = dataSource;
            } catch (ClassNotFoundException ex) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            }
        }
    }

    /**
     * When you using this method you can get DataSource with connection
     * information. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return dataSource
     */
    public DataSource getDataSource() {
        return ds;
    }

    /**
     * Rollbacks a connection.
     *
     * @param conn Connection to be rollbacked.
     */
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error("Cannot close connection!", ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error("Cannot close statement!", ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error("Cannot close resultSet!", ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    public static void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }
}
