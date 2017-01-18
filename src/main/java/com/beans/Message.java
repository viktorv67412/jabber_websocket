package com.beans;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Message {

    @JsonProperty("type")
    private String type;

    @JsonProperty("data")
    private Data data;

    @JsonProperty("roster")
    private List<String> roster;

    public Message(String type, Data data, List<String> roster) {
        this.type = type;
        this.data = data;
        this.roster = roster;
    }

    public Message(String type) {
        this.type = type;
    }

    public Message(List<String> roster, String type) {
        this.roster = roster;
        this.type = type;
    }

    public Message() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<String> getRoster() {
        return roster;
    }

    public void setRoster(List<String> roster) {
        this.roster = roster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (type != null ? !type.equals(message.type) : message.type != null) return false;
        if (data != null ? !data.equals(message.data) : message.data != null) return false;
        return !(roster != null ? !roster.equals(message.roster) : message.roster != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (roster != null ? roster.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", data=" + data +
                ", roster=" + roster +
                '}';
    }
}
