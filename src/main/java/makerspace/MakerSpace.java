/*
 * Copyright (c) 2023 Amine Zouaoui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package makerspace;

import java.util.ArrayList;

public class MakerSpace {

    public static ArrayList<Component> components= new ArrayList<Component>();
    private static ArrayList<User> users= new ArrayList<User>();
    private static ArrayList<Booking> bookings= new ArrayList<Booking>();
    private static User activeUser;




    public static void addComponent(String name, int quantity) {
        Component component = new Component(name, quantity);
        components.add(component);
    }
    public static void createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        users.add(user);
    }
    public static void createSuperUser(String name, String email, String password) {
        SuperUser superUser = new SuperUser(name, email, password);
        users.add(superUser);
    }
    public static void addBooking(User user, String date, String time, int duration) {
        Booking booking = new Booking(user, date, time, duration);
        bookings.add(booking);
    }
    public static void setActiveUser(User user){
        activeUser = user;
    }

    public static ArrayList<Component> getComponents() {
        return components;
    }
    public static ArrayList<User> getUsers() {
        return users;
    }
    public static ArrayList<SuperUser> getSuperUsers() {
        ArrayList<SuperUser> superUsers = new ArrayList<SuperUser>();
        for (User user : users) {
            if (user instanceof SuperUser) {
                superUsers.add((SuperUser) user);
            }
        }
        return superUsers;
    }
    public static ArrayList<Booking> getBookings() {
        return bookings;
    }
    public static Booking getBookingById(int id){
        for (Booking booking : bookings) {
            if (booking.getBookingId() == id) {
                return booking;
            }
        }
        return null;
    }
    public static ArrayList<Booking> getPendingBookings(){
        ArrayList<Booking> pendingBookings = new ArrayList<Booking>();
        for (Booking booking : bookings) {
            if (booking.getStatus()==false) {
                pendingBookings.add(booking);
            }
        }
        return pendingBookings;
    }
    public static User getActiveUser(){
        return activeUser;
    }
    public static User getUserById(int id){
        for (User user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }
    public static User getUserByEmail(String email){
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public static User getUserByUsername(String name){
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
    public static Component getComponentByName(String name){
        for (Component component : components) {
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }
}
