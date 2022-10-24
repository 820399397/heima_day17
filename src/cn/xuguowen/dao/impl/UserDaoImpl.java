package cn.xuguowen.dao.impl;

import cn.xuguowen.dao.UserDao;
import cn.xuguowen.pojo.User;
import cn.xuguowen.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuguowen
 * @create 2021-04-17 16:14
 * @Description
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        // 1.使用JDBC操作数据库，查询所有的用户

        // 定义sql语句
        String sql = "select * from user";

        // 执行sql语句
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUsernameAndPassword(String username, String password) {
        try {
            // 1.定义sql语句
            String sql = "select * from user where username = ? and password = ?";

            // 2.执行sql语句
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void add(User user) {
        // 定义sql语句
        // 数据库中的user表id字段自增长，所以定义为null，用户名和密码暂且先用一个用户登录即可，所以也先不用
        String sql = "insert into user values (null,?,?,?,?,?,?,null,null)";

        // 2.执行sql语句
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }

    @Override
    public void deleteUserById(int id) {
        // 定义sql语句
        String sql = "delete from user where id = ?";

        // 执行sql语句
        template.update(sql,id);
    }

    @Override
    public User findUserById(int id) {
        String sql = "select * from user where id = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
    }

    @Override
    public void updateUserById(User user) {
        // 定义sql语句
        String sql = "update user set name = ?, gender = ?, age = ?, address = ?, qq = ?, email = ? WHERE id = ?";

        // 执行sql语句
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());

    }

    /**
     * 查询所有的数据
     * @return
     * @param map
     */
    @Override
    public int findTotalCount(Map<String, String[]> map) {
        // 1.定义模板sql
        String sql = "select count(*) from user WHERE 1 = 1 ";
        // 2.可变序列拼接sql
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();   // 存储参数的动态数组
        // 3.遍历map集合
        Set<String> keySet = map.keySet();
        for (String key : keySet) {

            // 排除current和rows的情况
            if ("current".equals(key) || "rows".equals(key)) {
                continue;
            }
            // 通过键获取值,我们这里只需要获取第个值就可以，只要第一个值存在，就拼接sql，有可能用户只输入了一个条件
            String value = map.get(key)[0];

            if (value != null || !"".equals(value)){
                sb.append(" and "+ key +" like ?");
                params.add("%" + value +"%");
            }
        }
        System.out.println(params);
        System.out.println(sb.toString());

        // jdk1.5特性，自动拆箱
        Integer totalCount = template.queryForObject(sb.toString(), Integer.class,params.toArray());

        return totalCount;
    }

    /**
     * 查询每页显示的数据
     * @param start
     * @param rows
     * @param map
     * @return
     */
    @Override
    public List<User> findCurrentPage(int start, int rows, Map<String, String[]> map) {
        // 1.模板sql
        String sql = "select * from user where 1 = 1";

        // 2.可变序列拼接sql
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> params = new ArrayList<>();   // 存储参数的动态数组
        // 3.遍历map集合
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            // 运行程序出现了这样的异常select count(*) from user WHERE 1 = 1 and current like ? and rows like ?：sql语法错误

            // 排除current和rows的情况
            if ("current".equals(key) || "rows".equals(key)) {
                continue;
            }
            // 通过键获取值,我们这里只需要获取第个值就可以，只要第一个值存在，就拼接sql，有可能用户只输入了一个条件
            String value = map.get(key)[0];

            if (value != null || !"".equals(value)){
                sb.append(" and "+ key +" like ?");
                params.add("%" + value +"%");
            }
        }

        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);

        System.out.println(params);
        System.out.println(sb.toString());

        List<User> list = template.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class), params.toArray());
        return list;
    }
}
