package com.weddingpalace.weddingpalaceapplication;

import com.google.gson.annotations.SerializedName;

import java.lang.String;

import java.io.Serializable;
import java.util.List;

public class Hall implements Serializable {

    private int id;
    private String image;
    private String name;
    @SerializedName("hall_type")
    private HallType hallType;
    @SerializedName("place_type")
    private PlaceType placeType;
    private String location;
    private String region;
    private int rating;
    private String capacity;
    @SerializedName("reservation_type")
    private ReservationType reservationType;
    private int price;
    private List<ReservationTime> reservationTimes;

    public Hall(String image, String name, HallType hallType, String location, ReservationType reservationType, int price) {
        this.image = image;
        this.name = name;
        this.hallType = hallType;
        this.location = location;
        this.reservationType = reservationType;
        this.price = price;
    }

    public Hall(int id, String image, String name, HallType hallType, PlaceType placeType, String location, String region, int rating, String capacity, ReservationType reservationType, int price, List<ReservationTime> reservationTimes) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.hallType = hallType;
        this.placeType = placeType;
        this.location = location;
        this.region = region;
        this.rating = rating;
        this.capacity = capacity;
        this.reservationType = reservationType;
        this.price = price;
        this.reservationTimes = reservationTimes;
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

    public HallType getHallType() {
        return hallType;
    }

    public void setHallType(HallType hallType) {
        this.hallType = hallType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimes;
    }

    public void setReservationTimes(List<ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
