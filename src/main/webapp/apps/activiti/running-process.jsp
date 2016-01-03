<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.activiti.engine.RepositoryService" %>
<%@ page import="com.amayadream.leave.util.ProcessDefinitionCache" %>
<%@ page import="org.apache.commons.lang3.ObjectUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath();%>
<html>
<head>
    <title>工作区|运行中的流程</title>
    <link href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/scojs.css" type="text/css" rel="stylesheet">
    <link href="<%=path%>/plugins/scojs/css/sco.message.css" type="text/css" rel="stylesheet">
    <script src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
    <script src="<%=path%>/static/activiti/common.js"></script>
    <script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=path%>/plugins/scojs/js/sco.message.js"></script>
    <script type="text/javascript">
    </script>
    <script type="text/javascript">
        var ctx = '<%=request.getContextPath() %>';
    </script>
</head>
<body>
<%
    RepositoryService repositoryService = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean(org.activiti.engine.RepositoryService.class);
    ProcessDefinitionCache.setRepositoryService(repositoryService);
%>
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
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">请假管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=path%>/leave/list/task">当前流程</a></li>
                        <li><a href="<%=path%>/leave/list/running">在运行流程</a></li>
                        <li><a href="<%=path%>/leave/list/finished">已结束流程</a></li>
                    </ul>
                </li>
                <li class="dropdown active">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">工作区 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=path%>/workflow/process-list">流程定义与部署管理 </a></li>
                        <li><a href="<%=path%>/workflow/processinstance/process-list">所有流程 </a></li>
                        <li class="active"><a href="<%=path%>/workflow/processinstance/running">在运行流程</a></li>
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

<div>
    <div class="well">
        <h1>工作区/<small>运行中的流程</small></h1>
    </div>
    <div class="well">
        <table class="table table-bordered">
            <tr>
                <th>执行IDssss</th>
                <th>流程实例ID</th>
                <th>流程定义ID</th>
                <th>当前节点</th>
                <th>是否挂起</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${page.result }" var="p">
                <c:set var="pdid" value="${p.processDefinitionId }" />
                <c:set var="activityId" value="${p.activityId }" />
                <tr>
                    <td>${p.id }</td>
                    <td>${p.processInstanceId }</td>
                    <td>${p.processDefinitionId }</td>
                    <td><button class="btn btn-primary btn-sm show" id="${p.id}" onclick="showPage('${p.id}');"><%=ProcessDefinitionCache.getActivityName(pageContext.getAttribute("pdid").toString(), ObjectUtils.toString(pageContext.getAttribute("activityId"))) %></button></td>
                    <td>
                        <c:if test="${p.suspended}">
                            <span class="label label-danger">已挂起</span>
                        </c:if>
                        <c:if test="${!p.suspended}">
                            <span class="label label-success">正常</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${p.suspended }">
                            <a href="<%=path%>/workflow/processinstance/update/active/${p.processInstanceId}" class="btn btn-sm btn-success">激活</a>
                        </c:if>
                        <c:if test="${!p.suspended }">
                            <a href="<%=path%>/workflow/processinstance/update/suspend/${p.processInstanceId}" class="btn btn-sm btn-danger">挂起</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
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
    <c:if test="${not empty error}">
    $.scojs_message("${error}", $.scojs_message.TYPE_ERROR);
    </c:if>
    <c:if test="${not empty message}">
    $.scojs_message("${message}", $.scojs_message.TYPE_OK);
    </c:if>
    function showPage(id){
        $("#img").attr("src",'<%=path%>/workflow/process/trace/auto/'+id);
        $("#show-model").modal();
    }
</script>
</body>
</html>
