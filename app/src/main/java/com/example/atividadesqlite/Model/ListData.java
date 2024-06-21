package com.example.atividadesqlite.Model;

//Criação da ListData para tratar os dados que vão ser exibidos
public class ListData {
    public String name;
    public String doc;
    public String condition;
    public String disasterId;
    public String feature;
    public  String id;
    public String identification_status;
    public String rescueDate;
    public String doc_img;
    public String identifiedBy;

    public ListData(String name, String doc, String condition, String disasterId, String feature, String id, String identification_status, String rescueDate, String doc_img, String identifiedBy) {
        this.name = name;
        this.doc = doc;
        this.condition = condition;
        this.disasterId = disasterId;
        this.feature = feature;
        this.id = id;
        this.identification_status = identification_status;
        this.rescueDate = rescueDate;
        this.doc_img = doc_img;
        this.identifiedBy = identifiedBy;
    }
}
