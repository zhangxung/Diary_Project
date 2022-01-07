package com.example.diary;

import java.io.Serializable;
//新建一個User類並實現序列化Serializable
public class User implements Serializable {
    //序列化id
    private static final long serialVersionUID=1L;
    private String username; //使用者名稱
    private String password;  //密碼
    public User(){}
    public User(String username,String password){
        super();
        this.username=username;
        this.password=password;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String pasword){
        this.password=pasword;
    }
}
