package com.nure.kozhukhar.railway.db.bean;

public class UserStatisticBean {

    private String login;

    private String countTicket;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCountTicket() {
        return countTicket;
    }

    public void setCountTicket(String countTicket) {
        this.countTicket = countTicket;
    }

    @Override
    public String toString() {
        return "UserStatisticBean{" +
                "login='" + login + '\'' +
                ", countTicket='" + countTicket + '\'' +
                '}';
    }
}
