package com.nure.kozhukhar.railway.db.entity;

/**
 * Carriage entity
 *
 * @author Anatol Kozhukhar
 */
public class Carriage extends Entity {

    private static final long serialVersionUID = 478960032870699341L;

    private Integer numCarriage;

    private Integer idTrain;

    private String idType;

    public Integer getNumCarriage() {
        return numCarriage;
    }

    public void setNumCarriage(Integer numCarriage) {
        this.numCarriage = numCarriage;
    }

    public Integer getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(Integer idTrain) {
        this.idTrain = idTrain;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public String toString() {
        return "Carriage{" +
                "numCarriage=" + numCarriage +
                ", idTrain=" + idTrain +
                ", idType='" + idType + '\'' +
                '}';
    }
}
