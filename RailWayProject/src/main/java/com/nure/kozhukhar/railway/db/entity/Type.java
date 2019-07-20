package com.nure.kozhukhar.railway.db.entity;


/**
 * Type entity
 *
 * @author Anatol Kozhukhar
 */
public class Type extends Entity {

    private static final long serialVersionUID = 810496944417092750L;

    private Integer id;

    private String name;

    private Integer price;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
