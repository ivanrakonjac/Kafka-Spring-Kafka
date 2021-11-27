package com.counting_mechanism;

import java.util.Objects;

public class Minute {

    private int minute;
    private int houres;
    private int date;
    private int month;
    private int year;

    public Minute(int minute, int houres, int date, int month, int year) {
        this.minute = minute;
        this.houres = houres;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Minute minute1 = (Minute) o;
        return minute == minute1.minute &&
                houres == minute1.houres &&
                date == minute1.date &&
                month == minute1.month &&
                year == minute1.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minute, houres, date, month, year);
    }

    @Override
    public String toString() {
        return houres + ":" + minute + " | " +
               date + "." + month + "." + year + ".";
    }
}
