package com.beans;

import org.codehaus.jackson.annotate.JsonProperty;

public class Data {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("text")
    private String text;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (username != null ? !username.equals(data.username) : data.username != null) return false;
        if (password != null ? !password.equals(data.password) : data.password != null) return false;
        return !(text != null ? !text.equals(data.text) : data.text != null);

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public java.lang.String toString() {
        return "Data{" +
                "username=" + username +
                ", password=" + password +
                ", text=" + text +
                '}';
    }
}
