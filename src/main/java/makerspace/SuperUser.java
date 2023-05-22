package makerspace;

import java.util.ArrayList;

public class SuperUser extends User {
    public SuperUser(String name, String email, String password) {

        super(name, email, password);
    }

    public void approveBooking(Booking booking) {
        booking.setStatus(true);
    }

}
