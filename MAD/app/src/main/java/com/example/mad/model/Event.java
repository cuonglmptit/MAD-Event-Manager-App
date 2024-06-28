package com.example.mad.model;

import java.io.Serializable;
import java.sql.Date;

public class Event implements Serializable {

    private int id;
    private User user;
    private String name;
    private String startTime;
    private String endTime;
    private String location;
    private String address;
    private String city;
    private String description;
    private String eventVideo;
    private String registrationType;
    private String websiteLink;
    private String imageUrl;
    private Date startDate;
    private Date endDate;

    // Constructors, getters, and setters

    public Event(){
    }

    public Event(int id, User user, String name, String startTime, String endTime, String location, String address, String city, String description, String eventVideo, String registrationType, String websiteLink, String imageUrl, Date startDate, Date endDate) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.address = address;
        this.city = city;
        this.description = description;
        this.eventVideo = eventVideo;
        this.registrationType = registrationType;
        this.websiteLink = websiteLink;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventVideo() {
        return eventVideo;
    }

    public void setEventVideo(String eventVideo) {
        this.eventVideo = eventVideo;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", eventVideo='" + eventVideo + '\'' +
                ", registrationType='" + registrationType + '\'' +
                ", websiteLink='" + websiteLink + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
