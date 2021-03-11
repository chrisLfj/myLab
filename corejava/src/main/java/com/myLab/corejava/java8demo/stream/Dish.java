package com.myLab.corejava.java8demo.stream;

/**
 * 用于演示stream
 */
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public String getCaloricLevel() {
        if (this.getCalories() <= 400) {
            return "DIET";
        } else if (this.getCalories() <= 700) {
            return "NORMAL";
        } else {
            return "FAT";
        }
    }

    public static String getCaloricLevel1(Dish dish) {
        if (dish.getCalories() <= 400) {
            return "DIET";
        } else if (dish.getCalories() <= 700) {
            return "NORMAL";
        } else {
            return "FAT";
        }
    }

    public enum Type {
        MEAT(1, "肉"),
        FISH(2, "鱼"),
        OTHER(3,"其它");

        Type(int code, String name) {
        }

    }

    public String toString() {
        return name + "/" + vegetarian + "/" + calories + "/" + type.name();
    }
}
