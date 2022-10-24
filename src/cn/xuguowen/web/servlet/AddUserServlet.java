package cn.xuguowen.web.servlet;

import cn.xuguowen.pojo.User;
import cn.xuguowen.service.UserService;
import cn.xuguowen.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author xuguowen
 * @create 2021-04-19 17:28
 * @Description
 */
@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        // 1.获取请求参数
        Map<String, String[]> map = req.getParameterMap();

        // 2.创建User对象
        User user = new User();

        // 3.将数据封装到User对象中
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 4.调用service
        UserServiceImpl service = new UserServiceImpl();
        service.addUser(user);

        // 5.重定向到userListServlet
        resp.sendRedirect(req.getContextPath() + "/findUserByPageServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
