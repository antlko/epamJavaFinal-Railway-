package com.nure.kozhukhar.railway.db.entity;

import java.sql.Date;

public class Station extends Entity{

    private Integer id;

    private Integer idCity;

    private String name;

    private Date dateStart;

    private Date dateEnd;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
public String toString() {
        return "Station{" +
                "id=" + id +
                ", idCity=" + idCity +
                ", name='" + name + '\'' +
                '}';
    }
}
