package edu.andrews.cptr252.leviwalker.jsonapp;

public class Animal {
    private String common_name;
    private String scientific_name;
    private String gender;
    private int weight;
    private int id;
    private int age;

    public Animal(String common_name, String scientific_name, String gender, int weight, int id, int age) {
        this.common_name = common_name;
        this.scientific_name = scientific_name;
        this.gender = gender;
        this.weight = weight;
        this.id = id;
        this.age = age;
    }

    public String getCommon_name() {
        return common_name;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public String getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }
}
