package com.example.myrestaurant.dto;

public class RestaurantEnrollForm {
    private String name;
    private String location;
    private String link;
    private String category;
    private String userEmail;

    public RestaurantEnrollForm(String name, String location, String link, String category, String userEmail) {
        this.name = name;
        this.location = location;
        this.link = link;
        this.category = category;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
