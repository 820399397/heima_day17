package cn.xuguowen.web.servlet;

import cn.xuguowen.pojo.User;
import cn.xuguowen.service.UserService;
import cn.xuguowen.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xuguowen
 * @create 2021-04-17 16:04
 * @Description 查询所有用户的servlet
 */
@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.调用UserService
        UserService service = new UserServiceImpl();
        List<User> users = service.findAll();

        // 2.将数据保存到request域中
        req.setAttribute("users",users);

        // 3.跳转页面
        req.getRequestDispatcher("/list.jsp").forward(req,resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
