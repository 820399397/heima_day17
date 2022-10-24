package cn.xuguowen.web.servlet;

import cn.xuguowen.pojo.User;
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
 * @create 2021-04-20 17:12
 * @Description
 */
@WebServlet("/updateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");

        // 2.获取请求参数
        /*
               我发现：如果是浏览器传来的参数，我们就将它封装到User对象中，然后通过User对象进行操作数据库
         */
        Map<String, String[]> map = req.getParameterMap();

        // 3.封装到User对象中
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 4.调用service
        UserServiceImpl service = new UserServiceImpl();
        service.updateUser(user);

        // 5.重定向到userListServlet
        resp.sendRedirect(req.getContextPath() + "/findUserByPageServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
