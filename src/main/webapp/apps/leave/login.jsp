<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <title>请假系统|登陆</title>
    <link href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/sco.message.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/scojs.css" type="text/css" rel="stylesheet">
    <script src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=path%>/plugins/scojs/js/sco.message.js"></script>
    <style>
        .login form{
            margin: 0 auto;
            width : 400px;
        }
    </style>
</head>
<body>
<div class="login">
    <form action="<%=path%>/user/login" method="post">
        <h1>请假管理|登陆</h1>
        <hr>
        <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="请输入您的用户名...">
        </div>
        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="请输入您的密码...">
        </div>
        <button type="reset" class="btn btn-info">重置</button>
        <button type="submit" class="btn btn-success">提交</button>
    </form>
</div>

<script>
    if('${error}'){
        $.scojs_message("${error}",$.scojs_message.TYPE_ERROR);
    }
    <c:if test="${not empty param.error}">
    $.scojs_message("用户名或密码错误!",$.scojs_message.TYPE_ERROR);
    </c:if>
</script>
</body>
</html>
