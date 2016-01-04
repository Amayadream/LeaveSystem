<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <title>请假系统|在运行流程</title>
    <link href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <script src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%=path%>/leave/list/task">请假系统</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">请假管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=path%>/leave/list/task">当前流程</a></li>
                        <li><a href="<%=path%>/leave/list/running">在运行流程</a></li>
                        <li class="active"><a href="<%=path%>/leave/list/finished">已结束流程</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">工作区 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=path%>/workflow/process-list">流程定义与部署管理 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/process-list">所有流程 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/running">在运行流程</a></li>
                        <li><a href="<%=path%>/workflow/model/list">模型工作区</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${userid} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">个人信息</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="<%=path%>/user/logout">注销</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>#</th>
            <th>请假人</th>
            <th>申请时间</th>
            <th>申请开始时间</th>
            <th>申请结束时间</th>
            <th>实际开始时间</th>
            <th>实际结束时间</th>
            <th>流程启动时间</th>
            <th>流程结束时间</th>
            <th>流程版本</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.result }" var="leave" varStatus="status">
            <c:set var="hpi" value="${leave.historicProcessInstance }" />
            <tr id="${leave.id }" tid="${task.id }">
                <td>${status.index + 1}</td>
                <td>${leave.userid }</td>
                <td>${leave.applytime }</td>
                <td>${leave.starttime }</td>
                <td>${leave.realiystarttime }</td>
                <td>${leave.realiyendtime }</td>
                <td>${leave.endtime }</td>
                <td>${hpi.startTime }</td>
                <td>${hpi.endTime }</td>
                <td>${leave.processDefinition.version }</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
