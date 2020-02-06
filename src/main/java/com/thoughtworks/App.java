package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    String[] order = selectedItems.split(",");
    Food[] foodArr = listOrder(order);
    double originalPrice = getOriginalPrice(foodArr);
    double discountedPrice1 = getDiscountedPrice1(originalPrice);
    double discountedPrice2 = getDiscountedPrice2(foodArr);
    double bestPrice = originalPrice;
    String bestDiscount = "";
    if (discountedPrice1 < bestPrice || discountedPrice2 < bestPrice) {
      if (discountedPrice1 <= discountedPrice2) {
        bestPrice = discountedPrice1;
        bestDiscount = "满30减6元";
      } else {
        bestPrice = discountedPrice2;
        bestDiscount = "指定菜品半价(黄焖鸡，凉皮)";
      }
    }
    String summary = printReceipt(foodArr, originalPrice, bestPrice, bestDiscount);
    return summary;
  }

  public static Food[] listOrder(String[] order) {
    Food[] foodArr = new Food[order.length];
    for (int i = 0; i < order.length; i++) {
      String itemId = order[i].split(" x ")[0];
      String count = order[i].split(" x ")[1];
      int index = Arrays.binarySearch(App.getItemIds(), itemId);
      foodArr[i] = new Food(index, count);
    }
    return foodArr;
  }

  public static double getOriginalPrice(Food[] arr) {
    double price = 0;
    for (Food x: arr) {
      price += x.getFoodPrice();
    }
    return price;
  }

  //满30减6
  public static double getDiscountedPrice1(double p) {
    double price = p;
    if (price >= 30) {
      price = price - 6;
    }
    return price;
  }

  //指定商品半价
  public static double getDiscountedPrice2(Food[] arr) {
    double price = 0;
    for (Food x: arr) {
      if (Arrays.binarySearch(App.getHalfPriceIds(), x.id) >=0) {
        price += x.getFoodPrice() / 2;
      } else {
        price += x.getFoodPrice();
      }
    }
    return price;
  }

  public static String printReceipt(Food[] arr, double oPrice, double bestPrice, String bestDiscount) {
    int total = (int)bestPrice;
    String str = "============= 订餐明细 =============\n";
    for (Food x: arr) {
      int foodPrice = (int)x.getFoodPrice();
      str += x.name + " x " + x.count + " = " + foodPrice + "元\n";
    }
    if (bestPrice < oPrice) {
      int save =(int)(oPrice - bestPrice);
      str += "-----------------------------------\n使用优惠:\n" + bestDiscount + "，省" + save + "元\n";
    }
    str += "-----------------------------------\n总计：" + total + "元\n===================================";
    return str;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
