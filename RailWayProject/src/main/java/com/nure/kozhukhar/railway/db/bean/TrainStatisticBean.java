package com.nure.kozhukhar.railway.db.bean;

public class TrainStatisticBean {

    private Integer trainNumber;

    private Integer countCarriages;

    private Integer countSeats;


    public Integer getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Integer trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Integer getCountCarriages() {
        return countCarriages;
    }

    public void setCountCarriages(Integer countCarriages) {
        this.countCarriages = countCarriages;
    }

    public Integer getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(Integer countSeats) {
        this.countSeats = countSeats;
    }

    @Override
    public String toString() {
        return "TrainStatisticBean{" +
                "trainNumber=" + trainNumber +
                ", countCarriages=" + countCarriages +
                ", countSeats=" + countSeats +
                '}';
    }
}
