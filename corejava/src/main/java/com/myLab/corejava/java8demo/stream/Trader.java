package com.myLab.corejava.java8demo.stream;

public class Trader {
    private final String name;
    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public static String getCaloricLevel(Dish dish) {
        if (dish.getCalories() <= 400) {
            return "DIET";
        } else if (dish.getCalories() <= 700) {
            return "NORMAL";
        } else {
            return "FAT";
        }
    }

    public String toString(){
        return "Trader:"+this.name + " in " + this.city;
    }
}
