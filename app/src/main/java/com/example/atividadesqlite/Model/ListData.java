package com.example.atividadesqlite.Model;

//Criação da ListData para tratar os dados que vão ser exibidos
public class ListData {
    public String name;
    public String doc;
    public String condition;
    public String disaster_id;
    public String feature;
    public  String id;
    public String identification_status;
    public String rescue_date;

    public ListData(String name, String doc, String condition, String disaster_id, String feature, String id, String identification_status, String rescue_date) {
        this.name = name;
        this.doc = doc;
        this.condition = condition;
        this.disaster_id = disaster_id;
        this.feature = feature;
        this.id = id;
        this.identification_status = identification_status;
        this.rescue_date = rescue_date;
    }
}
