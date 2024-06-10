package com.example.atividadesqlite.Model;

public class UserData {
    private String id;
    private String name;
    private String cpf;
    private String phone;
    private String userRef;

    public UserData(String id, String name, String cpf, String phone, String userRef) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.userRef = userRef;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserRef() {
        return userRef;
    }

    public void setUserRef(String userRef) {
        this.userRef = userRef;
    }
}
