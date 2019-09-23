package com.hx.stevenbase.Realm;

import io.realm.RealmObject;
/**
 * @PrimaryKey      表示该字段是主键
 * @Required        表示该字段非空
 * @Ignore          表示忽略该字段
 * @Index           添加搜索索引
 */
public class UserDB extends RealmObject {
    private String userName;
    private String passWord;

    //对象的get,set方法
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    public String getPassWord(){
        return passWord;
    }
    public void setPassWord(String passWord){
        this.passWord=passWord;
    }
}
