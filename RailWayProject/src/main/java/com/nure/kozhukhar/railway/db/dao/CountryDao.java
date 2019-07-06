package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDao implements Dao<Country> {

    public static Integer findIdCountyByName(String name) {
        Integer idCounrty = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Countries WHERE name = ?");
        ) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idCounrty = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCounrty;
    }

    @Override
    public Country get(long id) {
        return null;
    }

    @Override
    public List<Country> getAll() {
        List<Country> countryNames = new ArrayList<>();
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Countries ORDER BY name");
            while (rs.next()) {
                Country countryTemp = new Country();
                countryTemp.setName(rs.getString("name"));
                countryNames.add(countryTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryNames;
    }

    @Override
    public void save(Country country) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO countries(name) VALUES(?)");
            pstmt.setString(1, country.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Country country, String[] params) {

    }

    @Override
    public void delete(Country country) {
        PreparedStatement pstmt = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            pstmt = conn.prepareStatement("DELETE FROM countries WHERE name = ?");
            pstmt.setString(1, country.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
