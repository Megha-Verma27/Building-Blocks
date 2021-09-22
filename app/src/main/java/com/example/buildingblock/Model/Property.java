package com.example.buildingblock.Model;

public class Property {
    private String ptype, description, price, image, category, pid, date, time;

    public Property()
    {}

    public Property(String ptype, String description, String price, String image, String category, String pid, String date, String time)
    {
        this.ptype = ptype;
        this.category = category;
        this.price = price;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.time = time;
        this.date = date;

    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
