<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <title>请假系统|系统日志</title>
    <link href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" >
    <link href="<%=path%>/plugins/scojs/css/scojs.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/sco.message.css" type="text/css" rel="stylesheet">
    <script src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=path%>/plugins/scojs/js/sco.message.js"></script>
    <script src="<%=path%>/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script src="<%=path%>/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<body>
<div class="container-fluid">
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>#</th>
                <th>操作人</th>
                <th>操作时间</th>
                <th>操作类型</th>
                <th>操作详情</th>
                <th>ip</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${result}" var="log" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${log.userid}</td>
                    <td>${log.time}</td>
                    <td>${log.type}</td>
                    <td>${log.detail}</td>
                    <td>${log.ip}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
