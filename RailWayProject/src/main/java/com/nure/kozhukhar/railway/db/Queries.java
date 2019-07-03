package com.nure.kozhukhar.railway.db;

public class Queries {

    public static final String SQL_FIND_ROUTE_ON_DATE_AND_CITIES = "SELECT * FROM routes_on_date RD3, stations S3\n" +
            "\tWHERE RD3.id_station = S3.id\n" +
            "            AND RD3.date_end >= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "                        )\n" +
            "                    AND RD3.date_end <= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                    ) \n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_ROUTE_ON_DATE_BY_ROUTE_ID = "SELECT * FROM routes_on_date RD3, stations S3\n" +
            "\tWHERE RD3.id_station = S3.id\n" +
            "            AND RD3.date_end >= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "                        )\n" +
            "                    AND RD3.date_end <= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                    ) \n" +
            "                    AND RD3.id_route = ?\n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_ROUTE_ON_DATE_ID = "SELECT DISTINCT RD3.id_route, T.number, T.id FROM routes_on_date RD3, stations S3, trains T, routes R\n" +
            "\tWHERE RD3.id_station = S3.id \n" +
            "\t\tAND RD3.id_route = R.id \n" +
            "\t\tAND R.id_train = T.id\n" +
            " AND RD3.date_end >= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "                        )\n" +
            "                    AND RD3.date_end <= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                    ) \n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_SEAT_FREE_INFO = "SELECT H.nameType, H.free FROM (SELECT DISTINCT TP.name as nameType, S3.name as nameStation, Count(*) as free\n" +
            "            FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
            "            WHERE RD3.id_station = S3.id\n" +
            "            AND RD3.id_route = R.id \n" +
            "            AND R.id_train = T.id\n" +
            "                    AND CR.id_train = R.id_train\n" +
            "                    AND CR.id_type = TP.id\n" +
            "                    AND ST.num_carriage = CR.num_carriage\n" +
            "                    AND ST.id_train = R.id_train\n" +
            "                    AND RD3.id_route NOT IN (\n" +
            "            SELECT US.id_route FROM user_check US\n" +
            "                        WHERE US.id_route = RD3.id_route\n" +
            "            AND US.id_train = RD3.id_train\n" +
            "                            AND US.num_carriage = CR.num_carriage\n" +
            "                            AND US.num_seat = ST.num_seat\n" +
            "                            AND US.id_station = RD3.id_station\n" +
            "                    )\n" +
            "\t\t\tAND RD3.date_end >= (\n" +
            "            SELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "            )\n" +
            "                    AND RD3.date_end <= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                    )\n" +
            "                   AND id_route = ?\n" +
            "           GROUP BY TP.name, S3.name\n" +
            "           ORDER BY RD3.date_end\n" +
            "            ) as H\n" +
            "WHERE H.nameStation = ?;";
    public static final String SQL_FIND_FREE_SEATS_BY_TRAIN = "SELECT * FROM (SELECT DISTINCT TP.name as nameType, S3.name as nameStation, T.id as idTrain, CR.num_carriage as numCarr\n" +
            "            FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
            "            WHERE RD3.id_station = S3.id\n" +
            "            AND RD3.id_route = R.id \n" +
            "            AND R.id_train = T.id\n" +
            "                    AND CR.id_train = R.id_train\n" +
            "                    AND CR.id_type = TP.id\n" +
            "                    AND ST.num_carriage = CR.num_carriage\n" +
            "                    AND ST.id_train = R.id_train\n" +
            "                    AND RD3.id_route NOT IN (\n" +
            "            SELECT US.id_route FROM user_check US\n" +
            "                        WHERE US.id_route = RD3.id_route\n" +
            "            AND US.id_train = RD3.id_train\n" +
            "                            AND US.num_carriage = CR.num_carriage\n" +
            "                            AND US.num_seat = ST.num_seat\n" +
            "                            AND US.id_station = RD3.id_station\n" +
            "                    )\n" +
            "\t\t\tAND RD3.date_end >= (\n" +
            "            SELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "            )\n" +
            "                    AND RD3.date_end <= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                    )\n" +
            "                   AND T.number = ?\n" +
            "                   AND TP.name = ?\n" +
            "           GROUP BY S3.name, T.id,CR.num_carriage,ST.num_seat\n" +
            "ORDER BY RD3.date_end\n" +
            ") H\n" +
            "WHERE H.nameStation = ?\n" +
            "ORDER BY idTrain,numCarr";
    public static final String SQL_FIND_FREE_SEATS_BY_TRAIN_AND_CARRIAGE = "" +
            "SELECT * FROM (SELECT DISTINCT TP.name as nameType, S3.name as nameStation, T.id as idTrain, CR.num_carriage as numCarr, ST.num_seat as numSeat\n" +
            "            FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
            "            WHERE RD3.id_station = S3.id\n" +
            "            AND RD3.id_route = R.id \n" +
            "            AND R.id_train = T.id\n" +
            "                    AND CR.id_train = R.id_train\n" +
            "                    AND CR.id_type = TP.id\n" +
            "                    AND ST.num_carriage = CR.num_carriage\n" +
            "                    AND ST.id_train = R.id_train\n" +
            "                    AND RD3.id_route NOT IN (\n" +
            "            SELECT US.id_route FROM user_check US\n" +
            "                        WHERE US.id_route = RD3.id_route\n" +
            "            AND US.id_train = RD3.id_train\n" +
            "                            AND US.num_carriage = CR.num_carriage\n" +
            "                            AND US.num_seat = ST.num_seat\n" +
            "                            AND US.id_station = RD3.id_station\n" +
            "                    )\n" +
            "\t\t\tAND RD3.date_end >= (\n" +
            "            SELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "            )\n" +
            "                    AND RD3.date_end <= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                    )\n" +
            "                   AND T.number = ?\n" +
            "                   AND CR.num_carriage = ?\n" +
            "                   AND TP.name = ?\n" +
            "           GROUP BY S3.name, T.id,CR.num_carriage,ST.num_seat\n" +
            "ORDER BY RD3.date_end\n" +
            ") H\n" +
            "WHERE H.nameStation = ?\n" +
            "ORDER BY idTrain,numCarr,numSeat";
}
