package com.weddingpalace.weddingpalaceapplication;

import android.location.Location;

import java.io.Serializable;
import java.util.List;

public class Hall implements Serializable {

    private int id;
    private String imageUrl;
    private String name;
    private HallType hallType;
    private Location location;
    private ReservationType reservationType;
    private double price;
    private List<ReservationTime> reservationTimes;

    public Hall(String imageUrl, String name, HallType hallType, Location location, ReservationType reservationType, double price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.hallType = hallType;
        this.location = location;
        this.reservationType = reservationType;
        this.price = price;
    }

    public Hall(int id, String imageUrl, String name, HallType hallType, Location location, ReservationType reservationType, double price) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.hallType = hallType;
        this.location = location;
        this.reservationType = reservationType;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimes;
    }

    public void setReservationTimes(List<ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }
}
