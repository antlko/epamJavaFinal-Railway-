package com.nure.kozhukhar.railway.db.entity;

/**
 * UserRole entity
 *
 * @author Anatol Kozhukhar
 */
public class UserRole extends Entity {

    private static final long serialVersionUID = -7376000280649629019L;

    private Integer id;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
