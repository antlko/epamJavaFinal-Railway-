package com.nure.kozhukhar.railway.db.entity;

/**
 * Country entity
 *
 * @author Anatol Kozhukhar
 */

public class Country extends Entity {

    private static final long serialVersionUID = -1354503420470673218L;
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
