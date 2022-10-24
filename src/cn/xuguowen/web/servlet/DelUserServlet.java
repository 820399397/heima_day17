package cn.xuguowen.web.servlet;

import cn.xuguowen.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuguowen
 * @create 2021-04-20 14:10
 * @Description
 */
@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取参数id
        String id = req.getParameter("id");

        // 调用service
        UserServiceImpl service = new UserServiceImpl();
        service.deleteUser(id);

        // 重定向到userListServlet
        resp.sendRedirect(req.getContextPath() + "/findUserByPageServlet");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
