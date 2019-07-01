SELECT * FROM routes_on_date RD3, stations S3, trains T, routes R
	WHERE RD3.id_station = S3.id 
		AND RD3.id_route = R.id 
		AND R.id_train = T.id
		AND '2019-09-18' IN (
			SELECT DATE(RD2.date_end) FROM routes_on_date RD2, stations S2
            WHERE RD2.id_station = S2.id
            AND S2.name = 'Odessa-Holovna'
		)
        AND RD3.date_end <= (
			SELECT RD2.date_end FROM routes_on_date RD2, stations S2
            WHERE RD2.id_station = S2.id
            AND S2.name = 'Poltava-Pivdenna'
        )
        AND id_route = 1
ORDER BY date_end;