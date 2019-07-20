package com.nure.kozhukhar.railway.db.entity;

/**
 * City entity
 *
 * @author Anatol Kozhukhar
 */
public class City extends Entity {

    private static final long serialVersionUID = 5307950332986911281L;

    private Integer id;

    private Integer idCountry;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", idCountry=" + idCountry +
                ", name='" + name + '\'' +
                '}';
    }
}
