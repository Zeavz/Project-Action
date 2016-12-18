package com.example.jenis.tabbedactivityapp;

/**
 * Created by Jenis on 8/23/2016.
 */
public class Game {
    private String GameID, creation, linkToVideo, opened, reciever, sender, word;
    private int score;

    public Game(){}
    public Game(String creation, String linkToVideo, String opened, String reciever, int score, String sender, String word){
        this.creation = creation;
        this.linkToVideo = linkToVideo;
        this.opened = opened;
        this.reciever = reciever;
        this.score = score;
        this.sender = sender;
        this.word = word;
    }

    public String getCreation(){return creation;}
    public String getLinkToVideo(){return linkToVideo;}
    public String getOpened(){return opened;}
    public String getReciever(){return reciever;}
    public int getScore(){return score;}
    public String getSender(){return sender;}
    public String getWord(){return word;}
    public String getGameID(){return GameID;}

    public void setCreation(String creation){this.creation = creation;}
    public void setLinkToVideo(String linkToVideo){this.linkToVideo = linkToVideo;}
    public void setOpened(String opened){this.opened = opened;}
    public void setReciever(String reciever){this.reciever = reciever;}
    public void setScore(int score){this.score = score;}
    public void setSender(String sender){this.sender = sender;}
    public void setWord(String word){this.word = word;}
    public void setGameID(String gameID){this.GameID = gameID;}
}
