package com.example.atividadesqlite.Model;

public class IndigentData {
    String features;
    String gender;
    String disasterId;
    String uuid;
    String rescueDate;

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisasterId() {
        return disasterId;
    }

    public void setDisasterId(String disasterId) {
        this.disasterId = disasterId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRescueDate() {
        return rescueDate;
    }

    public void setRescueDate(String rescueDate) {
        this.rescueDate = rescueDate;
    }

    public IndigentData(String features, String gender, String disasterId, String uuid, String rescueDate) {
        this.features = features;
        this.gender = gender;
        this.disasterId = disasterId;
        this.uuid = uuid;
        this.rescueDate = rescueDate;
    }
}
