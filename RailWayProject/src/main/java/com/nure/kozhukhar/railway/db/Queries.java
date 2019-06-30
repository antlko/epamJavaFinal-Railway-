package com.nure.kozhukhar.railway.db;

public class Queries {

    public static final String SQL_FIND_ROUTE_ON_DATE_AND_CITIES = "SELECT * FROM routes_on_date RD3, stations S3\n" +
            "\tWHERE RD3.id_station = S3.id\n" +
            "\t\tAND ? IN (\n" +
            "\t\t\tSELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2\n" +
            "            WHERE RD2.id_station = S2.id\n" +
            "            AND S2.name = ?\n" +
            "\t\t)\n" +
            "        AND RD3.date_end <= (\n" +
            "\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "            WHERE RD2.id_station = S2.id\n" +
            "            AND S2.name = ?\n" +
            "        )\n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_ROUTE_ON_DATE_BY_ROUTE_ID = "SELECT * FROM routes_on_date RD3, stations S3\n" +
            "\tWHERE RD3.id_station = S3.id\n" +
            "\t\tAND ? IN (\n" +
            "\t\t\tSELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2\n" +
            "            WHERE RD2.id_station = S2.id\n" +
            "            AND S2.name = ?\n" +
            "\t\t)\n" +
            "        AND RD3.date_end <= (\n" +
            "\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "            WHERE RD2.id_station = S2.id\n" +
            "            AND S2.name = ?\n" +
            "        )\n" +
            "        AND id_route = ?\n" +
            "ORDER BY date_end;";

    public static final String SQL_FIND_ROUTE_ON_DATE_ID = "SELECT DISTINCT RD3.id_route, T.number, T.id FROM routes_on_date RD3, stations S3, trains T, routes R\n" +
            "\tWHERE RD3.id_station = S3.id \n" +
            "\t\tAND RD3.id_route = R.id \n" +
            "\t\tAND R.id_train = T.id\n" +
            "\t\tAND ? IN (\n" +
            "\t\t\tSELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2\n" +
            "            WHERE RD2.id_station = S2.id\n" +
            "            AND S2.name = ?\n" +
            "\t\t)\n" +
            "        AND RD3.date_end <= (\n" +
            "\t\t\tSELECT RD2.date_end FROM routes_on_date RD2, stations S2\n" +
            "            WHERE RD2.id_station = S2.id\n" +
            "            AND S2.name = ?\n" +
            "        )\n" +
            " ORDER BY date_end;";
}
