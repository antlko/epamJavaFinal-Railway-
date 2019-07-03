SELECT * FROM (SELECT DISTINCT TP.name as nameType, S3.name as nameStation, T.id as idTrain, CR.num_carriage as numCarr
            FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR
            WHERE RD3.id_station = S3.id
            AND RD3.id_route = R.id 
            AND R.id_train = T.id
                    AND CR.id_train = R.id_train
                    AND CR.id_type = TP.id
                    AND ST.num_carriage = CR.num_carriage
                    AND ST.id_train = R.id_train
                    AND RD3.id_route NOT IN (
            SELECT US.id_route FROM user_check US
                        WHERE US.id_route = RD3.id_route
            AND US.id_train = RD3.id_train
                            AND US.num_carriage = CR.num_carriage
                            AND US.num_seat = ST.num_seat
                            AND US.id_station = RD3.id_station
                    )
			AND RD3.date_end >= (
            SELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2
                        WHERE RD2.id_station = S2.id
                        AND S2.name = 'Odessa-Holovna'
                        AND '2019-09-18' = DATE(RD2.date_end)
            )
                    AND RD3.date_end <= (
            SELECT RD2.date_end FROM routes_on_date RD2, stations S2
                        WHERE RD2.id_station = S2.id
                        AND S2.name = 'Kharkiv'
                    )
                   AND T.number = 263
                   AND TP.name = 'common'
           GROUP BY S3.name, T.id,CR.num_carriage,ST.num_seat
ORDER BY RD3.date_end
) H
WHERE H.nameStation = 'Odessa-Holovna'
ORDER BY idTrain,numCarr