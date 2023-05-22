package makerspace;

import java.util.ArrayList;

public class Component {
    private static int count=0;
    private int componentId=0;
    private String name;
    private int quantity;

    public Component(String name, int quantity) {
        count+=1;
        this.componentId+=count;
        this.name = name;
        this.quantity = quantity;
    }

    public int getComponentId() {
        return componentId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }




}
