package com.nure.kozhukhar.railway.util;

import com.nure.kozhukhar.railway.exception.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBUtil {

    private static final Logger LOG = Logger.getLogger(DBUtil.class);

    private static DBUtil instance;

    private DataSource ds;

    public static synchronized DBUtil getInstance() {
        if (instance == null) {
            instance = new DBUtil();
        }
        return instance;
    }

    private DBUtil(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/ResConDB");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            //throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    public DataSource getDataSource() {
        return ds;
    }

    /**
     * Rollbacks a connection.
     *
     * @param conn
     *            Connection to be rollbacked.
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
     * @param con
     *            Connection to be closed.
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
    public  static void close(ResultSet rs) {
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
