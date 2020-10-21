package com.myLab.corejava.classloaderDemo;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        try {
            Class helloClass = helloClassLoader.findClass("Hello");
            Method method = helloClass.getDeclaredMethod("hello");
            method.invoke(helloClass.newInstance());
            System.out.println("ok");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = getClassData();
        return defineClass(name, data, 0, data.length);
    }

    private byte[] getClassData() throws ClassNotFoundException {
        File inputFile = new File("G:\\极客时间java训练营\\week1\\Hello.xlass\\Hello.xlass");
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        if(inputFile.exists()){
            try {
                fileInputStream = new FileInputStream(inputFile);
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[10];
                while(true){
                    int len = fileInputStream.read(buffer);
                    for(int i = 0; i < len; i++)
                        buffer[i] = (byte) (255 - buffer[i]);
                    if(len == -1)
                        break;
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return byteArrayOutputStream.toByteArray();
        }else
            return null;
    }
}
