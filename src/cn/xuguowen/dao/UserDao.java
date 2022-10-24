package cn.xuguowen.dao;

import cn.xuguowen.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author xuguowen
 * @create 2021-04-17 16:14
 * @Description
 */
public interface UserDao {

    public List<User> findAll();


    User findUsernameAndPassword(String username, String password);

    void add(User user);

    void deleteUserById(int i);

    User findUserById(int id);

    void updateUserById(User user);

    int findTotalCount(Map<String, String[]> map);

    List<User> findCurrentPage(int start, int rows, Map<String, String[]> map);
}
