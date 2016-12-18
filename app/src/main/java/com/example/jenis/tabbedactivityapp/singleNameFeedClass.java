package com.example.jenis.tabbedactivityapp;

/**
 * Created by Jenis on 8/26/2016.
 */
public class singleNameFeedClass {
    public String name, gameId, sentOrRecieved;

    public singleNameFeedClass(){

    }
    public singleNameFeedClass(String name){
        this.name = name;
        //this.gameId = id;
    }
    public String getName(){return name;}
    public String getGameId(){return gameId;}
    public String getSentOrRecieved(){return sentOrRecieved;}
    public void setName(String name){this.name = name;}
    public void setGameId(String id){this.gameId = id;}
    public void setSentOrRecieved(String sentOrRecieved){this.sentOrRecieved = sentOrRecieved;}
}
