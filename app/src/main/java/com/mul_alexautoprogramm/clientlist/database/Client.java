package com.mul_alexautoprogramm.clientlist.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "client_list")
public class Client {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo (name = "name")
    private String name;
    @ColumnInfo (name = "sur_name")
    private String sur_name;
    @ColumnInfo (name = "tel_number")
    private String tel_number;
    @ColumnInfo (name = "importance")
    private int importance;
    @ColumnInfo (name = "description")
    private String description;
    @ColumnInfo (name = "special")
    private int special;


    public Client(int id, String name, String sur_name, String tel_number, int importance, String description, int special) {
        this.id = id;
        this.name = name;
        this.sur_name = sur_name;
        this.tel_number = tel_number;
        this.importance = importance;
        this.description = description;
        this.special = special;
    }

    @Ignore
    public Client(String name, String sur_name, String tel_number, int importance, String description, int special) {
        this.name = name;
        this.sur_name = sur_name;
        this.tel_number = tel_number;
        this.importance = importance;
        this.description = description;
        this.special = special;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }
}
