package com.med.meditationsoundapp.SoundModel;

public class SoundModel {
    int SoundMp3Checked;
    int SoundIcon;
    String SoundMp3;
    int SoundPos;

    public SoundModel(int soundIcon, String soundMp3, int soundMp3Checked, int soundPos) {
        SoundIcon = soundIcon;
        SoundMp3 = soundMp3;
        SoundMp3Checked = soundMp3Checked;
        SoundPos = soundPos;
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

    @Override
    public String toString() {
        return "SoundModel{" +
                "SoundMp3Checked=" + SoundMp3Checked +
                ", SoundIcon=" + SoundIcon +
                ", SoundMp3='" + SoundMp3 + '\'' +
                ", SoundPos=" + SoundPos +
                '}';
    }
}
