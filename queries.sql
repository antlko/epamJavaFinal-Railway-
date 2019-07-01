SELECT * FROM routes_on_date RD3, stations S3
	WHERE RD3.id_station = S3.id
            AND RD3.date_end >= (
            SELECT RD2.date_end FROM routes_on_date RD2, stations S2
                        WHERE RD2.id_station = S2.id
                        AND S2.name = 'Odessa-Mala'
                        AND '2019-09-18' = DATE(RD2.date_end)
                        )
                    AND RD3.date_end <= (
            SELECT RD2.date_end FROM routes_on_date RD2, stations S2
                        WHERE RD2.id_station = S2.id 
                        AND S2.name = 'Poltava-Pivdenna'
                    ) 
                    AND RD3.id_route = 1
ORDER BY date_end;