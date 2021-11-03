package com.myLab.corejava.java8demo;

public class AddStatement {
    private int a, b;

    public AddStatement(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public String toFormula() {
        if (a == 0) {
            return b + "";
        }
        if (b == 0) {
            return a + "";
        }
        return "(" + a + " + " + b + ")";
    }
    // 1 * (3 + 2) + 6 * 3

    static class test {
        int multifactor;
        AddStatement addStatement;

        public test(int multifactor, AddStatement addStatement) {
            this.multifactor = multifactor;
            this.addStatement = addStatement;
        }

        public String toFormula() {
            return multifactor + "*" + addStatement.toFormula();
        }
    }

    public static void main(String[] args) {
        AddStatement.test test1 = new AddStatement.test(1, new AddStatement(3, 2));
        AddStatement.test test2 = new AddStatement.test(6, new AddStatement(3, 0));
        System.out.println(test1.toFormula());
        System.out.println(test2.toFormula());
        System.out.println(test1.toFormula() + "+" + test2.toFormula());
    }
}
