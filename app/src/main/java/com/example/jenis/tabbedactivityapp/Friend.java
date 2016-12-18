package com.example.jenis.tabbedactivityapp;

/**
 * Created by Jenis on 8/8/2016.
 */
public class Friend {
    public String name;
    public String link;
    public int points;

    public Friend(){
    }
    public Friend(String link, int point){
        this.link = link;
        this.points = point;
    }
    public String getName(){return name;}
    public int getPoints(){return points;}
    public String getLink(){return link;}
    public void setName(String name){this.name = name;}
    public void setPoints(int points){this.points = points;}
    public void setLink(String link){this.link = link;}
}
