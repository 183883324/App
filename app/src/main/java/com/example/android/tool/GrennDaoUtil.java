package com.example.android.tool;

import com.example.android.base.dao.User;
import com.example.android.app.BaseApp;
import com.example.android.base.dao.UserDao;
import com.example.android.base.dao.prenster.UserDaoUtils;

import java.util.List;

/**
 * 数据库操作
 */
public class GrennDaoUtil {
    //设立静态变量，直接创建实例
    private static GrennDaoUtil mySingleton = new GrennDaoUtil();

    private static UserDao userDao;
    private static UserDaoUtils userDaoUtils1;

    private GrennDaoUtil() {
    }

    //初始化
    public static GrennDaoUtil InitDao() {
        userDao = BaseApp.getInstances().getDaoSession().getUserDao();
        userDaoUtils1 = new UserDaoUtils();
        return mySingleton;
    }

    //增加
    public static void AddData(User user) {
        List<User> users = mySingleton.InitDao().userDaoUtils1.selectWhrer(user.getId());//先查询是否有一样得id
        if (users == null) {
            long a = mySingleton.InitDao().userDaoUtils1.insert(user);
            if (a > 0) {
                Util.Toast("添加成功");
            }
        } else {
            Util.Toast("以有相同的id主健，请换个id主健");
        }
    }

    //根据主健删除一个
    public static void DellData(long id) {
        List<User> users = mySingleton.InitDao().userDaoUtils1.selectWhrer(id);//先查询是否有一样得id
        if (users == null) {
            Util.Toast("没有这个id主健");
        } else {
            mySingleton.InitDao().userDaoUtils1.deleteWhere(1);
            Util.Toast("删除成功");
        }

    }

    //删除全部
    public static void DellAllData() {

        mySingleton.InitDao().userDaoUtils1.deleteAll();
    }

    //修改某一个
    public static void UpDate(User user) {

        List<User> users = mySingleton.InitDao().userDaoUtils1.selectWhrer(user.getId());//先查询是否有一样得id
        if (users == null) {
            Util.Toast("没有这个id主健,没发修改");
        } else {
            mySingleton.InitDao().userDaoUtils1.update(user);
        }
    }

    //查询一个
    public static User CheckDate(long id) {
        User user = null;
        List<User> users = mySingleton.InitDao().userDaoUtils1.selectWhrer(id);//先查询是否有一样得id
        if (users != null) {
            user = users.get(0);
            Util.Toast(user.getId() + "----" + user.getName() + "----" + user.getDate() + "----" + user.getContent());
        }
        return user;
    }

    //根据name查询一个
    public static void CheckNameDate(String name) {
        User user = mySingleton.InitDao().userDaoUtils1.seelctWhrer(name);
        if (user == null) {
            Util.Toast("没有这个name主健");
        } else {
            Util.Toast(user.getId() + "----" + user.getName() + "----" + user.getDate() + "----" + user.getContent());
        }
    }

    //查询全部
    public static void CheckAllDate(long id) {
        List<User> users = mySingleton.InitDao().userDaoUtils1.selectAll();//先查询是否有一样得id
        String s = "";
        if (users == null) {
            Util.Toast("没有任何数据");
        } else {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(0);
                s = s + user.getId() + "----" + user.getName() + "----" + user.getDate() + "----" + user.getContent();
            }
            Util.Toast(s);
        }
    }
}
