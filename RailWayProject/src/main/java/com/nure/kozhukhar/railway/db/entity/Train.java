package com.nure.kozhukhar.railway.db.entity;

/**
 * Train entity
 *
 * @author Anatol Kozhukhar
 */
public class Train extends Entity{

    private static final long serialVersionUID = 5731420053663978810L;
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
