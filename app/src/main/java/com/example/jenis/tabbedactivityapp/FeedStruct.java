package com.example.jenis.tabbedactivityapp;

import android.net.Uri;

/**
 * Created by Jenis on 8/15/2016.
 */
public class FeedStruct {
    public String sender, reciever, creationDate, openedDate, gameWord;
    public Uri videoLink;
    public int score;

    public FeedStruct(){
    }
    public FeedStruct(String creationDate, Uri linkToVideo, String openedDate, String recieverUsername, int score, String senderUsername, String gameWord){
        this.creationDate = creationDate;
        this.videoLink = linkToVideo;
        this.openedDate = openedDate;
        this.reciever = recieverUsername;
        this.score = score;
        this.sender = senderUsername;
        this.gameWord = gameWord;
    }
    public String getCreationDate(){return creationDate;}
    public Uri getVideoLink(){return videoLink;}
    public String getOpenedDate(){return openedDate;}
    public String getReciever(){return reciever;}
    public String getSender(){return sender;}
    public int getScore(){return score;}
    public String getGameWord(){return gameWord;}

    public void setCreationDate(String creationDate){
        this.creationDate = creationDate;
    }
    public void setVideoLink(Uri videoLink){
        this.videoLink = videoLink;
    }
    public void setOpenedDate(String openedDate){
        this.openedDate = openedDate;
    }
    public void setReciever(String reciever){
        this.reciever = reciever;
    }
    public void setSender(String sender){
        this.sender = sender;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void setGameWord(String word){this.gameWord = word;}

}
