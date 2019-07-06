package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao implements Dao<City> {

    public static Integer getIdCityByName(String name) {
        Integer idCity = null;
        try(Connection conn = DBUtil.getInstance().getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM cities WHERE name = ?");
        ) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                idCity = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCity;
    }

    @Override
    public City get(long id) {
        return null;
    }

    @Override
    public List<City> getAll() {
        List<City> cityNames = new ArrayList<>();
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Cities ORDER BY name");
            while (rs.next()) {
                City cityTemp = new City();
                cityTemp.setName(rs.getString("name"));
                cityNames.add(cityTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityNames;
    }

    @Override
    public void save(City city) {
        PreparedStatement pstmt = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            pstmt = conn.prepareStatement("INSERT INTO cities(name,id_country) VALUES(?,?)");
            int atr = 1;
            pstmt.setString(atr++, city.getName());
            pstmt.setInt(atr, city.getIdCountry());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(City city, String[] params) {

    }

    @Override
    public void delete(City city) {
        PreparedStatement pstmt = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            pstmt = conn.prepareStatement("DELETE FROM cities WHERE name = ?");
            pstmt.setString(1, city.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
