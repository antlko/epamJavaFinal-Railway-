package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDao implements Dao<Train> {


    public static Integer getMaxSizeFromCarriageByTrain(Integer trainNum, Integer carrNum) {

        Integer maxSize = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM carriages C INNER JOIN Trains T ON C.id_train = T.id\n" +
                     " WHERE C.num_carriage = ?" +
                     " AND T.number = ?")
        ) {
            int atr = 1;
            pstmt.setInt(atr++, carrNum);
            pstmt.setInt(atr, trainNum);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                maxSize = rs.getInt("max_size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxSize;
    }

    public static void saveTrainContent(Integer idTrain, Integer countCarr, Integer countSeat, Integer idType) {
        PreparedStatement pstmt = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
        ) {
            int atr = 1;
            Integer countCarriage = 0;
            pstmt = conn.prepareStatement("SELECT Count(num_carriage) as cnt FROM carriages WHERE id_train = ?");
            pstmt.setInt(atr, idTrain);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                countCarriage = rs.getInt("cnt");
                countCarriage++;
            }
            countCarr += countCarriage;
            for (int numCarr = countCarriage; numCarr < countCarr; ++numCarr) {
                try {
                    atr = 1;
                    pstmt = conn.prepareStatement("INSERT INTO carriages(id_train, num_carriage,id_type, max_size) VALUES(?,?,?,?)");
                    pstmt.setInt(atr++, idTrain);
                    pstmt.setInt(atr++, numCarr);
                    pstmt.setInt(atr++, idType);
                    pstmt.setInt(atr, countSeat);
                    pstmt.executeUpdate();
                    for (int numSeat = 1; numSeat <= countSeat; ++numSeat) {
                        atr = 1;
                        pstmt = conn.prepareStatement("INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(?,?,?)");
                        pstmt.setInt(atr++, idTrain);
                        pstmt.setInt(atr++, numCarr);
                        pstmt.setInt(atr, numSeat);
                        pstmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<TrainStatisticBean> getTrainsStatistic() {
        List<TrainStatisticBean> trainStatistic = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_SELECT_COUNT_CARRIAGES_AND_SEATS);
        ) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TrainStatisticBean trainStatTemp = new TrainStatisticBean();
                trainStatTemp.setTrainNumber(rs.getInt("number"));
                trainStatTemp.setCountCarriages(rs.getInt("carriages"));
                trainStatTemp.setCountSeats(rs.getInt("seats"));
                trainStatistic.add(trainStatTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainStatistic;
    }

    public static void deleteAllTrainContent(Integer idTrain) {
        PreparedStatement pstmt = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
        ) {
            pstmt = conn.prepareStatement("DELETE FROM seats WHERE id_train = ? ");
            pstmt.setInt(1, idTrain);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("DELETE FROM carriages WHERE id_train = ?;");
            pstmt.setInt(1, idTrain);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
