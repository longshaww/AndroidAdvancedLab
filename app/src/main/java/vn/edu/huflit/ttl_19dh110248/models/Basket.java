package vn.edu.huflit.ttl_19dh110248.models;

import java.io.Serializable;
import java.util.HashMap;

public class Basket implements Serializable {
    public HashMap<String, vn.edu.huflit.ttl_19dh110248.models.FoodBasket> foods;
    public double totalPrice;
    public int totalItem;

    public Basket() {
        foods = new HashMap<>();
        totalPrice = 0;
        totalItem = 0;
    }


    public void addFood(vn.edu.huflit.ttl_19dh110248.models.FoodBasket food) {
        foods.put(food.getFoodKey(), food);
    }

    public vn.edu.huflit.ttl_19dh110248.models.FoodBasket getFood(String key) {
        return foods.get(key);
    }

    public void calculateBasket() {
        totalPrice = 0;
        totalItem = 0;
        for (vn.edu.huflit.ttl_19dh110248.models.FoodBasket foodBasket : foods.values()) {
            totalPrice += (foodBasket.price * foodBasket.quantity);
            totalItem += foodBasket.quantity;
        }
    }

    public String getTotalPrice() {
        return totalPrice + " VND";
    }

    public int getTotalItem() {
        return totalItem;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "foods=" + foods +
                ", totalPrice=" + totalPrice +
                ", totalItem=" + totalItem +
                '}';
    }
}
