SELECT nameType, MIN(freeCnt) as free FROM (
SELECT TP.name as nameType, Count(*) as freeCnt FROM routes_on_date RD3, stations S3, trains T, routes R, seats ST, Types TP, carriages CR
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
					AND US.id_station = RD3.id_station
					AND US.id_train = RD3.id_train
					AND US.num_seat = ST.num_seat
					AND US.num_carriage = ST.num_carriage
					AND US.date_end = RD3.date_end
					
			)
AND RD3.date_end >= '2019-07-18'
AND RD3.date_end <= (
	SELECT RD6.date_end FROM routes_on_date RD6, stations S6, routes_station RS6
								WHERE RD6.id_station = S6.id
								AND RS6.id_station = RD6.id_station
								AND RS6.id_route = RD6.id_route
								AND RS6.id_train = RD6.id_train
								AND RD6.date_end >=All (
									SELECT RD2.date_end FROM routes_on_date RD2, stations S2
										WHERE RD2.id_station = S2.id
										AND S2.name = 'Odessa-Mala'
										AND '2019-07-18' = DATE(RD2.date_end)
										AND RD2.id_route = 3
										)
                                AND S6.name = 'Odessa-Holovna'
								AND RD6.id_route = 3
								ORDER BY date_end
								LIMIT 1
)
AND RD3.id_route = 3
AND S3.name IN ( 
	SELECT ST1.name FROM routes_station S4 INNER JOIN Stations ST1 ON S4.id_station = ST1.id
	WHERE S4.id_route = RD3.id_route
		AND S4.time_end < (
			SELECT S5.time_end FROM routes_station S5 INNER JOIN Stations ST2 ON S5.id_station = ST2.id
			WHERE ST2.name = 'Odessa-Holovna'
				AND S5.id_route = S4.id_route
		)
)
 GROUP BY TP.name, S3.name
 ) H
 GROUP BY nameType