package com.myLab.corejava.transientDemo;

import java.io.*;

/**
 * transient关键字用法示例
 * transient是用于在对象序列化时，指定某些字段不参与序列化。
 * 比如，一些敏感数据 卡号 账号 等，不应该参与对象的序列化，这时就可以对这些属性用transient关键字来修饰。
 */
public class User implements Serializable {
    private transient String cardNo = "";
    private String name = "";
    private transient final static int age = 24;

    /**
     * 对象序列化方法，将对象序列化到文件中
     * @param user
     * @param targetPath
     */
    public static void serializeUser(User user, String targetPath){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(targetPath));
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对象反序列化方法，将文件中信息反序列化成对象
     * @param file
     * @return
     */
    public static User deSerializeUser(File file){
        ObjectInputStream ois = null;
        User user = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            user = (User) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getName() {
        return name;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "cardNo: " + this.getCardNo() + "\n name: " + this.getName() + "\n age: " +  User.age;
    }

    public static void main(String[] args) {
        String targetPath = "C:\\LifeAndWork\\userSerializeInfo";
        User oldUser = new User();
        oldUser.setCardNo("123456");
        oldUser.setName("liufj");
        User.serializeUser(oldUser, targetPath);
        User newUser = User.deSerializeUser(new File("C:\\LifeAndWork\\userSerializeInfo"));
        System.out.println(newUser);
        /*
        属性cardNo和age都是用transient来修饰，但是从程序输出来看，cardNo确实没有参与对象的序列化过程，而age依然参与了，证明transient对静态类变量不起作用。
        cardNo: null
        name: liufj
        age: 24
         */
    }
}
