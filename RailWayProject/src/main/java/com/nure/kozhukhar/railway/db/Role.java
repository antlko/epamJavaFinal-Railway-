package com.nure.kozhukhar.railway.db;

/**
 * Enumeration class user roles
 *
 * @author Anatol Kozhukhar
 */
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");


    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}