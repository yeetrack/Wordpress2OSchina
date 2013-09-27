package com.yeetrack.blog2oschina;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 13-9-27
 * Time: 上午11:32
 * To change this template use File | Settings | File Templates.
 * 配置用户名，密码。oschina的密码是明文密码取md5（40位），可以在这里获取 http://app.baidu.com/app/enter?appid=228509
 */
public class UserInfo
{
    private static String username = "wangxuemeng90@126.com";
    private static String password = "明文密码取md5（40位）";

    public static String getUsername()
    {
        return username;
    }

    public static String getPassword()
    {
        return password;
    }
}
