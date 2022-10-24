package cn.xuguowen.web.servlet;

import cn.xuguowen.pojo.PageBean;
import cn.xuguowen.pojo.User;

import cn.xuguowen.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author xuguowen
 * @create 2021-05-08 16:16
 * @Description 分页 + 复杂查询
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        // 1.获取请求参数current和rows
        String current = req.getParameter("current");   // 当前页码
        String rows = req.getParameter("rows");         // 每页显示的条数

        // 避免没有获取到参数，参数为null，这样在调用service方法是进行类型转换出现NumberFormatException异常
        if (current == null || "".equals(current)){
            current = "1";
        }

        if (rows == null || "".equals(rows)){
            rows = "5"; // 调用service方法后会进行转换
        }

        // 获取复杂查询表单中的3个数据:切记：这里Map集合中的值是一个数组
        Map<String, String[]> map = req.getParameterMap();

        // 2.调用service方法，将返回的分页对象存入request域中
        UserServiceImpl service = new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(current,rows,map);
        req.setAttribute("pb",pb);

        // 点击复杂查询的按钮之后，我看不到我之前查询的条件了，我想要实现查询条件的回显
        req.setAttribute("map",map);

        // 4.转发到list.jsp
        req.getRequestDispatcher("/list.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
