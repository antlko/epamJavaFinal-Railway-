package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Object which operate Type information from DB
 * Have a {@link TypeDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class TypeDao implements Dao<Type> {

    private static final Logger LOG = Logger.getLogger(StationDao.class);

    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public TypeDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method is used for getting type id by name
     * @param name name tag of type
     * @return ID Type
     * @throws DBException
     */
    public Integer getIDTypeByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer typeId = null;
        try {
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_ID_TYPE_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                typeId = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_ID_TYPE, e);
            throw new DBException(Messages.ERR_GET_ID_TYPE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return typeId;
    }

    @Override
    public Type get(long id) {
        return null;
    }

    /**
     * This method is used for getting all types
     * @return list of types
     * @throws DBException
     */
    @Override
    public List<Type> getAll() throws DBException {
        Statement stmt = null;
        ResultSet rs = null;

        List<Type> types = new ArrayList<>();
        Type typeTemp = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(Queries.SQL_GET_ALL_TYPES);
            while (rs.next()) {
                typeTemp = new Type();
                typeTemp.setId(rs.getInt("id"));
                typeTemp.setName(rs.getString("name"));
                typeTemp.setPrice(rs.getInt("price"));
                types.add(typeTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_TYPES, e);
            throw new DBException(Messages.ERR_GET_TYPES, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
        }
        return types;
    }

    /**
     * This method is used for saving new type
     * @param type object of Types
     * @throws DBException
     */
    @Override
    public void save(Type type) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_TYPE_BY_NAME);
            int atr = 1;
            pstmt.setString(atr, type.getName());
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_TYPE);
                atr = 1;
                pstmt.setString(atr++, type.getName());
                pstmt.setInt(atr, type.getPrice());
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                update(type, null);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_SAVE_CARR_TYPE, e);
            throw new DBException(Messages.ERR_SAVE_CARR_TYPE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for update type price
     * @param type object of type
     * @param params other parameters
     * @throws DBException
     */
    @Override
    public void update(Type type, String[] params) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_UPDATE_PRICE_OF_TYPE);
            int atr = 1;
            pstmt.setInt(atr++, type.getPrice());
            pstmt.setString(atr, type.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_UPDATE_CARR_TYPE, e);
            throw new DBException(Messages.ERR_UPDATE_CARR_TYPE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for deleting type
     * @param type object of Type
     * @throws DBException
     */
    @Override
    public void delete(Type type) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_TYPE);
            pstmt.setString(1, type.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_DELETE_TYPE_OF_CARRIAGE, e);
            throw new DBException(Messages.ERR_DELETE_TYPE_OF_CARRIAGE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
