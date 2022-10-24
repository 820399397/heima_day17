package cn.xuguowen.web.servlet;

import cn.xuguowen.pojo.User;
import cn.xuguowen.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuguowen
 * @create 2021-04-20 15:25
 * @Description 前端页面点击修改跳转到这个servlet，查询该用户的信息，然后回写到页面上，完成用户信息回显的功能
 */
@WebServlet("/findUserById")
public class FindUserById extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求参数id
        String id = req.getParameter("id");

        // 调用service根据id查询
        UserServiceImpl service = new UserServiceImpl();
        User user = service.findUser(id);

        // 将user对象存入到request域当中
        req.setAttribute("user",user);

        // 跳转到update.jsp页面
        req.getRequestDispatcher("/update.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
