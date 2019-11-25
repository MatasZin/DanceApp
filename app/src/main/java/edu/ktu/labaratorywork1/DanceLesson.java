package edu.ktu.labaratorywork1;

import java.sql.Date;
import java.sql.Time;

public class DanceLesson {

    private int iD;
    private String date;
    private String startTime;
    private String endTime;
    private String title;
    private String description;
    private String weekDay;
    private String type;

    public DanceLesson(){}

    public DanceLesson(String title, String startTime, String endTime, String weekDay,
                       String date, String description, String type){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.weekDay = weekDay;
        this.type = type;
    }

    public DanceLesson(int iD, String title, String startTime, String endTime, String weekDay,
                       String date, String description, String type){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.weekDay = weekDay;
        this.iD = iD;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
