package com.example.atividadesqlite.Model;

public class DisasterData {
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    String uuid;
    String type;

    public DisasterData(String uuid, String type, String local) {
        this.uuid = uuid;
        this.type = type;
        this.local = local;
    }

    String local;

}
