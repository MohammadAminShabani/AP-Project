package org.example;

public class City {
    private Long id;
    private String name;
    private String province;

    public City() {
    }

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return name + " (" + province + ")";
    }
}