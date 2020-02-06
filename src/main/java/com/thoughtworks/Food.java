package com.thoughtworks;

public class Food {
  public String name;
  public String id;
  public int count;
  public double price;

  public Food(int i, String c) {
    name = App.getItemNames()[i];
    id = App.getItemIds()[i];
    count = Integer.parseInt(c);
    price = App.getItemPrices()[i];
  }

  public double getFoodPrice() {
    return count * price;
  }
}
