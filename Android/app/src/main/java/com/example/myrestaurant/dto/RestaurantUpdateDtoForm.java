package com.example.myrestaurant.dto;

public class RestaurantUpdateDtoForm {
    private String name;
    private String location;
    private String link;
    private String category;

    public RestaurantUpdateDtoForm(String name, String location, String link, String category) {
        this.name = name;
        this.location = location;
        this.link = link;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
