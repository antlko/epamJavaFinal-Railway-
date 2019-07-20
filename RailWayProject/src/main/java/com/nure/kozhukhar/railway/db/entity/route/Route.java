package com.nure.kozhukhar.railway.db.entity.route;

import com.nure.kozhukhar.railway.db.entity.Entity;
import com.nure.kozhukhar.railway.db.entity.Train;

/**
 * Route entity
 *
 * @author Anatol Kozhukhar
 */
public class Route extends Entity {

    private static final long serialVersionUID = 7340263932335202016L;

    private Integer id;

    private Train train;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", train=" + train +
                '}';
    }
}
