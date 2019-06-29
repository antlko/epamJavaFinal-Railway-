package com.nure.kozhukhar.railway.db;

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