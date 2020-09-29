package ru.myt.core.entity;

public class Route {
/*<!--get codes of station from here https://rasp.yandex.ru/thread/6273_2_9601681_g16_4?tt=suburban&station_from=2000006&departure=2016-05-25&station_to=9601338&point_from=s2000006&point_to=s9601338-->*/
    private String from;
    private String to;
    private int priority;

    public Route (String from, String to, int priority){
        this.from = from;
        this.to = to;
        this.priority = priority;
    }

    public String getFrom() {
        return from;
    }

    protected void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    protected void setTo(String to) {
        this.to = to;
    }

    public int getPriority() {
        return priority;
    }

    protected void setPriority(int priority) {
        this.priority = priority;
    }


}
