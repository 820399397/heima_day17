package cn.xuguowen.web.servlet;

import cn.xuguowen.pojo.User;
import cn.xuguowen.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author xuguowen
 * @create 2021-04-17 17:53
 * @Description     登录功能
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 由于要接收用户输入的参数，所以设置编码
        req.setCharacterEncoding("utf-8");

        // 1.获取参数
        String verifycode = req.getParameter("verifycode"); // 获取用户输入的验证码
        Map<String, String[]> map = req.getParameterMap();      // 获取用户输入的用户名和密码，封装到了map结合当中

        // 2.判断用户输入的验证码是否正确，如果不正确，就不进行如下的操作了，节省资源
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");    // 获取服务器生成的验证码
        session.removeAttribute("CHECKCODE_SERVER");    // 保证验证码的一次性
        // 3.判断验证码
        if (!checkcode_server.equalsIgnoreCase(verifycode)){
            // 验证码不正确
            // 给出提示信息
            req.setAttribute("log_msg","验证码错误！");
            // 跳转页面
            req.getRequestDispatcher("/login.jsp").forward(req,resp);

            // 验证码都错误了，直接结束程序
            return;
        }

        // 4.创建User对象，将用户填入的参数封装到user对象中，携带数据到user表中进行查询
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 5.调用service进行查询
        UserServiceImpl service = new UserServiceImpl();
        User loginUser = service.login(user.getUsername(),user.getPassword());

        // 6.判断用户名和密码是否正确
        if (loginUser != null){
            // 登录成功
            // 将用户存入到session中
            session.setAttribute("user",loginUser);
            // 重定向到index.jsp
            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } else {
            // 登录失败
            // 给出提示信息
            req.setAttribute("log_msg","用户名或密码错误！");
            // 跳转页面
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
