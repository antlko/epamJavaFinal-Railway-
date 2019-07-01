package com.nure.kozhukhar.railway.db.bean;

import com.nure.kozhukhar.railway.db.entity.Seat;

import java.util.List;

public class SeatSearchBean {

    private String seatType;

    private Integer free;

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    @Override
    public String toString() {
        return "SeatSearchBean{" +
                "seatType=" + seatType +
                ", free=" + free +
                '}';
    }
}
