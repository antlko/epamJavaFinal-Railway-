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
            "                    AND RD3.date_end <=ANY (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                    ) \n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_ROUTE_ON_DATE_BY_ROUTE_ID = "SELECT * \n" +
            "    FROM routes_on_date RD3, stations S3, routes_station RS\n" +
            "\tWHERE RD3.id_station = S3.id\n" +
            "\t\t\tAND RS.id_station = RD3.id_station\n" +
            "            AND RS.id_route = RD3.id_route\n" +
            "            AND RS.id_train = RD3.id_train\n" +
            "            AND RD3.date_end >=(\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "                        AND RD2.id_route = ?\n" +
            "                        LIMIT 1\n" +
            "                        )\n" +
            "\t\t\tAND RD3.date_end <=Any (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                        AND RD2.date_end <= (\n" +
            "\t\t\t\t\t\t\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
            "\t\t\t\t\t\t\t\t\t\tAND RD2.id_route = ?\n" +
            "\t\t\t\t\t\t\t\t\t\t)\n" +
            "                                AND S6.name = ?\n" +
            "                                AND RD6.id_route = ?\n" +
            "\t\t\t\t\t\t\t\tORDER BY date_end \n" +
            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
            "                        )\n" +
            "                        ORDER BY RD2.date_end\n" +
            "                    ) \n" +
            "\t\t\tAND RD3.id_route = ?\n" +
            "ORDER BY date_end";

    public static final String SQL_FIND_ROUTE_ON_DATE_ID = "SELECT DISTINCT RD3.id_route, T.number, T.id FROM routes_on_date RD3, stations S3, trains T, routes R\n" +
            "\tWHERE RD3.id_station = S3.id \n" +
            "\t\tAND RD3.id_route = R.id \n" +
            "\t\tAND R.id_train = T.id\n" +
            "\tAND RD3.date_end >= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "                         LIMIT 1  "+
            "                        )\n" +
            " AND RD3.date_end <=Any (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                        AND RD3.date_end <= (\n" +
            "\t\t\t\t\t\t\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
            "           "+
            "\t\t\t\t\t\t\t\t\t\t)\n" +
            "                                AND S6.name = ?\n" +
            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
            "                        )\n" +
            "                        ORDER BY RD2.date_end\n" +
            "                    ) \n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_SEAT_FREE_INFO = "SELECT nameType, MIN(freeCnt) as free FROM (\n" +
            "SELECT TP.name as nameType, Count(*) as freeCnt FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
            "WHERE RD3.id_station = S3.id\n" +
            "            AND RD3.id_route = R.id \n" +
            "            AND R.id_train = T.id\n" +
            "\t\t\tAND CR.id_train = R.id_train\n" +
            "            AND CR.id_type = TP.id\n" +
            "            AND ST.num_carriage = CR.num_carriage\n" +
            "            AND ST.id_train = R.id_train\n" +
            "            AND RD3.id_route NOT IN (\n" +
            "\t\t\t\tSELECT US.id_route FROM user_check US\n" +
            "\t\t\t\t\tWHERE US.id_route = RD3.id_route\n" +
            "\t\t\t\t\tAND US.id_station = RD3.id_station\n" +
            "\t\t\t\t\tAND US.id_train = RD3.id_train\n" +
            "\t\t\t\t\tAND US.num_seat = ST.num_seat\n" +
            "\t\t\t\t\tAND US.num_carriage = ST.num_carriage\n" +
            "\t\t\t\t\tAND US.date_end = RD3.date_end\n" +
            "\t\t\t\t\t\n" +
            "\t\t\t)\n" +
            "AND RD3.date_end >= ?\n" +
            "AND RD3.date_end <= (\n" +
            "\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
            "                    AND RD2.id_route = ?"+
            "\t\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\t\t\t)\n" +
            "                                AND S6.name = ?\n" +
            "\t\t\t\t\t\t\t\tAND RD6.id_route = ?\n" +
            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
            ")\n" +
            "AND RD3.id_route = ?\n" +
            "AND S3.name IN ( \n" +
            "\tSELECT ST1.name FROM routes_station S4 INNER JOIN Stations ST1 ON S4.id_station = ST1.id\n" +
            "\tWHERE S4.id_route = RD3.id_route\n" +
            "\t\tAND S4.time_end < (\n" +
            "\t\t\tSELECT S5.time_end FROM routes_station S5 INNER JOIN Stations ST2 ON S5.id_station = ST2.id\n" +
            "\t\t\tWHERE ST2.name = ?\n" +
            "\t\t\t\tAND S5.id_route = RD3.id_route\n" +
            "\t\t)\n" +
            ")\n" +
            " GROUP BY TP.name, S3.name\n" +
            " ) H\n" +
            " GROUP BY nameType";

//    public static final String SQL_FIND_SEAT_FREE_INFO = "SELECT H.nameType, H.free FROM (SELECT DISTINCT TP.name as nameType, S3.name as nameStation, Count(*) as free\n" +
//            "            FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
//            "            WHERE RD3.id_station = S3.id\n" +
//            "            AND RD3.id_route = R.id \n" +
//            "            AND R.id_train = T.id\n" +
//            "                    AND CR.id_train = R.id_train\n" +
//            "                    AND CR.id_type = TP.id\n" +
//            "                    AND ST.num_carriage = CR.num_carriage\n" +
//            "                    AND ST.id_train = R.id_train\n" +
//            "                    AND RD3.id_route NOT IN (\n" +
//            "            SELECT US.id_route FROM user_check US\n" +
//            "                        WHERE US.id_route = RD3.id_route\n" +
//            "            AND US.id_train = RD3.id_train\n" +
//            "                            AND US.num_carriage = CR.num_carriage\n" +
//            "                            AND US.num_seat = ST.num_seat\n" +
//            "                            AND US.id_station = RD3.id_station\n" +
//            "                    )\n" +
//            "\tAND RD3.date_end >= (\n" +
//            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
//            "                        WHERE RD2.id_station = S2.id\n" +
//            "                        AND S2.name = ?\n" +
//            "                        AND ? = DATE(RD2.date_end)\n" +
//            "                        AND RD2.id_route = ?"+
//            "                         LIMIT 1  "+
//            "                        )\n" +
//            "\tAND RD3.date_end <=Any (\n" +
//            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
//            "                        WHERE RD2.id_station = S2.id \n" +
//            "                        AND S2.name = ?\n" +
//            "                        AND RD3.date_end <= (\n" +
//            "\t\t\t\t\t\t\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
//            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
//            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
//            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
//            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
//            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
//            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
//            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
//            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
//            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
//            "\t\t\t\t\t\t\t\t\t\t)\n" +
//            "                                AND S6.name = ?\n" +
//            "                                AND RD6.id_route = ?\n" +
//            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
//            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
//            "                        )\n" +
//            "                        ORDER BY RD2.date_end\n" +
//            "                    ) \n" +
//            "\t\t\tAND RD3.id_route = ?" +
//            "           GROUP BY TP.name, S3.name\n" +
//            "           ORDER BY RD3.date_end\n" +
//            "            ) as H\n" +
//            "WHERE H.nameStation = ?;\n";
    public static final String SQL_FIND_FREE_SEATS_BY_TRAIN = "SELECT * FROM (SELECT DISTINCT TP.price as tpPrice, TP.name as nameType, S3.name as nameStation, T.id as idTrain, CR.num_carriage as numCarr\n" +
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
            "\tAND RD3.date_end >= (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id\n" +
            "                        AND S2.name = ?\n" +
            "                        AND ? = DATE(RD2.date_end)\n" +
            "                         LIMIT 1  "+
            "                        )\n" +
            "\tAND RD3.date_end <=Any (\n" +
            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "                        WHERE RD2.id_station = S2.id \n" +
            "                        AND S2.name = ?\n" +
            "                        AND RD3.date_end <= (\n" +
            "\t\t\t\t\t\t\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
            "\t\t\t\t\t\t\t\t\t\t)\n" +
            "                                AND S6.name = ?\n" +
            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
            "                        )\n" +
            "                        ORDER BY RD2.date_end\n" +
            "                    ) \n" +
            "                   AND T.number = ?\n" +
            "                   AND TP.name = ?\n" +
            "           GROUP BY S3.name, T.id,CR.num_carriage,ST.num_seat\n" +
            "ORDER BY RD3.date_end\n" +
            ") H\n" +
            "WHERE H.nameStation = ?\n" +
            "ORDER BY idTrain,numCarr";

    public static final String SQL_FIND_FREE_SEATS_BY_TRAIN_AND_CARRIAGE = "SELECT TP.name as nameType, S3.name as nameStation, T.id as idTrain, CR.num_carriage as numCarr, ST.num_seat as numSeat FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
            "WHERE RD3.id_station = S3.id\n" +
            "            AND RD3.id_route = R.id \n" +
            "            AND R.id_train = T.id\n" +
            "\t\t\tAND CR.id_train = R.id_train\n" +
            "            AND CR.id_type = TP.id\n" +
            "            AND ST.num_carriage = CR.num_carriage\n" +
            "            AND ST.id_train = R.id_train\n" +
            "            AND RD3.id_route NOT IN (\n" +
            "\t\t\t\tSELECT US.id_route FROM user_check US\n" +
            "\t\t\t\t\tWHERE US.id_route = RD3.id_route\n" +
            "\t\t\t\t\tAND US.id_train = RD3.id_train\n" +
            "\t\t\t\t\tAND US.num_carriage = CR.num_carriage\n" +
            "\t\t\t\t\tAND US.num_seat = ST.num_seat\n" +
            "\t\t\t\t\tAND US.id_station = RD3.id_station\n" +
            "                    AND US.date_end >= ?\n" +
            "\t\t\t\t\tAND US.date_end <= (\n" +
            "\t\t\t\t\t\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
            "\t\t\t\t\t\t\t\t\t\t)\n" +
            "                                AND S6.name = ?\n" +
            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
            "                    )\n" +
            "\t\t\t)\n" +
            "AND RD3.date_end >= ?\n" +
            "AND RD3.date_end <= (\n" +
            "\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
            "\t\t\t\t\t\t\t\t\t\t)\n" +
            "                                AND S6.name = ?\n" +
            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
            ")\n" +
            "AND T.number = ?\n" +
            "AND CR.num_carriage = ?\n" +
            "AND TP.name = ?\n" +
            "AND S3.name = ?\n" +
            " GROUP BY S3.name, T.id,CR.num_carriage,ST.num_seat";

//    public static final String SQL_FIND_FREE_SEATS_BY_TRAIN_AND_CARRIAGE = "" +
//            "SELECT * FROM (SELECT DISTINCT TP.name as nameType, S3.name as nameStation, T.id as idTrain, CR.num_carriage as numCarr, ST.num_seat as numSeat\n" +
//            "            FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR\n" +
//            "            WHERE RD3.id_station = S3.id\n" +
//            "            AND RD3.id_route = R.id \n" +
//            "            AND R.id_train = T.id\n" +
//            "                    AND CR.id_train = R.id_train\n" +
//            "                    AND CR.id_type = TP.id\n" +
//            "                    AND ST.num_carriage = CR.num_carriage\n" +
//            "                    AND ST.id_train = R.id_train\n" +
//            "                    AND RD3.id_route NOT IN (\n" +
//            "            SELECT US.id_route FROM user_check US\n" +
//            "                        WHERE US.id_route = RD3.id_route\n" +
//            "            AND US.id_train = RD3.id_train\n" +
//            "                            AND US.num_carriage = CR.num_carriage\n" +
//            "                            AND US.num_seat = ST.num_seat\n" +
//            "                            AND US.id_station = RD3.id_station\n" +
//            "                    )\n" +
//            "AND RD3.date_end <=Any (\n" +
//            "            SELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
//            "                        WHERE RD2.id_station = S2.id \n" +
//            "                        AND S2.name = ?\n" +
//            "                        AND RD3.date_end <= (\n" +
//            "\t\t\t\t\t\t\tSELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6\n" +
//            "\t\t\t\t\t\t\t\tWHERE RD6.id_station = S6.id\n" +
//            "\t\t\t\t\t\t\t\tAND RS6.id_station = RD6.id_station\n" +
//            "\t\t\t\t\t\t\t\tAND RS6.id_route = RD6.id_route\n" +
//            "\t\t\t\t\t\t\t\tAND RS6.id_train = RD6.id_train\n" +
//            "\t\t\t\t\t\t\t\tAND RD6.date_end >=All (\n" +
//            "\t\t\t\t\t\t\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
//            "\t\t\t\t\t\t\t\t\t\tWHERE RD2.id_station = S2.id\n" +
//            "\t\t\t\t\t\t\t\t\t\tAND S2.name = ?\n" +
//            "\t\t\t\t\t\t\t\t\t\tAND ? = DATE(RD2.date_end)\n" +
//            "\t\t\t\t\t\t\t\t\t\t)\n" +
//            "                                AND S6.name = ?\n" +
//            "\t\t\t\t\t\t\t\tORDER BY date_end\n" +
//            "\t\t\t\t\t\t\t\tLIMIT 1\n" +
//            "                        )\n" +
//            "                        ORDER BY RD2.date_end\n" +
//            "                    ) \n" +
//            "                   AND T.number = ?\n" +
//            "                   AND CR.num_carriage = ?\n" +
//            "                   AND TP.name = ?\n" +
//            "           GROUP BY S3.name, T.id,CR.num_carriage,ST.num_seat\n" +
//            "ORDER BY RD3.date_end\n" +
//            ") H\n" +
//            "WHERE H.nameStation = ?\n" +
//            "ORDER BY idTrain,numCarr,numSeat";
    public static final String SQL_SELECT_TYPE_PRICE = "SELECT price FROM Types\n" +
            "WHERE name = ?";

    public static final String SQL_SELECT_ALL_CHECK_FOR_USER = "SELECT  DISTINCT initials, id_train, number, num_carriage, num_seat\n" +
            "from user_check UCH, stations S, trains T\n" +
            "WHERE UCH.id_station = S.id \n" +
            "\tAND T.id = UCH.id_train\n" +
            "    AND id_user = ?\n" +
            "ORDER BY id_train, num_carriage, num_seat, date_end ";
    public static final String SQL_SELECT_ALL_STATION_INFO_FOR_CHECK =
            "SELECT  id_train, number, num_carriage, num_seat, date_end, name, (\n" +
                    "\t\tSELECT S2.name FROM stations S2\n" +
                    "        WHERE S2.id = S.id + 1\n" +
                    "\t) as dest  from user_check UCH, stations S, trains T\n" +
                    "WHERE UCH.id_station = S.id \n" +
                    "\tAND T.id = UCH.id_train\n" +
                    "    AND id_user = ?\n" +
                    "    AND id_train = ?\n" +
                    "    AND num_carriage = ?\n" +
                    "    AND num_seat = ?\n" +
                    "ORDER BY id_train, num_carriage, num_seat, date_end ";
    public static final String SQL_DELETE_USER_CHECK = "DELETE FROM user_check \n" +
            "WHERE id_user = ?\n" +
            "    AND id_train = ?\n" +
            "    AND num_carriage = ?\n" +
            "    AND num_seat = ?";
    public static final String SQL_SELECT_COUNT_CARRIAGES_AND_SEATS = "" +
            "SELECT distinct number, (\n" +
            "\tSELECT Count(*) FROM carriages \n" +
            "    WHERE id_train = CR.id_train\n" +
            ") as carriages,\n" +
            " (\n" +
            "\tSELECT Count(*) FROM seats \n" +
            "    WHERE id_train = CR.id_train\n" +
            ") as seats FROM carriages CR, trains T\n" +
            "WHERE CR.id_train = T.id";
}
