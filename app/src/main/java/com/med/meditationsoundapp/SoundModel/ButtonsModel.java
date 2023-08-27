package com.med.meditationsoundapp.SoundModel;

public class ButtonsModel {
    int Buttons,selected;

    public ButtonsModel(int buttons, int selected) {
        Buttons = buttons;
        this.selected = selected;
    }

    public int getButtons() {
        return Buttons;
    }

    public void setButtons(int buttons) {
        Buttons = buttons;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ButtonsModel{" +
                "Buttons=" + Buttons +
                ", selected=" + selected +
                '}';
    }
}
