package cn.xuguowen.service.impl;

import cn.xuguowen.dao.UserDao;
import cn.xuguowen.dao.impl.UserDaoImpl;
import cn.xuguowen.pojo.PageBean;
import cn.xuguowen.pojo.User;
import cn.xuguowen.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * @author xuguowen
 * @create 2021-04-17 16:13
 * @Description
 */
public class UserServiceImpl implements UserService{
    UserDao dao = new UserDaoImpl();
    /**
     *  调用dao层查询所有的用户
     * @return
     */
    @Override
    public List<User> findAll() {
        // 1.调用dao层查询所有用户的方法

        return dao.findAll();
    }

    public User login(String username, String password) {
        return dao.findUsernameAndPassword(username,password);
    }

    @Override
    public void addUser(User user) {
        // 调用doa层，添加用户的方法
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        // 调用dao
        dao.deleteUserById(Integer.parseInt(id));
    }

    @Override
    public User findUser(String id) {
        return dao.findUserById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.updateUserById(user);
    }

    @Override
    public void delSelectedUser(String[] uids) {
        // 判断非空操作
        if (uids != null && uids.length != 0){
            // 1.循环遍历
            for (String uid : uids) {

                // 2.调用dao删除
                dao.deleteUserById(Integer.parseInt(uid));
            }
        }

    }

    @Override
    public PageBean<User> findUserByPage(String _current, String _rows, Map<String, String[]> map) {

        // 将参数转换类型
        int current = Integer.parseInt(_current);
        int rows = Integer.parseInt(_rows);

        // 1.创建一个空的PageBean对象
        PageBean<User> pb = new PageBean<User>();

        // 2.将前台传入的数据设置到分页对象中
        pb.setCurrent(current);
        pb.setRows(rows);


        // 3.调用dao层查询总记录的方法
        int totalCount = dao.findTotalCount(map);

        // 7.计算总共多少页
        int totalPage = (totalCount % rows) == 0 ? totalCount / rows : (totalCount / rows) + 1;

        // 8.设置到分页对象中
        pb.setTotalPage(totalPage);


        // 4.将其设置到分页对象
        pb.setTotalCount(totalCount);

        int start = (current - 1 ) * rows;
        // 5.调用dao层查询每页显示的数据，每页显示5条
        List<User> list = dao.findCurrentPage(start,rows,map);

        // 6.设置到分页对象中
        pb.setList(list);


        return pb;

    }


}
