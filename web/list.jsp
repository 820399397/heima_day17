<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>
        // 点击删除按钮跳转过来的方法
        function deleteUser(id) {
            // 给出用户提示
            if (confirm("你确定要删除吗？")) {
                location = "${pageContext.request.contextPath}/delUserServlet?id=" + id;
            }
        }
        // 当页面加载完毕之后在获取元素对象
        window.onload = function () {

            // 做出全选与全不选的效果
            var firstCb = document.getElementById("firstCb")
            // 1.给它绑定单击事件
            firstCb.onclick = function () {
                var cbs = document.getElementsByName("uid")
                // 2.循环遍历cbs
                for (var i = 0; i < cbs.length; i++){
                    // 让下面所有的复选框状态 = 它的状态
                    cbs[i].checked = this.checked;
                }
            }


            var del = document.getElementById("del");   // 获取删除选中的按钮对象

            // 给按钮对象绑定单击事件
            del.onclick = function() {
                // 将表单提交，后台需要获取复选框中用户的ID
                var form = document.getElementById("form");

                if (confirm("您确定要删除选中项？")){

                    // 获取下面所有的复选框
                    var cbs = document.getElementsByName("uid");
                    var flag = false;
                    // 循环判断他的状态是否是选中的状态，如果不是则不会提交表单，也就不会跳转到指定的servlet
                    for (var i = 0; i < cbs.length; i++){
                        if (cbs[i].checked){
                            // 只要有一个复选框被选中，就可以提交表单
                            flag = true;
                            break;
                        }
                    }
                    if (flag){
                        // 调用表单提交的方法
                        form.submit();
                    }
                }
            }
        }

    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <div style="float: left;margin-left: 5px">
        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" class="form-control" name="name" value="${map.name[0]}" id="exampleInputName2">
            </div>

            <div class="form-group">
                <label for="exampleInputEmail1">籍贯</label>
                <input type="text" class="form-control" name="address" value="${map.address[0]}" id="exampleInputEmail1">
            </div>

            <div class="form-group">
                <label for="exampleInputName3">邮箱</label>
                <input type="text" class="form-control" name="email" value="${map.email[0]}" id="exampleInputName3">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>

    <div style="float: right;margin: 5px">
        <td colspan="8" align="center"><a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a></td>
        <td colspan="8" align="center"><a id="del" class="btn btn-primary" href="javascript:void(0)">删除选中</a></td>
    </div>
    <form action="${pageContext.request.contextPath}/delSelectedServlet" method="post" id="form">
        <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th><input type="checkbox" id="firstCb"></th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${pb.list}" var="user" varStatus="s">
            <%--varStatus对应前端页面的编号这一个单元格--%>
            <tr>
                <td><input type="checkbox" value="${user.id}" name="uid"></td>
                <td>${s.count}</td>
                <td>${user.name}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.qq}</td>
                <td>${user.email}</td>
                <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserById?id=${user.id}">修改</a>&nbsp;
                    <%--根据用户的id删除指定的用户--%>
                    <%-- 点击删除按钮直接跳转到servlet，这样页面做的不够友好，应该给出用户提示--%>
                    <a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id})">删除</a></td>
            </tr>
        </c:forEach>

    </table>
    </form>

    <nav aria-label="Page navigation">
        <ul class="pagination">
            <%--当前页为1时，将上一页的按钮禁用，不可点击--%>
            <c:if test="${pb.current == 1}">
                <li class="disabled">
                    <%--上一页也就是当前页码 - 1--%>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?current=${pb.current}&rows=5&name=${map.name[0]}&address=${map.address[0]}&email=${map.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${pb.current != 1}">
                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?current=${pb.current - 1}&rows=5&name=${map.name[0]}&address=${map.address[0]}&email=${map.email[0]}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>


            <c:forEach begin="1" end="${pb.totalPage}" var="i">
                <c:if test="${pb.current == i}">
                    <li class="active"><a href="${pageContext.request.contextPath}/findUserByPageServlet?current=${i}&rows=5&name=${map.name[0]}&address=${map.address[0]}&email=${map.email[0]}">${i}</a></li>
                </c:if>
                <c:if test="${pb.current != i}">
                    <li><a href="${pageContext.request.contextPath}/findUserByPageServlet?current=${i}&rows=5&name=${map.name[0]}&address=${map.address[0]}&email=${map.email[0]}">${i}</a></li>
                </c:if>
            </c:forEach>


            <%--如果当页码等于最后一页，那下一页就不让点击--%>
            <c:if test="${pb.current == pb.totalPage}">
                <li class="disabled">
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?current=${pb.current}&rows=5&name=${map.name[0]}&address=${map.address[0]}&email=${map.email[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${pb.current != pb.totalPage}">
                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?current=${pb.current + 1}&rows=5&name=${map.name[0]}&address=${map.address[0]}&email=${map.email[0]}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>






            <span style="font-size: 25px;margin-left: 5px">
            共${pb.totalCount}条记录 共${pb.totalPage}页
        </span>
        </ul>

    </nav>
</div>
</body>
</html>
