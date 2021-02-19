package com.mylab;

import java.io.File;
import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(TestMain.class.getResource(""));
        TestMain tm = new TestMain();
        File f = new File(tm.getClass().getResource("").getPath());
        System.out.println(f);
        File f1 = new File("");
        System.out.println(f1.getCanonicalPath());
    }
}
