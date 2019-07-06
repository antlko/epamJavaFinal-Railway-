package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDao implements Dao<Train> {

    public static Integer getIdTrainByNumber(Integer number) {
        Integer idTrain = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM trains WHERE number = ?");
        ) {
            pstmt.setInt(1, number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idTrain = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idTrain;
    }

    @Override
    public Train get(long id) {
        return null;
    }

    @Override
    public List<Train> getAll() {
        List<Train> trains = new ArrayList<>();
        Train trainTemp = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM trains;");
            while (rs.next()) {
                trainTemp = new Train();
                trainTemp.setId(rs.getInt("id"));
                trainTemp.setNumber(rs.getInt("number"));
                trains.add(trainTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }

    @Override
    public void save(Train train) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO trains(number) VALUES(?)");
            pstmt.setInt(1, train.getNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Train train, String[] params) {

    }

    @Override
    public void delete(Train train) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM trains WHERE number = ?");
        ) {
            pstmt.setInt(1, train.getNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
