package com.weddingpalace.weddingpalaceapplication;

public class ReservationTime {
    private String date;
    private boolean isReserved;

    public ReservationTime(String date, boolean isReserved) {
        this.date = date;
        this.isReserved = isReserved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
