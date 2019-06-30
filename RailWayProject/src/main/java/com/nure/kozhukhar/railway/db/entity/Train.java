package com.nure.kozhukhar.railway.db.entity;

public class Train extends Entity{

    private Integer id;

    private Integer number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}
