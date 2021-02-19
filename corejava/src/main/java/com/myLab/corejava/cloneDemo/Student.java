package com.myLab.corejava.cloneDemo;

import java.io.Serializable;

public class Student implements Cloneable {
    private String name;
    private int age;
    private Teacher teacher;

    public Student() {
    }

    public Student(String name, int age, Teacher teacher) {
        this.name = name;
        this.age = age;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", teacher=" + teacher +
                '}';
    }

    @Override
    protected Student clone() {
        Student student = null;
        try {
            student = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        student.setTeacher(student.getTeacher().clone());
        return student;
    }
}
