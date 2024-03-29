package com.nure.kozhukhar.railway.db.entity;

/**
 * Seat entity
 *
 * @author Anatol Kozhukhar
 */
public class Seat extends Entity {

    private static final long serialVersionUID = 5696112236552238940L;
    private Integer numCarriage;

    private Integer numSeat;

    private Integer idTrain;

    public Integer getNumCarriage() {
        return numCarriage;
    }

    public void setNumCarriage(Integer numCarriage) {
        this.numCarriage = numCarriage;
    }

    public Integer getNumSeat() {
        return numSeat;
    }

    public void setNumSeat(Integer numSeat) {
        this.numSeat = numSeat;
    }

    public Integer getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(Integer idTrain) {
        this.idTrain = idTrain;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "numCarriage=" + numCarriage +
                ", numSeat=" + numSeat +
                ", idTrain=" + idTrain +
                '}';
    }
}
