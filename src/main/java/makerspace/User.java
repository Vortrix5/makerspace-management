package makerspace;

import java.util.ArrayList;

public class User {
    private static int count=0;
    private int userId=0;
    private String name;
    private String email;
    private String password;
    private ArrayList<Booking> bookings=new ArrayList<Booking>();
    private ArrayList<Component> components=new ArrayList<Component>();

    public User(String name, String email, String password) {
        count+=1;
        this.userId+=count;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void borrowComponent(Component component,int quantity) {
        if(component.getQuantity()-quantity < 0) {
            System.out.println("Component is not available");
        }else{
            component.setQuantity(component.getQuantity() - quantity);
            this.components.add(component);
        }
    }
    public void bookSpace(Booking booking) {
        this.bookings.add(booking);
    }
    public int getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public ArrayList<Booking> getBookings() {
        return bookings;
    }
    public ArrayList<Component> getComponents() {
        return components;
    }


}
