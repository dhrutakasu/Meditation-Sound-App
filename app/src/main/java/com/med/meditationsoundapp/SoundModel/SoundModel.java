package com.med.meditationsoundapp.SoundModel;

public class SoundModel {
    int SoundMp3Checked;
    int SoundIcon;
    String SoundMp3,SoundTitle;
    int SoundPos;
    int SoundVolume;

    public SoundModel(int soundIcon, String soundMp3, String soundTitle, int soundMp3Checked, int soundPos, int soundVolume) {
        SoundIcon = soundIcon;
        SoundMp3 = soundMp3;
        SoundTitle = soundTitle;
        SoundMp3Checked = soundMp3Checked;
        SoundPos = soundPos;
        SoundVolume = soundVolume;
    }

    public int getSoundIcon() {
        return SoundIcon;
    }

    public void setSoundIcon(int soundIcon) {
        SoundIcon = soundIcon;
    }

    public String getSoundMp3() {
        return SoundMp3;
    }

    public void setSoundMp3(String soundMp3) {
        SoundMp3 = soundMp3;
    }

    public String getSoundTitle() {
        return SoundTitle;
    }

    public void setSoundTitle(String soundTitle) {
        SoundTitle = soundTitle;
    }

    public int getSoundMp3Checked() {
        return SoundMp3Checked;
    }

    public void setSoundMp3Checked(int soundMp3Checked) {
        SoundMp3Checked = soundMp3Checked;
    }

    public int getSoundPos() {
        return SoundPos;
    }

    public void setSoundPos(int soundPos) {
        SoundPos = soundPos;
    }

    public int getSoundVolume() {
        return SoundVolume;
    }

    public void setSoundVolume(int soundVolume) {
        SoundVolume = soundVolume;
    }

    @Override
    public String toString() {
        return "SoundModel{" +
                "SoundMp3Checked=" + SoundMp3Checked +
                ", SoundIcon=" + SoundIcon +
                ", SoundMp3='" + SoundMp3 + '\'' +
                ", SoundTitle='" + SoundTitle + '\'' +
                ", SoundPos=" + SoundPos +
                ", SoundVolume=" + SoundVolume +
                '}';
    }
}
