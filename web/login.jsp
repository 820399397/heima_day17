<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>管理员登录</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        // 刷新验证码图片的方法
        function refreshCode() {
            // 1.获取图片元素对象
            var vcode = document.getElementById("vcode");

            // 2.切换图片的src属性，就是让图片再次访问servlet + 时间戳的方式
            vcode.src = "${pageContext.request.contextPath}/checkCodeServlet?time" + new Date().getTime();
        }
    </script>
</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">管理员登录</h3>
    <form action="${pageContext.request.contextPath}/loginServlet" method="post">
        <div class="form-group">
            <label for="user">用户名：</label>
            <%--由于数据库中没有登录功能的一张表，所以我再现有的user表上进行字段的添加username，password--%>
            <input type="text" name="username" class="form-control" id="user" placeholder="请输入用户名"/>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="请输入密码"/>
        </div>

        <div class="form-inline">
            <label for="vcode">验证码：</label>
            <input type="text" name="verifycode" class="form-control" id="verifycode" placeholder="请输入验证码" style="width: 120px;"/>
            <%--图片要显示验证码，使用servlet动态生成验证码，改变图片的src属性，访问生成验证码图片的servlet--%>
            <%--验证码图片再一个超链接中，点击超链接，执行切换验证码图片的功能，再js中定义一个refreshCode()方法--%>
            <a href="javascript:refreshCode()"><img src="${pageContext.request.contextPath}/checkCodeServlet" title="看不清点击刷新" id="vcode"/></a>
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" value="登录">
        </div>
    </form>

    <!-- 出错显示的信息框 -->
    <div class="alert alert-warning alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" >
            <span>&times;</span></button>
        <strong>
            <span>${log_msg}</span>
        </strong>
    </div>
</div>
</body>
</html>