package com.med.meditationsoundapp.SoundModel;

public class SoundModel {
    int SoundIcon,SoundMp3;

    public SoundModel(int soundIcon, int soundMp3) {
        SoundIcon = soundIcon;
        SoundMp3 = soundMp3;
    }

    public int getSoundIcon() {
        return SoundIcon;
    }

    public void setSoundIcon(int soundIcon) {
        SoundIcon = soundIcon;
    }

    public int getSoundMp3() {
        return SoundMp3;
    }

    public void setSoundMp3(int soundMp3) {
        SoundMp3 = soundMp3;
    }

    @Override
    public String toString() {
        return "SoundModel{" +
                "SoundIcon=" + SoundIcon +
                ", SoundMp3=" + SoundMp3 +
                '}';
    }
}