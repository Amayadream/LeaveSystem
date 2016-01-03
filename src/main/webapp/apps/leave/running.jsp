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
                        <li class="active"><a href="<%=path%>/leave/list/running">在运行流程</a></li>
                        <li><a href="<%=path%>/leave/list/finished">已结束流程</a></li>
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
            <th>当前节点</th>
            <th>任务创建时间</th>
            <th>流程状态</th>
            <th>当前处理人</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.result }" var="leave" varStatus="status">
            <c:set var="task" value="${leave.task }" />
            <c:set var="pi" value="${leave.processInstance }" />
            <tr id="${leave.id }" tid="${task.id }">
                <td>${status.index +1}</td>
                <td>${leave.userid }</td>
                <td>${leave.applytime }</td>
                <td>${leave.starttime }</td>
                <td>${leave.endtime }</td>
                <td>
                    <button class="btn btn-primary btn-sm show" id="${pi.id}" onclick="showPage('${pi.id}');">${task.name }</button>
                </td>
                <td>${task.createTime }</td>
                <td>${pi.suspended ? "已挂起" : "正常" }；<b title='流程版本号'>V: ${leave.processDefinition.version }</b></td>
                <td>${task.assignee }</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 图形模态框 -->
<div class="modal fade" id="show-model" tabindex="-1" role="dialog" aria-labelledby="model2" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="model2">
                    <span class="glyphicon glyphicon-search"></span> 流程图
                </h4>
            </div>
            <div class="modal-body">
                <img src="" id="img">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> 关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    function showPage(id){
        $("#img").attr("src",'<%=path%>/workflow/process/trace/auto/'+id);
        $("#show-model").modal();
    }
</script>
</body>
</html>
