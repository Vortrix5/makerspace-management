package makerspace;

import java.util.ArrayList;

public class Booking {
    private static int count = 0;
    private int bookingId = 0;
    private User user;
    private String date;
    private String time;
    private int duration;
    private boolean status;

    public Booking(User user, String date, String time, int duration) {
        count += 1;
        this.bookingId += count;
        this.user = user;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.status = false;
    }


    public int getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public boolean getStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


}
