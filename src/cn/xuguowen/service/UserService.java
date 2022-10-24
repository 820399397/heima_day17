package cn.xuguowen.service;

import cn.xuguowen.pojo.PageBean;
import cn.xuguowen.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author xuguowen
 * @create 2021-04-17 16:12
 * @Description 用户管理的业务类
 */
public interface UserService {

    /**
     *  调用UserDao查询所有的用户
     * @return
     */
    public List<User> findAll();

    public User login(String username,String password);

    /**
     * 添加User
     */
    void addUser(User user);

    /**
     * 根据id删除指定用户
     * @param id
     */
    void deleteUser(String id);

    /**
     *  查询单独一个用户的信息，将查询到的信息回显到修改页面上
     * @param id
     * @return
     */
    User findUser(String id);

    /**
     * 根据用户的id修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除用户 （删除前端页面选中的用户）
     * @param uids
     */
    void delSelectedUser(String[] uids);

    /**
     * 分页 + 复杂 查询的业务逻辑
     * @param _current
     * @param _rows
     * @param map 复杂查询表单中的数据
     * @return
     */
    PageBean<User> findUserByPage(String _current, String _rows, Map<String, String[]> map);
}
