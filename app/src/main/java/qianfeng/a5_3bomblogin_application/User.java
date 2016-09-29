package qianfeng.a5_3bomblogin_application;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class User extends BmobObject {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
