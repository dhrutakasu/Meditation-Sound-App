package com.med.meditationsoundapp.SoundModel;

import java.util.ArrayList;

public class FavModel {
    String Name;
    ArrayList<PlyerModel> model;

    public FavModel(String name, ArrayList<PlyerModel> model) {
        Name = name;
        this.model = model;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<PlyerModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<PlyerModel> model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "FavModel{" +
                "Name='" + Name + '\'' +
                ", model=" + model +
                '}';
    }
}
