package com.nure.kozhukhar.railway.db.entity;

import java.sql.Date;
import java.time.LocalDateTime;


/**
 * Station entity
 *
 * @author Anatol Kozhukhar
 */
public class Station extends Entity {

    private static final long serialVersionUID = 2853786184111697342L;
    private Integer id;

    private Integer idCity;

    private String name;

    private LocalDateTime dateStart;

    private Integer price;

    private LocalDateTime dateEnd;


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

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

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", idCity=" + idCity +
                ", name='" + name + '\'' +
                ", dateStart=" + dateStart +
                ", price=" + price +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
