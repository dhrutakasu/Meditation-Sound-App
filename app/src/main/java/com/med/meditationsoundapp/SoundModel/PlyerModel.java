package com.med.meditationsoundapp.SoundModel;

import android.media.MediaPlayer;

public class PlyerModel {
    MediaPlayer player;
    int PlayerPos;
    String PlayerName;

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public int getPlayerPos() {
        return PlayerPos;
    }

    public void setPlayerPos(int playerPos) {
        PlayerPos = playerPos;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }
}