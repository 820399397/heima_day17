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
 * @create 2021-04-26 16:07
 * @Description  批量删除用户的servlet（删除复选框选中的用户）
 */
@WebServlet("/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取请求参数uid
        String[] uids = req.getParameterValues("uid");

        // 2.调用service进行查询
        UserServiceImpl service = new UserServiceImpl();
        service.delSelectedUser(uids);

        // 3.重定向到userListServlet
        resp.sendRedirect(req.getContextPath() + "/findUserByPageServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
