package com.mylab.mybatisplus.quickstart.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.mylab.mybatisplus.quickstart.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mylab.mybatisplus.quickstart.entity.enums.GenderEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author liufj
 * @since 2021-05-26
 */
@TableName("user")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 骞撮緞
     */
    @TableField("age")
    private Integer age;

    /**
     * 性别，1，男；2，女
     */
    @TableField("gender")
    private GenderEnum gender;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    @TableField("isDelete")
    @TableLogic
    private Integer isdelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    @Override
    public String toString() {
        return "User{" +
            "name=" + name +
            ", age=" + age +
            ", gender=" + gender +
            ", email=" + email +
            ", isdelete=" + isdelete +
        "}";
    }
}
