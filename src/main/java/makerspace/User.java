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
