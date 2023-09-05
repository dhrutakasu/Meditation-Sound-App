package com.med.meditationsoundapp.SoundModel;

import java.util.ArrayList;

public class FavModel {
    String Name,id;
   int PlayerPos,PlayerVol;

    public FavModel(String name, String id, int playerPos, int playerVol) {
        Name = name;
        this.id = id;
        PlayerPos = playerPos;
        PlayerVol = playerVol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPlayerPos() {
        return PlayerPos;
    }

    public void setPlayerPos(int playerPos) {
        PlayerPos = playerPos;
    }

    public int getPlayerVol() {
        return PlayerVol;
    }

    public void setPlayerVol(int playerVol) {
        PlayerVol = playerVol;
    }

    @Override
    public String toString() {
        return "FavModel{" +
                "Name='" + Name + '\'' +
                ", id='" + id + '\'' +
                ", PlayerPos=" + PlayerPos +
                ", PlayerVol=" + PlayerVol +
                '}';
    }
}
